package controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import model.*;
import org.controlsfx.control.textfield.TextFields;

import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.stream.Collectors;

public class Controller9 {//класс контроллер формы добавления договора


    @FXML
    private TextField idServiceField;


    @FXML
    private TextField amountField;

    @FXML
    private DatePicker datepick;
    @FXML
    private Controller2 mainController;
    private Service service;
    @FXML
    private ComboBox<Client> combobox;

    @FXML
    private ComboBox<Lawyer> combobox2;
    private Client client1;
    private DAOClient dao;
    private DAOLawyer dao2;

    @FXML
    private void initialize() {//Блок инициализации
        DAOClient dao = new DAOClient();
        DAOLawyer dao2 = new DAOLawyer();
        combobox.setItems(dao.selectClient());
        combobox2.setItems(dao2.selectLawyer());
        combobox.setEditable(true);
        combobox2.setEditable(true);

        combobox.setConverter(new StringConverter<Client>() {
            @Override
            public String toString(Client client) {
                return client == null ? "" : client.toString();
            }

            @Override
            public Client fromString(String string) {
                // return combobox.getSelectionModel().getSelectedItem();
                return combobox.getItems().stream().filter(state ->
                        state.toString().equals(string)).findFirst().orElse(null);
            }
        });

        TextFields.bindAutoCompletion(combobox2.getEditor(),
                combobox2.getItems().stream().map(state2 ->
                        state2.toString()).collect(Collectors.toList()));
        combobox2.setConverter(new StringConverter<Lawyer>() {
            @Override
            public String toString(Lawyer lawyer) {
                return lawyer == null ? "" : lawyer.toString();
            }

            @Override
            public Lawyer fromString(String string) {
                // return combobox.getSelectionModel().getSelectedItem();
                return combobox2.getItems().stream().filter(state2 ->
                        state2.toString().equals(string)).findFirst().orElse(null);
            }
        });

        TextFields.bindAutoCompletion(combobox.getEditor(),
                combobox.getItems().stream().map(state ->
                        state.toString()).collect(Collectors.toList()));
        combobox.setEditable(true);

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
    public void setMainController(Controller2 mainController) {//получение данных от 2-го контроллера
        this.mainController = mainController;
    }

    @FXML
    private void handleCancel(javafx.event.ActionEvent event) {//нажатие на кнопку отмена
        ((Stage) (((Button) event.getSource()).getScene().getWindow())).close();
    }


    public void setService(Service service) {//получение данных услуги
        this.service = service;
        idServiceField.setText(String.valueOf(service.getId()));
        amountField.setText(String.valueOf(service.getPrice()));
    }

    private boolean isInputValid() {//проверка условий
        String errorMessage = "";

        if (combobox.getSelectionModel().getSelectedItem() == null) {
            errorMessage += "Выберите клиента!\n";
        }

        if (combobox.getSelectionModel().getSelectedItem() == null) {
            errorMessage += "Выберите адвоката!\n";
        }
        if (datepick.getValue() == null) {
            errorMessage += "Выберите дату заключение договора!\n";
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

        Contract contract = new Contract();
        if (isInputValid()) {
            contract.setIdClient(combobox.getSelectionModel().getSelectedItem().getId());
            contract.setIdEmployee(combobox2.getSelectionModel().getSelectedItem().getId());
            contract.setIdService(Integer.parseInt(idServiceField.getText()));
            contract.setDate(Date.valueOf(datepick.getValue()));
            contract.setAmount(Float.parseFloat(amountField.getText()));

            try {
                DAOContract dao = new DAOContract();
                dao.insertContract(contract);
                mainController.DialogInfo("Договор успешно добавлен!");


            } catch (Exception e) {
                mainController.DialogError("Не удалось добавить договор!");

                e.printStackTrace();
            }

        }

        ((Stage) (((Button) event.getSource()).getScene().getWindow())).close();
    }


}


