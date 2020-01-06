package Controller.Patient;

import Controller.MainController;
import java_files.Doctor;
import java_files.Visit_description;
import javafx.fxml.FXML;
import javafx.scene.text.Text;

import javax.persistence.Query;
import java.text.SimpleDateFormat;

public class PatientShowVisitDetailsController {

    @FXML
    public Text visitType;
    @FXML
    public Text specialization;
    @FXML
    public Text doctorName;
    @FXML
    public Text doctorLastname;
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
        mainController.switchScreen("patient_view_appointments", true);
    }

    public void loadData(){

        visitType.setText(mainController.getTemp_visit().getVisitType());
        visitStatus.setText(mainController.getTemp_visit().getStatusString());
        visitDate.setText(new SimpleDateFormat("dd/MM/yyyy").format(mainController.getTemp_visit().getVisit_date()));

        Query q1 = mainController.getEm().createQuery("SELECT d FROM Doctor d WHERE d.id = :id");
        q1.setParameter("id", mainController.getTemp_visit().getDoctor_id());
        Doctor doctor = (Doctor) q1.getSingleResult();

        specialization.setText(doctor.getSpecialization());
        doctorName.setText(doctor.getName());
        doctorLastname.setText(doctor.getLastname());

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