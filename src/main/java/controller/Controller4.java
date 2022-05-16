package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DialogPane;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import model.DAOService;
import model.Service;

public class Controller4 {//класс контроллер формы изменение данных услуги
    @FXML
    private TextField serviceNameField;

    @FXML
    private TextField servicePriceField;
    @FXML
    private TextField desServiceField;
    @FXML
    private Controller2 mainController;
    @FXML
    private TextField Id;

    private Service service;

    @FXML
    private void initialize() {
    }


    public void setPerson(Service service) {//получение данных
        this.service = service;

        serviceNameField.setText(service.getTypeservice());
        servicePriceField.setText(String.valueOf(service.getPrice()));
        desServiceField.setText(service.getDescription());
        Id.setText(String.valueOf(service.getId()));
    }

    public void setMainController(Controller2 mainController) {
        this.mainController = mainController;
    }//получение данных из 2-го контроллера


    @FXML
    private void handleCancel(javafx.event.ActionEvent event) {//кнопка отмены
        ((Stage) (((Button) event.getSource()).getScene().getWindow())).close();
    }

    private boolean isInputValid() {//проверка условий
        String errorMessage = "";

        if (serviceNameField.getText() == null || serviceNameField.getText().length() == 0) {
            errorMessage += "Заполните поле названия услуги!\n";
        }
        if (servicePriceField.getText() == null || servicePriceField.getText().length() == 0) {
            errorMessage += "Заполните поле стоимости услуги!\n";
        }
        if (desServiceField.getText() == null || desServiceField.getText().length() == 0) {
            errorMessage += "Заполните поле описания услуги!\n";
        }
        if (errorMessage.length() == 0) {
            return true;
        } else {

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Поля ввода не заполнены!");
            alert.setHeaderText("Заполните все поля ввода!");
            alert.setContentText(errorMessage);
            DialogPane dialogPane = alert.getDialogPane();
            dialogPane.getStylesheets().add(
                    getClass().getResource("/fullpackstyling.css").toExternalForm());
            dialogPane.getStyleClass().add("myDialog");
            alert.showAndWait();

            return false;
        }
    }

    public void handleOk(javafx.event.ActionEvent event) {//нажатие кнопки ок

        if (isInputValid()) {
            service.setTypeservice(serviceNameField.getText());
            service.setPrice(Float.parseFloat(servicePriceField.getText()));
            service.setDescription(desServiceField.getText());
            try {
                DAOService dao = new DAOService();
                dao.updateService(service);
                mainController.updateTable();
                mainController.DialogInfo("Данные услуги успешно изменены! ");

            } catch (Exception e) {
                mainController.DialogError("Не удалось изменить данные услуги!");
                e.printStackTrace();
            }

        }

        ((Stage) (((Button) event.getSource()).getScene().getWindow())).close();
    }


    public void test() {//ограничение ввода
        serviceNameField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\sa-zA-ZА-Яа-я*")) {
                serviceNameField.setText(newValue.replaceAll("[^\\sa-zA-ZА-Яа-я]", ""));
            }
        });
        serviceNameField.setOnKeyPressed(event -> {
            if (event.getCode().equals(KeyCode.ENTER)) {
                servicePriceField.requestFocus();
            }
        });

    }

    public void test2() {//ограничение ввода
        servicePriceField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\.0123456789*")) {
                servicePriceField.setText(newValue.replaceAll("[^\\.0123456789]", ""));
            }
        });
        servicePriceField.setOnKeyPressed(event -> {
            if (event.getCode().equals(KeyCode.ENTER)) {
                desServiceField.requestFocus();
            }
        });
    }


}
