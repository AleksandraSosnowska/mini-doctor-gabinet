package Controller.Patient;

import Controller.MainController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import java_files.Patient;

import javax.persistence.Query;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PatientAccountDataController {

    @FXML
    public Text patientName;

    @FXML
    public Text patientLastname;

    @FXML
    public Text patientDate;

    @FXML
    public Text patientAddress;

    @FXML
    public Text patientStatus;

    @FXML
    public Text error;

    @FXML
    public TextField change_input1;

    @FXML
    public TextField change_input2;

    @FXML
    public TextField change_input3;

    @FXML
    public TextField change_input4;

    private MainController mainController;

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    @FXML
    public void doChange() {
        try{
            String new_name = change_input1.getText();
            String new_lastname = change_input2.getText();
            String new_date = change_input3.getText();
            String new_address = change_input4.getText();

            if (new_name.length() > 1 || new_lastname.length() > 1 || new_date.length() > 1 || new_address.length() > 1) {

                /* Dokonujemy zmiany w koncie pacjenta*/
                Patient changedPatient = mainController.getEm().find(Patient.class, mainController.getLogged_patient());

                mainController.getEm().getTransaction().begin();
                if(new_name.length() > 1)   changedPatient.setName(new_name);
                if(new_lastname.length() > 1)   changedPatient.setLastname(new_lastname);
                if(new_address.length() > 1)   changedPatient.setHome_address(new_address);
                if(new_date.length() == 10){
                    try {
                        changedPatient.setBirth_date(new SimpleDateFormat("dd/MM/yyyy").parse(new_date));
                    } catch (ParseException e) {
                        error.setText("Błędny format daty!");
                        System.out.println("Błędny format daty!");
                    }
                }
                mainController.getEm().getTransaction().commit();

                /* Załadowanie strony od nowa */
                mainController.switchScreen("patient_account_data", true);
            } else {
                error.setText("Brak danych");
                System.out.println("Brak danych");
            }
        } catch (Exception e){
            System.out.println("Błędne dane");
        }
    }

    @FXML
    public void backMenu() {
        mainController.switchScreen("patient_menu", true);
    }

    public void loadData(){

        error.setText("");
        /*wczytywanie danych pacjenta*/
        Query q1 =  mainController.getEm().createQuery("SELECT p FROM Patient p WHERE p.id = :id");
        q1.setParameter("id", mainController.getLogged_patient().getId());

        Patient patient = (Patient) q1.getSingleResult();

        patientName.setText(patient.getName());
        patientLastname.setText(patient.getLastname());
        Date data_urodzin = patient.getBirth_date();
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyy");
        patientDate.setText(dateFormat.format(data_urodzin));
        patientAddress.setText(patient.getHome_address());
        if(patient.getInsurance_status() == true){
            patientStatus.setText("Ubezpieczony");
        }
        else
            patientStatus.setText("Brak ubezpieczenia");

    }
}