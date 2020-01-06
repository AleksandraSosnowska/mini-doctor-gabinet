package Controller.Worker;

import Controller.MainController;
import TableClasses.UserAppointment;
import java_files.Doctor;
import java_files.Patient;
import java_files.Visit;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.text.SimpleDateFormat;
import java.util.List;

public class WorkerShowDoctorVisitsController {

    @FXML
    public ChoiceBox choosenDoctor;

    @FXML
    public TableView<UserAppointment> appointment_table;

    @FXML
    public TableColumn<UserAppointment, String> appointment_type;

    @FXML
    public TableColumn <UserAppointment, String> doctor_spec;

    @FXML
    public TableColumn <UserAppointment, String> patient_name;

    @FXML
    public TableColumn <UserAppointment, String> date;

    @FXML
    public TableColumn <UserAppointment, String> status;

    private ObservableList<UserAppointment> appointment_list = FXCollections.observableArrayList();

    private Thread getDataThread;

    private MainController mainController;

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    @FXML
    public void backMenu() {
        mainController.switchScreen("worker_menu", true);
    }

    @FXML
    public void showDetails(){
        //otworzenie okna z szczegółami wizyty + zapis mainController.temp...
        try {
            UserAppointment choosen = appointment_table.getSelectionModel().getSelectedItem();
            System.out.println("Zaznaczona wizyta: " + choosen);

            if (choosen.getVisit_id() > 0) {

                Query q1 = mainController.getEm().createQuery("SELECT v FROM Visit v WHERE v.id = :id");
                q1.setParameter("id", choosen.getVisit_id());
                Visit finder = (Visit) q1.getSingleResult();

                mainController.setTemp_visit(finder);
                mainController.switchScreen("worker_show_details_appointment", true);
            }
        } catch (NullPointerException e){
            System.out.println("Nic nie zaznaczono");
        }
    }

    @FXML
    public void showPatient(){
        //otworzenie okna ze szczegółami pacjenta + zapis mainController.temp...
        try {
            UserAppointment choosen = appointment_table.getSelectionModel().getSelectedItem();
            System.out.println("Zaznaczona wizyta: " + choosen);

            if (choosen.getVisit_id() > 0) {

                Query q1 = mainController.getEm().createQuery("SELECT v FROM Visit v WHERE v.id = :id");
                q1.setParameter("id", choosen.getVisit_id());
                Visit finder = (Visit) q1.getSingleResult();

                Query q2 = mainController.getEm().createQuery("SELECT p FROM Patient p WHERE p.id = :id");
                q2.setParameter("id", finder.getPatient_id());
                Patient finder1 = (Patient) q2.getSingleResult();

                mainController.setTemp_visit(finder);
                mainController.setTemp_patient(finder1);
                mainController.switchScreen("worker_show_patient_appointment", true);
            }
        } catch (NullPointerException e){
            System.out.println("Nic nie zaznaczono");
        }
    }

    public void loadData() {

        TypedQuery<Doctor> query_doctors = mainController.getEm().createQuery("SELECT p FROM Doctor p", Doctor.class);
        List<Doctor> results = query_doctors.getResultList();
        for (Doctor d : results) {
            choosenDoctor.getItems().add(d.getName() + " " + d.getLastname());
        }

        choosenDoctor.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String oldValue, String newValue) {
                System.out.println("Rozpoczynanie wątku");
                appointment_list.clear();
                Controller.Worker.WorkerShowDoctorVisitsController.GetData getData = new Controller.Worker.WorkerShowDoctorVisitsController.GetData();
                getDataThread = new Thread(getData);
                getDataThread.start();
            }
        });

    }

    class GetData implements Runnable {

        @Override
        public void run() {
            String temp_doctor = " ";
            String temp_doctor_name = " ";
            String temp_doctor_lastname = " ";
            try {
                temp_doctor = (String) choosenDoctor.getValue();
                System.out.println("pobrano" + temp_doctor);
                int i = temp_doctor.indexOf(" ");
                temp_doctor_name = temp_doctor.substring(0, i);
                temp_doctor_lastname = temp_doctor.substring(i+1);
            } catch (Exception ignored) {
            }
            if (temp_doctor != null) {

                Query q1 = mainController.getEm().createQuery("SELECT d FROM Doctor d WHERE d.name = :name AND d.lastname = :lastname", Doctor.class);
                q1.setParameter("name", temp_doctor_name);
                q1.setParameter("lastname", temp_doctor_lastname);

                try{
                    Doctor doctor = (Doctor) q1.getSingleResult();

                    TypedQuery<Visit> q2 = mainController.getEm().createQuery("SELECT v FROM Visit v WHERE v.doctor_id = :id", Visit.class);
                    q2.setParameter("id", doctor.getId());

                    try {
                        System.out.println("zapisujemy wyniki");
                        List<Visit> visit_list = q2.getResultList();
                        for(Visit visit: visit_list){
                            if(!visit.getStatusString().equals("oczekująca")){

                                Query q3 = mainController.getEm().createQuery("SELECT p FROM Patient p WHERE p.id = :id", Patient.class);
                                q3.setParameter("id", visit.getPatient_id());
                                Patient patient = (Patient) q3.getSingleResult();

                                appointment_list.add(new UserAppointment(visit.getId(), visit.getVisitType(), doctor.getSpecialization(),
                                        doctor.getName() + " " + doctor.getLastname(),
                                        new SimpleDateFormat("dd/MM/yyyy").format(visit.getVisit_date()),
                                        visit.getStatusString(), patient.getName() + " " + patient.getLastname()));
                            }
                        }
                        setCellValuesFactory();
                    } catch (Exception e) {
                        System.out.println();
                    }
                } catch (Exception ignored){

                }
            }
        }
    }

    private void setCellValuesFactory() {
        appointment_type.setCellValueFactory(new PropertyValueFactory<>("appointment_type"));
        doctor_spec.setCellValueFactory(new PropertyValueFactory<>("doctor_spec"));
        patient_name.setCellValueFactory(new PropertyValueFactory<>("patient_name"));
        date.setCellValueFactory(new PropertyValueFactory<>("date"));
        status.setCellValueFactory(new PropertyValueFactory<>("status"));
        appointment_table.setItems(appointment_list);
    }
}