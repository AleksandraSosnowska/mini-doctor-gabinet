package Controller.Doctor;

import Controller.MainController;
import TableClasses.*;
import java_files.Doctor;
import java_files.Patient;
import java_files.Visit;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.text.SimpleDateFormat;
import java.util.List;

public class DoctorVisitPlannerController {


    @FXML
    public TableView <UserAppointment> appointment_table;

    @FXML
    public TableColumn <UserAppointment, String> appointment_type;

    @FXML
    public TableColumn <UserAppointment, String> patient_name;

    @FXML
    public TableColumn <UserAppointment, String> date;

    @FXML
    public TableColumn <UserAppointment, String> status;

    private MainController mainController;

    private ObservableList<UserAppointment> appointment_list = FXCollections.observableArrayList();

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    @FXML
    public void backMenu() {
        mainController.switchScreen("doctor_menu", true);
    }

    @FXML
    public void confirmAppoitment() {
        try {
            UserAppointment selected = appointment_table.getSelectionModel().getSelectedItem();
            if (selected.getVisit_id() > 0) {

                Query q1 = mainController.getEm().createQuery("SELECT v FROM Visit v WHERE v.id = :id");
                q1.setParameter("id", selected.getVisit_id());
                Visit visit = (Visit) q1.getSingleResult();

                Query q2 = mainController.getEm().createQuery("SELECT p FROM Patient p WHERE p.id = :id");
                q2.setParameter("id", visit.getPatient_id());
                Patient patient = (Patient) q2.getSingleResult();

                mainController.setTemp_patient(patient);
                mainController.setTemp_visit(visit);
                mainController.switchScreen("doctor_sumUp_appointment", true);
            }
        } catch (NullPointerException e){
            System.out.println("Nic nie zaznaczono");
        }
    }

    @FXML
    public void showPatient() {
        try {
            UserAppointment selected = appointment_table.getSelectionModel().getSelectedItem();
            if (selected.getVisit_id() > 0) {

                Query q1 = mainController.getEm().createQuery("SELECT v FROM Visit v WHERE v.id = :id");
                q1.setParameter("id", selected.getVisit_id());
                Visit visit = (Visit) q1.getSingleResult();

                Query q2 = mainController.getEm().createQuery("SELECT p FROM Patient p WHERE p.id = :id");
                q2.setParameter("id", visit.getPatient_id());
                Patient patient = (Patient) q2.getSingleResult();

                mainController.setTemp_patient(patient);
                mainController.switchScreen("doctor_show_patient_data", true);
            }
        } catch (NullPointerException e){
            System.out.println("Nic nie zaznaczono");
        }
    }

    public void loadData(){
        try {
            TypedQuery<Visit> query_visits = mainController.getEm().createQuery("SELECT p FROM Visit p WHERE p.doctor_id = :id", Visit.class);
            query_visits.setParameter("id", mainController.getLogged_doctor().getId());

            List<Visit> appointments = query_visits.getResultList();
            Patient temp_patient = null;

            for (Visit temp_visit : appointments) {

                /* Pobieramy dane pacjenta */
                Query q2 = mainController.getEm().createQuery("SELECT p FROM Patient p WHERE p.id = :id");
                q2.setParameter("id", temp_visit.getPatient_id());
                temp_patient = (Patient) q2.getSingleResult();

                appointment_list.add(new UserAppointment(temp_visit.getId(), temp_visit.getVisitType(),
                        "", " ", new SimpleDateFormat("dd/MM/yyyy").format(temp_visit.getVisit_date()),
                        temp_visit.getStatusString(), temp_patient.getName() + " " + temp_patient.getLastname()));

            }
        } catch (Exception e){
            System.out.println("Brak wizyt");
        }

        setCellValuesFactory();
    }

    private void setCellValuesFactory() {
        appointment_type.setCellValueFactory(new PropertyValueFactory<>("appointment_type"));
        patient_name.setCellValueFactory(new PropertyValueFactory<>("patient_name"));
        date.setCellValueFactory(new PropertyValueFactory<>("date"));
        status.setCellValueFactory(new PropertyValueFactory<>("status"));
        appointment_table.setItems(appointment_list);
    }
}