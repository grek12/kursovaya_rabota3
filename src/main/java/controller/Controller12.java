package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DialogPane;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.DAOLawyer;
import model.Lawyer;

public class Controller12 {//класс контроллер формы изменения данных мастера

    @FXML
    private TextField experienceField;

    @FXML
    private TextField firstNameField;

    @FXML
    private TextField idField;

    @FXML
    private TextField lastNameField;

    @FXML
    private TextField numberPhoneField;

    @FXML
    private TextField patronymicField;
    @FXML
    private Controller10 mainController;
    private Controller2 controller2;
    private Lawyer lawyer;


    @FXML
    public void setMainController(Controller10 mainController) {
        this.mainController = mainController;
    }//получение данных от 10-го контроллера

    public void setPerson(Lawyer lawyer) {//получение данных мастера
        this.lawyer = lawyer;

        lastNameField.setText(lawyer.getFirstName());
        firstNameField.setText(lawyer.getLastName());
        patronymicField.setText(lawyer.getPatronymic());
        experienceField.setText(String.valueOf(lawyer.getExperience()));
        numberPhoneField.setText(lawyer.getNumberPhone());
        idField.setText(String.valueOf(lawyer.getId()));
    }


    @FXML
    private void handleCancel(javafx.event.ActionEvent event) {//нажатие кнопки отмены
        ((Stage) (((Button) event.getSource()).getScene().getWindow())).close();
    }

    private boolean isInputValid() {//проверка условий
        String errorMessage = "";

        if (firstNameField.getText() == null || firstNameField.getText().length() == 0) {
            errorMessage += "Заполните поле фамилии мастера!\n";
        }
        if (lastNameField.getText() == null || lastNameField.getText().length() == 0) {
            errorMessage += "Заполните поле имени мастера!\n";
        }
        if (patronymicField.getText() == null || patronymicField.getText().length() == 0) {
            errorMessage += "Заполните поле отчества мастера!\n";
        }

        if (experienceField.getText() == null || experienceField.getText().length() == 0) {
            errorMessage += "Заполните поле стажа работы мастера!\n";
        }
        if (numberPhoneField.getText() == null || numberPhoneField.getText().length() == 0) {
            errorMessage += "Заполните поле номера телефона мастера!\n";
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
    public void handleOk(javafx.event.ActionEvent event) {//нажатие кнопки ОК


        if (isInputValid()) {
            lawyer.setFirstName(firstNameField.getText());
            lawyer.setLastName(lastNameField.getText());
            lawyer.setPatronymic(patronymicField.getText());
            lawyer.setExperience(Integer.parseInt(experienceField.getText()));
            lawyer.setNumberPhone(numberPhoneField.getText());

            try {
                DAOLawyer dao = new DAOLawyer();
                dao.updateLawyer(lawyer);
                mainController.updateTable();
                mainController.DialogInfo("Данные мастера успешно изменены!");


            } catch (Exception e) {
                mainController.DialogError("Не удалось изменить данные мастера!");

                e.printStackTrace();
            }

        }

        ((Stage) (((Button) event.getSource()).getScene().getWindow())).close();
    }

    public void test() {//ограничение ввода
        lastNameField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\sa-zA-ZА-Яа-я*")) {
                lastNameField.setText(newValue.replaceAll("[^\\sa-zA-ZА-Яа-я]", ""));
            }
        });
        firstNameField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\sa-zA-ZА-Яа-я*")) {
                firstNameField.setText(newValue.replaceAll("[^\\sa-zA-ZА-Яа-я]", ""));
            }
        });
        patronymicField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\sa-zA-ZА-Яа-я*")) {
                patronymicField.setText(newValue.replaceAll("[^\\sa-zA-ZА-Яа-я]", ""));
            }
        });
        experienceField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                experienceField.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });
    }
}
