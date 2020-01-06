package Controller.Worker;

import Controller.MainController;
import java_files.Patient;
import javafx.fxml.FXML;
import javafx.scene.text.Text;

import javax.persistence.Query;
import java.text.SimpleDateFormat;
import java.util.Date;

public class WorkerShowPatientDataController {

    @FXML
    public Text patientName;

    @FXML
    public Text patientLastname;

    @FXML
    public Text patientPesel;

    @FXML
    public Text patientAge;

    @FXML
    public Text patientDate;

    @FXML
    public Text patientAddress;

    @FXML
    public Text patientStatus;

    private MainController mainController;

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    @FXML
    public void backMenu() {
        mainController.switchScreen("worker_doctor_appointment", true);
    }

    public void loadData(){

        Query q1 =  mainController.getEm().createQuery("SELECT p FROM Patient p WHERE p.id = :id");
        q1.setParameter("id", mainController.getTemp_patient().getId());

        Patient patient = (Patient) q1.getSingleResult();

        patientName.setText(patient.getName());
        patientLastname.setText(patient.getLastname());
        patientAge.setText(Integer.toString(patient.getAge()));
        Date data_urodzin = patient.getBirth_date();
        patientDate.setText(new SimpleDateFormat("dd/MM/yyy").format(data_urodzin));
        patientPesel.setText(patient.getPesel());
        patientAddress.setText(patient.getHome_address());
        if(patient.getInsurance_status() == true){
            patientStatus.setText("Ubezpieczony");
        }
        else
            patientStatus.setText("Brak ubezpieczenia");

    }
}