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

import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.text.SimpleDateFormat;
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

        try {
            String new_date = dateInput.getText();
            String new_type = (String) typeInput.getValue();
            String new_spec = (String) specializationInput.getValue();
            String new_doctor = (String) doctorInput.getValue();


            if (new_date.length() == 10 && new_type.length() > 2 && new_spec.length() > 2 && new_doctor.length() > 2) {

                Query q1 = mainController.getEm().createQuery("SELECT d FROM Doctor d WHERE d.lastname = :nazwa");
                q1.setParameter("nazwa", new_doctor);
                Doctor choosen_doctor = (Doctor) q1.getSingleResult();

                mainController.getEm().getTransaction().begin();
                Visit new_appointment = new Visit(new_type, new SimpleDateFormat("dd/MM/yyyy").parse(new_date), mainController.getLogged_patient().getId(), choosen_doctor.getId());
                mainController.getEm().persist(new_appointment);
                mainController.getEm().getTransaction().commit();
                System.out.println("Dodano wizyte");
//                getDoctorsThread.stop();
                mainController.switchScreen("patient_create_appointments", true);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void loadData() {

        /* Przesyłamy do choice boxów informacje o możliwych typach wizyty i specjalizacjach lekarzy */

        typeInput.getItems().add("private");
        typeInput.getItems().add("home");
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
            String prev_specialization = " ";
            String temp_specialization = " ";
            try {
                temp_specialization = (String) specializationInput.getValue();
                System.out.println("pobrano" + temp_specialization);
            } catch (Exception ignored) {
            }
            if (temp_specialization != null) {

                prev_specialization = temp_specialization;
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