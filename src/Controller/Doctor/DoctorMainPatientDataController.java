package Controller.Doctor;

import Controller.MainController;
import javafx.fxml.FXML;
import java_files.*;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import javax.persistence.Query;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DoctorMainPatientDataController {

    @FXML
    public TextField patientPesel;

    @FXML
    public Text patientName;

    @FXML
    public Text patientLastname;

    @FXML
    public Text patientAge;

    @FXML
    public Text patientDate;

    @FXML
    public Text patientAddress;

    @FXML
    public Text patientStatus;

    @FXML
    public Text info;

    private MainController mainController;

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    public void initialize(){
        info.setText(" ");
    }

    @FXML
    public void backMenu() {
        mainController.switchScreen("doctor_menu", true);
    }

    @FXML
    public void loadData(){

        try{
            String new_pesel = patientPesel.getText();

            if(new_pesel.length() == 11){

                Query q1 =  mainController.getEm().createQuery("SELECT p FROM Patient p WHERE p.pesel = :pesel");
                q1.setParameter("pesel", new_pesel);

                try{
                    info.setText("");
                    Patient patient = (Patient) q1.getSingleResult();
                    patientName.setText(patient.getName());
                    patientLastname.setText(patient.getLastname());
                    patientAge.setText(Integer.toString(patient.getAge()));
                    Date data_urodzin = patient.getBirth_date();
                    patientDate.setText(new SimpleDateFormat("dd/MM/yyy").format(data_urodzin));
                    patientAddress.setText(patient.getHome_address());
                    if (patient.getInsurance_status() == true) {
                        patientStatus.setText("Ubezpieczony");
                    } else
                        patientStatus.setText("Brak ubezpieczenia zdrowotnego");

                } catch (Exception e){
                    info.setText("Brak pacjenta o podanym numerze pesel");
                    clearTexts();
                }
            } else {
                info.setText("Błędny numer pesel");
                clearTexts();
            }

        }catch(NullPointerException e){
            System.out.println("Nie wprowadzono danych");
        }
    }
    private void clearTexts(){
        patientName.setText("imie");
        patientLastname.setText("nazwisko");
        patientAge.setText("wiek");
        patientDate.setText("data urodzenia");
        patientAddress.setText("adres zamieszkania");
        patientStatus.setText("status ubezpieczenia");
    }
}