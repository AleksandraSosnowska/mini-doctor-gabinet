package Controller.Doctor;

import Controller.MainController;
import java_files.Patient;
import java_files.Visit_description;
import javafx.fxml.FXML;
import javafx.scene.text.Text;

import javax.persistence.Query;
import java.text.SimpleDateFormat;

public class DoctorShowVisitDetailsController {

    @FXML
    public Text visitType;
    @FXML
    public Text patientName;
    @FXML
    public Text patientLastname;
    @FXML
    public Text patientPesel;
    @FXML
    public Text visitStatus;
    @FXML
    public Text visitDate;
    @FXML
    public Text visitDescr;
    @FXML
    public Text visitMeds;

    private MainController mainController;

    @FXML
    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    @FXML
    public void backMenu() {
        mainController.switchScreen("doctor_visit_planner", true);
    }

    public void loadData(){
        System.out.println("jestem");
        System.out.println(mainController.getTemp_visit());

        visitType.setText(mainController.getTemp_visit().getVisitType());
        visitStatus.setText(mainController.getTemp_visit().getStatusString());
        visitDate.setText(new SimpleDateFormat("dd/MM/yyyy").format(mainController.getTemp_visit().getVisit_date()));

        Query q1 = mainController.getEm().createQuery("SELECT p FROM Patient p WHERE p.id = :id");
        q1.setParameter("id", mainController.getTemp_visit().getPatient_id());
        Patient patient = (Patient) q1.getSingleResult();

        patientName.setText(patient.getName());
        patientLastname.setText(patient.getLastname());
        patientPesel.setText(patient.getPesel());

        if(mainController.getTemp_visit().getStatusString().equals("przeszła")) {

            Query q2 = mainController.getEm().createQuery("SELECT vd FROM Visit_description vd WHERE vd.visit_id = :id");
            q2.setParameter("id", mainController.getTemp_visit().getId());
            Visit_description description = (Visit_description) q2.getSingleResult();

            visitMeds.setText(description.getVisit_summary());
            visitDescr.setText(description.getPrescribed_meds());
        }
        else {
            visitMeds.setText("");
            visitDescr.setText("");
        }

    }
}