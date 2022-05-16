package controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import model.Client;
import model.DAOClient;

import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Controller6 {//класс контроллер формы добавления клиента

    @FXML
    private TextField firstNameField;

    @FXML
    private TextField lastNameField;

    @FXML
    private TextField patronymicField;

    @FXML
    private TextField pasportField;

    @FXML
    private TextField numberPhoneField;

    @FXML
    private DatePicker datepick;

    @FXML
    private Controller5 mainController;


    @FXML
    private void initialize() {//Блок инициализации
        StringConverter<LocalDate> converter = new StringConverter<LocalDate>() {
            DateTimeFormatter dateFormatter =
                    DateTimeFormatter.ofPattern("yyyy-MM-dd");

            @Override
            public String toString(LocalDate date) {
                if (date != null) {
                    return dateFormatter.format(date);
                } else {
                    return "";
                }
            }

            @Override
            public LocalDate fromString(String string) {
                if (string != null && !string.isEmpty()) {
                    return LocalDate.parse(string, dateFormatter);
                } else {
                    return null;
                }
            }

        };
        datepick.setConverter(converter);
        datepick.setPromptText("yyyy-MM-dd");
    }

    @FXML
    public void setMainController(Controller5 mainController) {
        this.mainController = mainController;
    }//получение данных из 2-го контроллера

    @FXML
    private void handleCancel(javafx.event.ActionEvent event) {//нажатие кнопки отмена
        ((Stage) (((Button) event.getSource()).getScene().getWindow())).close();
    }

    private boolean isInputValid() {//проверка условий
        String errorMessage = "";

        if (firstNameField.getText() == null || firstNameField.getText().length() == 0) {
            errorMessage += "Заполните поле фамилии клиента!\n";
        }
        if (lastNameField.getText() == null || lastNameField.getText().length() == 0) {
            errorMessage += "Заполните поле имени клиента!\n";
        }
        if (patronymicField.getText() == null || patronymicField.getText().length() == 0) {
            errorMessage += "Заполните поле отчества клиента!\n";
        }
        if (datepick.getValue() == null) {
            errorMessage += "Заполните поле даты рождения клиента!\n";
        }
        if (pasportField.getText() == null || pasportField.getText().length() == 0) {
            errorMessage += "Заполните поле паспортных данных клиента!\n";
        }
        if (numberPhoneField.getText() == null || numberPhoneField.getText().length() == 0) {
            errorMessage += "Заполните поле номера телефона клиента!\n";
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
    public void handleOk(javafx.event.ActionEvent event) {//нажатие кнопки Ок

        Client client = new Client();
        if (isInputValid()) {
            client.setFirstName(firstNameField.getText());
            client.setLastName(lastNameField.getText());
            client.setPatronymic(patronymicField.getText());
            client.setBirthDate(Date.valueOf(datepick.getValue()));
            client.setPasport(pasportField.getText());
            client.setNumberPhone(numberPhoneField.getText());

            try {
                DAOClient dao = new DAOClient();
                dao.insertClient(client);
                mainController.updateTable();
                mainController.DialogInfo("Клиент успешно добавлен!");


            } catch (Exception e) {
                mainController.DialogError("Не удалось добавить клиента!");

                e.printStackTrace();
            }

        }

        ((Stage) (((Button) event.getSource()).getScene().getWindow())).close();
    }

    public void test() {//ограничение ввода
        firstNameField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\sa-zA-ZА-Яа-я*")) {
                firstNameField.setText(newValue.replaceAll("[^\\sa-zA-ZА-Яа-я]", ""));
            }
        });
        lastNameField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\sa-zA-ZА-Яа-я*")) {
                lastNameField.setText(newValue.replaceAll("[^\\sa-zA-ZА-Яа-я]", ""));
            }
        });
        patronymicField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\sa-zA-ZА-Яа-я*")) {
                patronymicField.setText(newValue.replaceAll("[^\\sa-zA-ZА-Яа-я]", ""));
            }
        });
    }


}
