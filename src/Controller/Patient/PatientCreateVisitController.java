package Controller.Patient;

import Controller.MainController;
import java_files.Doctor;
import java_files.Visit;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class PatientCreateVisitController {

    @FXML
    public TextField dateInput;

    @FXML
    public ChoiceBox typeInput;

    @FXML
    public ChoiceBox specializationInput;

    @FXML
    public ChoiceBox doctorInput;

    @FXML
    public Text errorDate;

    @FXML
    public Text error1;

    @FXML
    public Text error2;

    @FXML
    public Text error3;

    @FXML
    public Text alreadyExist;

    private Thread getDoctorsThread;

    private MainController mainController;

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    @FXML
    public void backMenu(ActionEvent actionEvent) {
        /*try {
            getDoctorsThread.stop();
        } catch (Exception ignored) {
        }*/
        mainController.switchScreen("patient_menu", true);
    }

    @FXML
    public void createAppoitment() {
        String read_date = dateInput.getText();
        String new_type = null;
        String new_doctor = null;
        Date new_date = null;
        boolean correctDate = true;

        if (typeInput.getValue() != null) {
            if (specializationInput.getValue() != null){
                if(doctorInput.getValue() != null){
                    if(read_date.length() == 10){
                        try {
                            new_date = new SimpleDateFormat("dd/MM/yyyy").parse(read_date);
                            new_type = (String) typeInput.getValue();
                            new_doctor = (String) doctorInput.getValue();

                            TypedQuery<Visit> query_visits = mainController.getEm().createQuery("SELECT v FROM Visit v WHERE v.patient_id = :id", Visit.class);
                            query_visits.setParameter("id", mainController.getLogged_patient().getId());
                            List<Visit> results = query_visits.getResultList();
                            for (Visit v : results) {
                                if(v.getVisit_date().equals(new_date)) correctDate = false;
                            }

                            if(correctDate == true) {
                                Query q1 = mainController.getEm().createQuery("SELECT d FROM Doctor d WHERE d.lastname = :nazwa");
                                q1.setParameter("nazwa", new_doctor);
                                Doctor choosen_doctor = (Doctor) q1.getSingleResult();

                                mainController.getEm().getTransaction().begin();
                                Visit new_appointment = new Visit(new_type, new_date, mainController.getLogged_patient().getId(), choosen_doctor.getId());
                                mainController.getEm().persist(new_appointment);
                                mainController.getEm().getTransaction().commit();
                                System.out.println("Dodano wizyte");
                                mainController.switchScreen("patient_create_appointments", true);
                            } else {
                                alreadyExist.setText("Posiadasz już wizytę w wybranym dniu.");
                                error1.setText("");
                                error2.setText("");
                                error3.setText("");
                                errorDate.setText("");
                            }

                        } catch (Exception e) {
                            error1.setText("");
                            error2.setText("");
                            error3.setText("");
                            alreadyExist.setText("");
                            errorDate.setText("błędna data!");
                        }
                    } else {
                        error1.setText("");
                        error2.setText("");
                        error3.setText("");
                        alreadyExist.setText("");
                        errorDate.setText("błędna data!");
                    }
                } else {
                    error1.setText("");
                    error2.setText("");
                    error3.setText("brak danych");
                    alreadyExist.setText("");
                    errorDate.setText("");
                }
            } else {
                error1.setText("");
                error2.setText("brak danych");
                error3.setText("");
                alreadyExist.setText("");
                errorDate.setText("");
            }
        } else {
            error1.setText("brak danych");
            error2.setText("");
            error3.setText("");
            alreadyExist.setText("");
            errorDate.setText("");
        }
    }

    public void loadData() {

        /* Przesyłamy do choice boxów informacje o możliwych typach wizyty i specjalizacjach lekarzy */
        errorDate.setText("");
        error1.setText("");
        error2.setText("");
        error3.setText("");
        alreadyExist.setText("");

        typeInput.getItems().add("prywatna");
        typeInput.getItems().add("domowa");

        if(mainController.getLogged_patient().getInsurance_status() == true)
            typeInput.getItems().add("nfz");

        specializationInput.getItems().add("Lekarz rodzinny");
        specializationInput.getItems().add("Kardiolog");
        specializationInput.getItems().add("Laryngolog");
        specializationInput.getItems().add("Ortopeda");
        specializationInput.getItems().add("Dermatolog");
        specializationInput.getItems().add("Okulista");

        specializationInput.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String oldValue, String newValue) {
                System.out.println("Rozpoczynanie wątku");
                doctorInput.getItems().clear();
                GetDoctors getDoctors = new GetDoctors();
                getDoctorsThread = new Thread(getDoctors);
                getDoctorsThread.start();
            }
        });

    }

    class GetDoctors implements Runnable {

        @Override
        public void run() {
            String temp_specialization = " ";
            try {
                temp_specialization = (String) specializationInput.getValue();
                System.out.println("pobrano" + temp_specialization);
            } catch (Exception ignored) {
            }
            if (temp_specialization != null) {

                TypedQuery<Doctor> q1 = mainController.getEm().createQuery("SELECT d FROM Doctor d WHERE d.specialization = :nazwa", Doctor.class);
                q1.setParameter("nazwa", temp_specialization);

                try {
                    System.out.println("zapisujemy wyniki");
                    List<Doctor> doctors_list = q1.getResultList();
                    Doctor doctor;
                    while (!doctors_list.isEmpty()) {
                        doctor = doctors_list.get(0);
                        if (!doctorInput.getItems().contains(doctor.getLastname())) {
                            doctorInput.getItems().add(doctor.getLastname());
                        }
                        doctors_list.remove(0);
                    }
                } catch (Exception e) {
                    System.out.println();
                }
            }
        }
    }

}