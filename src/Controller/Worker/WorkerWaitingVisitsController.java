package Controller.Worker;

import Controller.MainController;
import TableClasses.UserAppointment;
import java_files.Patient;
import java_files.Visit;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import java_files.Doctor;

import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.text.SimpleDateFormat;
import java.util.List;

public class WorkerWaitingVisitsController {

    @FXML
    public TableView<UserAppointment> appointment_table;

    @FXML
    public TableColumn<UserAppointment, String> appointment_type;

    @FXML
    public TableColumn <UserAppointment, String> doctor_spec;

    @FXML
    public TableColumn <UserAppointment, String> doctor_name;

    @FXML
    public TableColumn <UserAppointment, String> patient_name;

    @FXML
    public TableColumn <UserAppointment, String> date;

    @FXML
    public TableColumn <UserAppointment, String> status;

    private ObservableList<UserAppointment> appointment_list = FXCollections.observableArrayList();

    private MainController mainController;

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    @FXML
    public void backMenu() {
        mainController.switchScreen("worker_menu", true);
    }

    @FXML
    public void declineAppoitment() {
        try {
            UserAppointment choosen = appointment_table.getSelectionModel().getSelectedItem();
            System.out.println("Zaznaczona wizyta: " + choosen);

            if (choosen.getVisit_id() > 0) {

                Query q1 = mainController.getEm().createQuery("SELECT v FROM Visit v WHERE v.id = :id");
                q1.setParameter("id", choosen.getVisit_id());
                Visit finder = (Visit) q1.getSingleResult();

                Visit new_visit = mainController.getEm().find(Visit.class, finder);

                mainController.getEm().getTransaction().begin();
                new_visit.setStatus("declined");
                mainController.getEm().getTransaction().commit();
                mainController.switchScreen("worker_waiting_appointment", true);
            }
        } catch (NullPointerException e){
            System.out.println("Nic nie zaznaczono");
        }
    }
    @FXML
    public void confirmAppoitment(){
        try {
            UserAppointment choosen = appointment_table.getSelectionModel().getSelectedItem();
            System.out.println("Zaznaczona wizyta: " + choosen);

            if (choosen.getVisit_id() > 0) {

                Query q1 = mainController.getEm().createQuery("SELECT v FROM Visit v WHERE v.id = :id");
                q1.setParameter("id", choosen.getVisit_id());
                Visit finder = (Visit) q1.getSingleResult();

                Visit new_visit = mainController.getEm().find(Visit.class, finder);

                mainController.getEm().getTransaction().begin();
                new_visit.setStatus("confirmed");
                mainController.getEm().getTransaction().commit();
                mainController.switchScreen("worker_waiting_appointment", true);
            }
        } catch (NullPointerException e){
            System.out.println("Nic nie zaznaczono");
        }
    }

    public void loadData(){
        try {
            TypedQuery<Visit> query_visits = mainController.getEm().createQuery("SELECT v FROM Visit v", Visit.class);
            //query_visits.setParameter("status", "WAITING");

            List<Visit> visit_list = query_visits.getResultList();
            Doctor temp_doctor = null;
            Patient temp_patient = null;

            for (Visit temp_visit : visit_list) {

                System.out.println(temp_visit);
                if(temp_visit.getStatusString().equals("oczekująca")){
                    /* Pobieramy dane lekarza */
                    Query q2 = mainController.getEm().createQuery("SELECT d FROM Doctor d WHERE d.id = :id");
                    q2.setParameter("id", temp_visit.getDoctor_id());
                    temp_doctor = (Doctor) q2.getSingleResult();

                    Query q3 = mainController.getEm().createQuery("SELECT p FROM Patient p WHERE p.id = :id");
                    q3.setParameter("id", temp_visit.getPatient_id());
                    temp_patient = (Patient) q3.getSingleResult();

                    appointment_list.add(new UserAppointment(temp_visit.getId(), temp_visit.getVisitType(),
                            temp_doctor.getSpecialization(), temp_doctor.getName() + " " + temp_doctor.getLastname(),
                            new SimpleDateFormat("dd/MM/yyyy").format(temp_visit.getVisit_date()), temp_visit.getStatusString(),
                            temp_patient.getName() + " " + temp_patient.getLastname()));
                }

            }

        }catch (Exception e){
            System.out.println("Brak wizyt");
        }

        setCellValuesFactory();
    }
    private void setCellValuesFactory() {
        appointment_type.setCellValueFactory(new PropertyValueFactory<>("appointment_type"));
        doctor_spec.setCellValueFactory(new PropertyValueFactory<>("doctor_spec"));
        doctor_name.setCellValueFactory(new PropertyValueFactory<>("doctor_name"));
        patient_name.setCellValueFactory(new PropertyValueFactory<>("patient_name"));
        date.setCellValueFactory(new PropertyValueFactory<>("date"));
        status.setCellValueFactory(new PropertyValueFactory<>("status"));
        appointment_table.setItems(appointment_list);
    }

}