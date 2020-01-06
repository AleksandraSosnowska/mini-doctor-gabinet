package Controller;

import Controller.Doctor.*;
import Controller.Patient.*;
import Controller.Worker.*;
import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;
import java_files.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.print.Doc;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class MainController {

    private EntityManagerFactory emf;
    private EntityManager em;

    private Patient logged_patient;
    private Doctor logged_doctor;
    private Worker logged_worker;

    private Patient temp_patient;
    private Doctor temp_doctor;
    private Worker temp_worker;
    private Visit temp_visit;

    @FXML
    private StackPane MainStackPane;

    @FXML
    public void initialize(){

        createDatabase();
        switchScreen("menu", true);
    }

    private void createDatabase() {

        emf = Persistence.createEntityManagerFactory("$objectdb/db/clinicDataBase.odb");
        em = emf.createEntityManager();
        long countClients = 0;

        try {
            Query q1 = em.createQuery("SELECT COUNT(p) FROM Patient p");
            countClients = (long) q1.getSingleResult();
            System.out.println("Liczba klientow w systemie: " + countClients);
            if (countClients == 2) return;
        } catch (Exception e) {
            System.out.println("Brak klientow w systemie, generuję nowych klientów...");
        }

        /*Patient pacjent;
        Doctor doktor;
        Worker pracownik;
        Visit wizyta;
        Visit wizyta2;
        Visit wizyta3;
        em.getTransaction().begin();
        System.out.println("dodaję wizyty");
        try{
           *//* pacjent = new Patient("pacjent2", "pacjent2", "Imię", "Nazwisko", 21, new SimpleDateFormat("dd/MM/yyyy").parse("31/10/1998"), "98103104923", true, "Fabryczna");
            em.persist(pacjent);
            doktor = new Doctor("doktor2", "doktor2", "Imię", "Nazwisko", "Laryngolog", "");
            em.persist(doktor);
            pracownik = new Worker("pracownik", "pracownik", "Imię", "Nazwisko", 4, "Sportowa 20");
            em.persist(pracownik);*//*

            wizyta = new Visit("home",new SimpleDateFormat("dd/MM/yyyy").parse("15/02/2020"), 1,6);
            System.out.println("dodaje wizyte1");
            em.persist(wizyta);
            wizyta2 = new Visit("private",new SimpleDateFormat("dd/MM/yyyy").parse("10/03/2020"), 5,2);
            em.persist(wizyta2);
            wizyta3 = new Visit("nfz",new SimpleDateFormat("dd/MM/yyyy").parse("23/01/2020"), 5,6);
            em.persist(wizyta3);
            em.getTransaction().commit();

        } catch (ParseException e) {
            System.out.println("nie udało sie");
        }*/
    }

    public EntityManager getEm() {
        return em;
    }

    public static void FadeIn(int time, Node X){
        FadeTransition fadein = new FadeTransition();
        fadein.setDuration(Duration.millis(time));
        fadein.setNode(X);
        fadein.setFromValue(0);
        fadein.setToValue(1);
        fadein.play();
    }

    public void setScreen(Pane pane){
        MainStackPane.getChildren().clear();
        MainStackPane.getChildren().add(pane);
    }

    public FXMLLoader getFXMLLoader(String loaderResource){
        FXMLLoader loader = null;
        if (loaderResource.equals("menu"))
            loader = new FXMLLoader(this.getClass().getResource("/fxml/LoginFrame.fxml"));

        if (loaderResource.equals("doctor_menu"))
            loader = new FXMLLoader(this.getClass().getResource("/fxml/Doctor/DoctorMainScreen.fxml"));
        if (loaderResource.equals("doctor_sumUp_appointment"))
            loader = new FXMLLoader(this.getClass().getResource("/fxml/Doctor/DoctorSumUpAppointmentScreen.fxml"));
        if (loaderResource.equals("doctor_main_patient_data"))
            loader = new FXMLLoader(this.getClass().getResource("/fxml/Doctor/DoctorMainPatientDataScreen.fxml"));
        if (loaderResource.equals("doctor_visit_planner"))
            loader = new FXMLLoader(this.getClass().getResource("/FXML/Doctor/DoctorVisitPlannerScreen.fxml"));
        if (loaderResource.equals("doctor_create_description"))
            loader = new FXMLLoader(this.getClass().getResource("/FXML/Doctor/DoctorCreateDescriptionScreen.fxml"));
        if (loaderResource.equals("doctor_show_patient_data"))
            loader = new FXMLLoader(this.getClass().getResource("/FXML/Doctor/DoctorShowPatientScreen.fxml"));
        if (loaderResource.equals("doctor_show_details_appointment")) {
            loader = new FXMLLoader(this.getClass().getResource("/FXML/Doctor/DoctorShowVisitDetailsScreen.fxml"));
            System.out.println("tu jestem");
        }
        if (loaderResource.equals("patient_menu"))
            loader = new FXMLLoader(this.getClass().getResource("/fxml/Patient/PatientMainScreen.fxml"));
        if (loaderResource.equals("patient_account_data"))
            loader = new FXMLLoader(this.getClass().getResource("/FXML/Patient/PatientAccountDataScreen.fxml"));
        if (loaderResource.equals("patient_create_appointments"))
            loader = new FXMLLoader(this.getClass().getResource("/FXML/Patient/PatientCreateVisitScreen.fxml"));
        if (loaderResource.equals("patient_view_appointments"))
            loader = new FXMLLoader(this.getClass().getResource("/fxml/Patient/PatientViewVisitsScreen.fxml"));
        if (loaderResource.equals("patient_show_details_appointments"))
            loader = new FXMLLoader(this.getClass().getResource("/fxml/Patient/PatientShowVisitDetailsScreen.fxml"));

        if (loaderResource.equals("worker_menu"))
            loader = new FXMLLoader(this.getClass().getResource("/fxml/Worker/WorkerMainScreen.fxml"));
        if (loaderResource.equals("worker_confirm_appointment"))
            loader = new FXMLLoader(this.getClass().getResource("/FXML/Worker/WorkerConfirmVisitScreen.fxml"));
        if (loaderResource.equals("worker_waiting_appointment"))
            loader = new FXMLLoader(this.getClass().getResource("/FXML/Worker/WorkerWaitingVisitsScreen.fxml"));
        if (loaderResource.equals("worker_doctor_appointment"))
            loader = new FXMLLoader(this.getClass().getResource("/fxml/Worker/WorkerShowDoctorVisitsScreen.fxml"));
        if (loaderResource.equals("worker_show_details_appointment"))
            loader = new FXMLLoader(this.getClass().getResource("/fxml/Worker/WorkerShowVisitDetailsScreen.fxml"));
        if (loaderResource.equals("worker_show_patient_appointment"))
            loader = new FXMLLoader(this.getClass().getResource("/fxml/Worker/WorkerShowPatientDataScreen.fxml"));

        return loader;
    }

    public void switchScreen(String loaderResource, boolean fadeAnimation){

        FXMLLoader loader = getFXMLLoader(loaderResource);
        Pane pane = null;

        try {
            pane = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (loaderResource.equals("menu")){
            LoginController menuController = loader.getController();
            menuController.setMainController(this);
        }

         if (loaderResource.equals("patient_menu")){
             PatientMenuController menuController = loader.getController();
             menuController.setMainController(this);
         }

         if (loaderResource.equals("patient_account_data")){
             PatientAccountDataController menuController = loader.getController();
             menuController.setMainController(this);
             menuController.loadData();
         }

         if (loaderResource.equals("patient_create_appointments")){
             PatientCreateVisitController menuController = loader.getController();
             menuController.setMainController(this);
             menuController.loadData();
         }

         if (loaderResource.equals("patient_view_appointments")){
             PatientViewVisitsController menuController = loader.getController();
             menuController.setMainController(this);
             menuController.loadData();
         }

         if (loaderResource.equals("patient_show_details_appointments")){
             PatientShowVisitDetailsController menuController = loader.getController();
             menuController.setMainController(this);
             menuController.loadData();
         }

        if (loaderResource.equals("doctor_menu")){
            DoctorMenuController menuController = loader.getController();
            menuController.setMainController(this);
        }

        if (loaderResource.equals("doctor_sumUp_appointment")){
            DoctorSumUpAppointmentController menuController = loader.getController();
            menuController.setMainController(this);
            menuController.loadData();
        }

        if (loaderResource.equals("doctor_create_description")){
            DoctorCreateDescriptionController menuController = loader.getController();
            menuController.setMainController(this);
        }

        if (loaderResource.equals("doctor_show_patient_data")){
            DoctorShowPatientController menuController = loader.getController();
            menuController.setMainController(this);
            menuController.loadData();
        }

        if (loaderResource.equals("doctor_main_patient_data")){
            DoctorMainPatientDataController menuController = loader.getController();
            menuController.setMainController(this);
        }

        if (loaderResource.equals("doctor_visit_planner")){
            DoctorVisitPlannerController menuController = loader.getController();
            menuController.setMainController(this);
            menuController.loadData();
        }

        if(loaderResource.equals("doctor_show_details_appointment")){
            DoctorShowVisitDetailsController menuController = loader.getController();
            menuController.setMainController(this);
            menuController.loadData();
        }

        if(loaderResource.equals("worker_menu")){
            WorkerMenuController menuController = loader.getController();
            menuController.setMainController(this);
        }

         if (loaderResource.equals("worker_confirm_appointment")){
             WorkerConfirmVisitController menuController = loader.getController();
             menuController.setMainController(this);
             menuController.loadData();
         }

         if (loaderResource.equals("worker_waiting_appointment")){
             WorkerWaitingVisitsController menuController = loader.getController();
             menuController.setMainController(this);
             menuController.loadData();
         }

         if (loaderResource.equals("worker_doctor_appointment")){
             WorkerShowDoctorVisitsController menuController = loader.getController();
             menuController.setMainController(this);
             menuController.loadData();
         }

         if (loaderResource.equals("worker_show_details_appointment")){
             WorkerShowVisitDetailsController menuController = loader.getController();
             menuController.setMainController(this);
             menuController.loadData();
         }

         if (loaderResource.equals("worker_show_patient_appointment")){
             WorkerShowPatientDataController menuController = loader.getController();
             menuController.setMainController(this);
             menuController.loadData();
         }

        setScreen(pane);
        if (fadeAnimation) {
            FadeIn(1000, MainStackPane.getChildren().get(0));
        }
    }


    public Patient getLogged_patient() {
        return logged_patient;
    }

    public void setLogged_patient(Patient logged_patient) {
        this.logged_patient = logged_patient;
    }

    public Doctor getLogged_doctor() {
        return logged_doctor;
    }

    public void setLogged_doctor(Doctor logged_doctor) {
        this.logged_doctor = logged_doctor;
    }

    public Worker getLogged_worker() {
        return logged_worker;
    }

    public void setLogged_worker(Worker logged_worker) {
        this.logged_worker = logged_worker;
    }

    public void setTemp_patient(Patient temp_patient){ this.temp_patient = temp_patient; }

    public Patient getTemp_patient() { return temp_patient; }

    public void setTemp_doctor(Doctor temp_doctor){ this.temp_doctor = temp_doctor; }

    public Doctor getTemp_doctor() { return temp_doctor; }

    public void setTemp_worker(Worker temp_worker){ this.temp_worker = temp_worker; }

    public Worker getTemp_worker() { return temp_worker; }

    public void setTemp_visit(Visit temp_visit){ this.temp_visit = temp_visit; }

    public Visit getTemp_visit() { return temp_visit; }
}
