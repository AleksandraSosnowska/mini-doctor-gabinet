package Controller.Patient;

import Controller.MainController;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class PatientMenuController {

    private MainController mainController;

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    @FXML
    public void ViewAppoitments(ActionEvent actionEvent) {
        mainController.switchScreen("patient_view_appointments", true);
    }

    @FXML
    public void CreateAppoitment(ActionEvent actionEvent) {
        mainController.switchScreen("patient_create_appointments", true);
    }

    @FXML
    public void ViewAccountData(ActionEvent actionEvent) {
        mainController.switchScreen("patient_account_data", true);
    }

    @FXML
    public void log_out(ActionEvent actionEvent) {
        mainController.setLogged_patient(null);
        mainController.switchScreen("menu", true);
    }

    @FXML
    public void exit(ActionEvent actionEvent) {
        Platform.exit();
        System.exit(0);
    }

}