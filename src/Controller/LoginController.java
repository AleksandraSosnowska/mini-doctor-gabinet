package Controller;

import java_files.Visit;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import java_files.Patient;
import java_files.Doctor;
import java_files.Worker;

import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;

public class LoginController {

    @FXML
    public PasswordField password_input;

    @FXML
    public TextField login_input;

    @FXML
    public Text info;

    private MainController mainController;

    public void initialize(){
        info.setText(" ");
    }

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }


    @FXML
    public void zaloguj_do_systemu(ActionEvent actionEvent) {

        /* Pacjenci w systemie */

        TypedQuery<Patient> query_patients = mainController.getEm().createQuery("SELECT p FROM Patient p", Patient.class);
        List<Patient> results = query_patients.getResultList();
        for (Patient p : results) {
            System.out.println(p);
        }

        /* Lekarze w systemie */
        TypedQuery<Doctor> query_doctors = mainController.getEm().createQuery("SELECT p FROM Doctor p", Doctor.class);
        List<Doctor> results2 = query_doctors.getResultList();
        for (Doctor p : results2) {
            System.out.println(p);
        }

        /* Recepcjoniści w systemie */
        TypedQuery<Worker> query_workers = mainController.getEm().createQuery("SELECT p FROM Worker p", Worker.class);
        List<Worker> results3 = query_workers.getResultList();
        for (Worker r : results3) {
            System.out.println(r);
        }

        /* Wizyty w systemie */
        TypedQuery<Visit> query_visits = mainController.getEm().createQuery("SELECT v FROM Visit v", Visit.class);
        List<Visit> results4 = query_visits.getResultList();
        for (Visit v : results4) {
            System.out.println(v);
        }

        String login = login_input.getText();
        String password = password_input.getText();

        System.out.println("login: " + login + "\nhasło: " + password);

        if (login.length() > 0 && password.length() > 0) {

            /* Sprawdzamy w bazie danych czy jest taki użytkownik - pacjent*/
            Query q1 =  mainController.getEm().createQuery("SELECT p FROM Patient p WHERE p.password = :password AND p.login = :login", Patient.class);
            q1.setParameter("password", password);
            q1.setParameter("login", login);

            try{
                mainController.setLogged_patient((Patient) q1.getSingleResult());
                mainController.switchScreen("patient_menu", true);
                System.out.println("Zalogowano klienta.");
                System.out.println(mainController.getLogged_patient());
                return;
            } catch (Exception e){
                System.out.println("Nie wykryto pacjenta, sprawdzamy czy lekarz");
            }

            /* Sprawdzamy w bazie danych czy jest taki użytkownik - lekarz*/
            Query q2 =  mainController.getEm().createQuery("SELECT d FROM Doctor d WHERE d.password = :password AND d.login = :login", Doctor.class);
            q2.setParameter("password", password);
            q2.setParameter("login", login);

            try{
                mainController.setLogged_doctor((Doctor) q2.getSingleResult());
                mainController.switchScreen("doctor_menu", true);
                System.out.println("Zalogowano lekarza.");
                System.out.println(mainController.getLogged_doctor());
                return;
            } catch (Exception e){
                System.out.println("Nie wykryto lekarza, sprawdzamy czy recepcjonista");
            }

            /* Sprawdzamy w bazie danych czy jest taki użytkownik - recepcjonista*/
            Query q3 =  mainController.getEm().createQuery("SELECT p FROM Worker p WHERE p.password = :password AND p.login = :login");
            q2.setParameter("password", password);
            q2.setParameter("login", login);

            try{
                mainController.setLogged_worker((Worker) q3.getSingleResult());
                //mainController.switchScreen("menu_pracownika", true);
                System.out.println("Zalogowano recepcjonistę.");
                System.out.println(mainController.getLogged_worker());
                return;
            } catch (Exception e){
                System.out.println("Nie wykryto użytkownika. Popraw dane");
            }
            info.setText("Błędny login lub hasło");
        } else {
            info.setText("Uzupełnij pola tekstowe");
        }
    }

    @FXML
    public void exit(ActionEvent actionEvent) {
        Platform.exit();
        System.exit(0);
    }

}
