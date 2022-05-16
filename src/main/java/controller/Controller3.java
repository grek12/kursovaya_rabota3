package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DialogPane;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.DAOService;
import model.Service;


public class Controller3 {//класс контроллер формы добавления услуги


    @FXML
    private TextField nameServiceField;

    @FXML
    private TextField priceServiceField;
    @FXML
    private TextField desServiceField;

    @FXML
    private Controller2 mainController;


    @FXML
    private void initialize() {

    }

    @FXML
    public void setMainController(Controller2 mainController) {
        this.mainController = mainController;
    }//получение данных из 2-го контроллера

    @FXML
    private void handleCancel(javafx.event.ActionEvent event) {//кнопка отмены
        ((Stage) (((Button) event.getSource()).getScene().getWindow())).close();
    }


    private boolean isInputValid() {//проверка условий
        String errorMessage = "";

        if (nameServiceField.getText() == null || nameServiceField.getText().length() == 0) {
            errorMessage += "Заполните поле названия услуги!\n";
        }
        if (priceServiceField.getText() == null || priceServiceField.getText().length() == 0) {
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

    @FXML
    public void handleOk(javafx.event.ActionEvent event) {//нажатие кнопки ок

        Service service = new Service();
        if (isInputValid()) {
            service.setTypeservice(nameServiceField.getText());
            service.setPrice(Float.parseFloat(priceServiceField.getText()));
            service.setDescription(desServiceField.getText());

            try {
                DAOService dao = new DAOService();
                dao.insertService(service);
                mainController.updateTable();
                mainController.DialogInfo("Услуга успешно добавлена!");


            } catch (Exception e) {
                mainController.DialogError("Не удалось добавить услугу!");

                e.printStackTrace();
            }

        }

        ((Stage) (((Button) event.getSource()).getScene().getWindow())).close();
    }


    public void test() {//ограничение ввода
        nameServiceField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\sa-zA-ZА-Яа-я*")) {
                nameServiceField.setText(newValue.replaceAll("[^\\sa-zA-ZА-Яа-я]", ""));
            }
        });
    }

    public void test2() {//ограничение ввода
        priceServiceField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\.0123456789*")) {
                priceServiceField.setText(newValue.replaceAll("[^\\.0123456789]", ""));
            }
        });
    }

}
