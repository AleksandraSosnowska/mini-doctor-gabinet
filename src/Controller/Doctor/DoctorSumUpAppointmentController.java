package Controller.Doctor;

import Controller.MainController;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import java_files.*;
import javafx.scene.text.Text;

import javax.persistence.Query;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class DoctorSumUpAppointmentController {

    @FXML
    public Text patient_pesel;

    @FXML
    public Text visit_date;

    @FXML
    public TextField visit_description;

    @FXML
    public TextField medicines;

    private MainController mainController;

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    @FXML
    public void backMenu() {
        mainController.switchScreen("doctor_menu", true);
    }

    @FXML
    public void addSummary() {
        try {

            String visit_summary = visit_description.getText();
            String new_medicines = medicines.getText();

            if (visit_summary.length() > 2 && new_medicines.length() > 2){

               /* Query q1 =  mainController.getEm().createQuery("SELECT p FROM Patient p WHERE p.pesel = :pesel");
                q1.setParameter("pesel", patient_pesel);
                Patient patient = (Patient) q1.getSingleResult();

                Query q2 =  mainController.getEm().createQuery("SELECT v FROM Visit v WHERE v.patient_id = :id");
                q2.setParameter("id", patient.getId());
                Visit visit = (Visit) q2.getSingleResult();*/

                Visit change_visit = mainController.getEm().find(Visit.class, mainController.getTemp_visit());

                mainController.getEm().getTransaction().begin();

                Visit_description new_description = new Visit_description(visit_summary, new_medicines, mainController.getTemp_visit().getId());
                mainController.getEm().persist(new_description);
                change_visit.setStatus("past");

                mainController.getEm().getTransaction().commit();
                System.out.println("Dodano opis wizyty");

                mainController.switchScreen("doctor_visit_planner", true);
            }
        } catch (NullPointerException e){
            System.out.println("Nic nie zaznaczono");
        }
    }

    public void loadData(){
        try {

            patient_pesel.setText(mainController.getTemp_patient().getPesel());

            DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyy");
            visit_date.setText(dateFormat.format(mainController.getTemp_visit().getVisit_date()));

        } catch (Exception e){
            e.printStackTrace();
        }
    }

}