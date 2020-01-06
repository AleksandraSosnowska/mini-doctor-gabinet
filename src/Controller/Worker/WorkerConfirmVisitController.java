package Controller.Worker;

import Controller.MainController;
import java_files.*;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import javax.persistence.Query;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class WorkerConfirmVisitController {

    @FXML
    public TextField visitPesel;
    @FXML
    public TextField visitDate;
    @FXML
    public Text patientName;
    @FXML
    public Text patientLastname;
    @FXML
    public Text patientDate;
    @FXML
    public Text patientAddress;
    @FXML
    public Text patientAge;
    @FXML
    public Text patientStatus;
    @FXML
    public Text visitType;
    @FXML
    public Text specialization;
    @FXML
    public Text doctorLastname;
    @FXML
    public Text doctorName;
    @FXML
    public Text visitStatus;
    @FXML
    public Text errorPesel;
    @FXML
    public Text errorDate;
    @FXML
    public Text errorStatus;

    private MainController mainController;

    @FXML
    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    @FXML
    public void backMenu() {
        mainController.switchScreen("worker_menu", true);
    }

    @FXML
    public void loadVisitDetails() {
        errorDate.setText("");
        errorPesel.setText("");
        errorStatus.setText("");
        clearTexts();

        String new_pesel = visitPesel.getText();
        String read_date = visitDate.getText();

        if(new_pesel.length() == 11){
            try{
                Date new_date = new SimpleDateFormat("dd/MM/yyyy").parse(read_date);

                Query q1 =  mainController.getEm().createQuery("SELECT p FROM Patient p WHERE p.pesel = :pesel");
                q1.setParameter("pesel", new_pesel);

                try {
                    Patient patient = (Patient) q1.getSingleResult();
                    Query q2 =  mainController.getEm().createQuery("SELECT v FROM Visit v WHERE v.patient_id = :id AND v.visit_date = :date");
                    q2.setParameter("id", patient.getId());
                    q2.setParameter("date", new_date);

                    try {
                        Visit visit = (Visit) q2.getSingleResult();

                        Query q3 = mainController.getEm().createQuery("SELECT d FROM Doctor d WHERE d.id = :id");
                        q3.setParameter("id", visit.getDoctor_id());
                        Doctor doctor = (Doctor) q3.getSingleResult();

                        mainController.setTemp_patient(patient);
                        mainController.setTemp_visit(visit);
                        mainController.setTemp_doctor(doctor);
                        setTexts();

                    } catch (Exception e){
                        clearTexts();
                        errorDate.setText("Błędna data!");
                        errorPesel.setText("");
                    }

                } catch (Exception e){
                    clearTexts();
                    errorPesel.setText("Błędny pesel!");
                    errorDate.setText("");
                }

            }catch (ParseException e){
                clearTexts();
                errorDate.setText("Błędna data!");
                errorPesel.setText("");
            }
        }else{
            clearTexts();
            errorPesel.setText("Błędny pesel!");
            errorDate.setText("");
        }
    }

    private void clearTexts(){
        patientName.setText("");
        patientLastname.setText("");
        patientDate.setText("");
        patientAddress.setText("");
        patientAge.setText("");
        patientStatus.setText("");
        visitType.setText("");
        specialization.setText("");
        doctorLastname.setText("");
        doctorName.setText("");
        visitStatus.setText("");
    }

    private void setTexts(){
        errorDate.setText("");
        errorPesel.setText("");
        patientName.setText(mainController.getTemp_patient().getName());
        patientLastname.setText(mainController.getTemp_patient().getLastname());
        patientDate.setText(new SimpleDateFormat("dd/MM/yyyy").format(mainController.getTemp_patient().getBirth_date()));
        patientAddress.setText(mainController.getTemp_patient().getHome_address());
        patientAge.setText(Integer.toString(mainController.getTemp_patient().getAge()));
        if(mainController.getTemp_patient().getInsurance_status() == true) { patientStatus.setText("Ubezpieczony"); }
        else { patientStatus.setText("Brak ubezpiecznia"); }
        visitType.setText(mainController.getTemp_visit().getVisitType());
        specialization.setText(mainController.getTemp_doctor().getSpecialization());
        doctorLastname.setText(mainController.getTemp_doctor().getLastname());
        doctorName.setText(mainController.getTemp_doctor().getName());
        visitStatus.setText(mainController.getTemp_visit().getStatusString());
    }

    @FXML
    public void declineAppointment() {
        if(mainController.getTemp_visit() != null){

            if(mainController.getTemp_visit().getStatusString().equals("oczekująca")){

                Visit change_visit = mainController.getEm().find(Visit.class, mainController.getTemp_visit());
                mainController.getEm().getTransaction().begin();
                change_visit.setStatus("declined");
                mainController.getEm().getTransaction().commit();

                mainController.setTemp_visit(change_visit);
                setTexts();
                errorDate.setText("");
                errorPesel.setText("");
                errorStatus.setText("");

                //loadData();
                //mainController.switchScreen("worker_confirm_appointment", true);
            } else {
                errorStatus.setText("Wizyta nie posiada statusu oczekująca");
            }

        }
    }

    @FXML
    public void ConfirmAppointment() {
        if(mainController.getTemp_visit() != null){

            if(mainController.getTemp_visit().getStatusString().equals("oczekująca")) {

                Visit change_visit = mainController.getEm().find(Visit.class, mainController.getTemp_visit());

                mainController.getEm().getTransaction().begin();
                change_visit.setStatus("confirmed");
                mainController.getEm().getTransaction().commit();

                mainController.setTemp_visit(change_visit);
                setTexts();
                errorDate.setText("");
                errorPesel.setText("");
                errorStatus.setText("");
//                loadData();
//                mainController.switchScreen("worker_confirm_appointment", true);
            } else {
                errorStatus.setText("Wizyta nie posiada statusu oczekująca");
            }
        }

    }

    public void loadData(){
        clearTexts();
        errorDate.setText("");
        errorPesel.setText("");
        errorStatus.setText("");
        mainController.setTemp_visit(null);
        mainController.setTemp_doctor(null);
        mainController.setTemp_patient(null);
    }
}