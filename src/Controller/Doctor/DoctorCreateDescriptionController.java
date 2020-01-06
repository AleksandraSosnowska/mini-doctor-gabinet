package Controller.Doctor;

import Controller.MainController;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import java_files.*;
import javafx.scene.text.Text;

import javax.persistence.Query;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DoctorCreateDescriptionController {

    @FXML
    public TextField patient_pesel;

    @FXML
    public TextField visit_date;

    @FXML
    public TextField visit_description;

    @FXML
    public TextField medicines;

    @FXML
    public Text patientName;

    @FXML
    public Text errorPesel;

    @FXML
    public Text errorDate;

    @FXML
    public Text emptyData;

    private MainController mainController;

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    @FXML
    public void backMenu() {
        mainController.switchScreen("doctor_menu", true);
    }

    @FXML
    public void checkPatientExisting(){
        errorDate.setText("");
        errorPesel.setText("");
        patientName.setText("");
        try {
            String new_pesel = patient_pesel.getText();
            String read_date = visit_date.getText();

            if(new_pesel.length() == 11){
                if(read_date.length() == 10){
                    Date new_date = new SimpleDateFormat("dd/MM/yyyy").parse(read_date);

                    Query q1 =  mainController.getEm().createQuery("SELECT p FROM Patient p WHERE p.pesel = :pesel");
                    q1.setParameter("pesel", new_pesel);
                    Patient patient = (Patient) q1.getSingleResult();

                    Query q2 =  mainController.getEm().createQuery("SELECT v FROM Visit v WHERE v.patient_id = :id AND v.visit_date = :date");
                    q2.setParameter("id", patient.getId());
                    q2.setParameter("date", new_date);
                    Visit visit = (Visit) q2.getSingleResult();

                    if (visit.getDoctor_id() == mainController.getLogged_doctor().getId()) {
                        errorDate.setText("");
                        errorPesel.setText("");
                        patientName.setText(patient.getName() + " " + patient.getLastname());
                        mainController.setTemp_patient(patient);
                        mainController.setTemp_visit(visit);
                    } else {
                        emptyData.setText("Wizyta umówiona do innego lekarza");
                        patientName.setText("");
                        errorPesel.setText("");
                        errorDate.setText("");
                    }
                }else {
                    patientName.setText("");
                    errorDate.setText("Błędna data!");
                    errorPesel.setText("");
                }
            }else{
                patientName.setText("");
                errorPesel.setText("Błędny pesel!");
                errorDate.setText("");
            }
        } catch (ParseException e) {
            System.out.println("Błędne dane!");
        }
    }

    @FXML
    public void addSummary() {
        try {

            String visit_summary = visit_description.getText();
            String new_medicines = medicines.getText();

            if(mainController.getTemp_visit() != null && visit_summary.length() > 5 && new_medicines.length() > 2){

                if(mainController.getTemp_visit().getStatusString().equals("potwierdzona")) {
                    mainController.getEm().getTransaction().begin();
                    Visit_description new_description = new Visit_description(visit_summary, new_medicines, mainController.getTemp_visit().getId());
                    mainController.getEm().persist(new_description);

                    mainController.getEm().getTransaction().commit();
                    System.out.println("Dodano opis wizyty");

                    mainController.switchScreen("doctor_menu", true);
                } else {
                    emptyData.setText("Wizyta nie została potwierdzona");
                }
            } else {
                emptyData.setText("Wprowadź wszystkie dane!");
            }
        } catch (NullPointerException e){
            System.out.println("Nic nie zaznaczono");
        }
    }

    public void loadData(){
        mainController.setTemp_visit(null);
        patientName.setText("");
        errorPesel.setText("");
        errorDate.setText("");
        emptyData.setText("");
    }
}