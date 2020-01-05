package Controller.Doctor;

import Controller.MainController;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class DoctorMenuController {

    private MainController mainController;

    @FXML
    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    @FXML
    public void AppoitmentPlanner() {
        mainController.switchScreen("doctor_visit_planner", true);
    }

    @FXML
    public void SumUpAppoinment() {
        mainController.switchScreen("doctor_create_description", true);
    }

    @FXML
    public void ShowPatientData() {
        mainController.switchScreen("doctor_main_patient_data", true);
    }

    @FXML
    public void exit(ActionEvent actionEvent) {
        Platform.exit();
        System.exit(0);
    }

    @FXML
    public void log_out(ActionEvent actionEvent) {
        mainController.setLogged_doctor(null);
        mainController.switchScreen("menu", true);
    }
}