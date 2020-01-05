package Controller.Worker;

import Controller.MainController;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class WorkerMenuController {

    private MainController mainController;

    @FXML
    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }


    @FXML
    public void ConfirmAppointment() {
        mainController.switchScreen("worker_confirm_appointment", true);
    }

    @FXML
    public void ShowWaitingAppointment() {
        mainController.switchScreen("worker_waiting_appointment", true);
    }

    @FXML
    public void ShowDoctorAppointment() {
        mainController.switchScreen("worker_doctor_appointment", true);
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