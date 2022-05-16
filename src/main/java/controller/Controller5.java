package controller;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Client;
import model.DAOClient;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Controller5 implements Initializable {//класс контроллер формы с клиентами

    @FXML
    private TableView<Client> tableClient;

    @FXML
    private TableColumn<Client, Integer> idClient;

    @FXML
    private TableColumn<Client, String> lastName;

    @FXML
    private TableColumn<Client, String> firstName;

    @FXML
    private TableColumn<Client, String> patronymic;

    @FXML
    private TableColumn<Client, Date> birthDate;

    @FXML
    private TableColumn<Client, String> pasport;

    @FXML
    private TableColumn<Client, String> numberPhone;

    @FXML
    private TextField nameField;

    @FXML
    private TextField pasField;

    public DAOClient dao;

    @FXML
    public void initialize(URL url, ResourceBundle resourceBundle) {//Блок инициализации
        dao = new DAOClient();
        tableClient.setItems(dao.selectClient());
        idClient.setCellValueFactory(new PropertyValueFactory<>("id"));
        firstName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        lastName.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        patronymic.setCellValueFactory(new PropertyValueFactory<>("patronymic"));
        birthDate.setCellValueFactory(new PropertyValueFactory<>("birthDate"));
        pasport.setCellValueFactory(new PropertyValueFactory<>("pasport"));
        numberPhone.setCellValueFactory(new PropertyValueFactory<>("numberPhone"));
    }

    @FXML
    private void addClient() {//открытие формы добавления клиента
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/addClient.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 420, 252);
            Stage stage = new Stage();
            stage.setTitle("Добавление нового клиента");
            stage.setScene(scene);
            stage.setResizable(false);
            stage.getIcons().add(new Image(Controller5.class.getResourceAsStream("/icon.png")));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.show();
            Controller6 controller6 = fxmlLoader.getController();
            controller6.setMainController(this);
        } catch (IOException e) {
            Logger logger = Logger.getLogger(getClass().getName());
            logger.log(Level.SEVERE, "Не удалось создать новое окно.", e);

        }
    }

    @FXML
    private void deleteClient() {//удаление клиента
        Client client = tableClient.getSelectionModel().getSelectedItem();
        if (client == null) {
            DialogError("Удаление невозможно, список клиентов пуст!");
        } else {
            if (DialogConf("Подтверждаете удаление клиента?")) {

                try {

                    dao.deleteCleint(client.getId());
                    DialogInfo("Клиент успешно удален!");
                    updateTable();

                } catch (Exception e) {
                    DialogError("Ошибка при удалении");
                    e.printStackTrace();
                }
            }
        }
    }


    @FXML
    public void updateTable() {
        tableClient.setItems(dao.selectClient());
    }//обновление таблицы

    @FXML
    private void updateClient(Client client) {//открытие формы изменение данных клиента
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/updateClient.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 420, 304);
            Stage stage = new Stage();
            stage.setTitle("Изменение данных клиента");
            stage.setScene(scene);
            stage.setResizable(false);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.show();
            stage.getIcons().add(new Image(Controller5.class.getResourceAsStream("/icon.png")));
            Controller7 controller = fxmlLoader.getController();
            controller.setMainController(this);
            controller.setPerson(client);
        } catch (IOException e) {
            Logger logger = Logger.getLogger(getClass().getName());
            logger.log(Level.SEVERE, "Не удалось создать новое окно.", e);

        }

    }

    public void updateClient(ActionEvent event) {//открытие формы изменение данных клиента
        Client client = tableClient.getSelectionModel().getSelectedItem();
        if (client != null) {
            updateClient(client);
        } else {
            DialogError("Клиент не выбран");
        }
    }


    public void DialogError(String error) {//вывод диалогового окна
        Alert alert = new Alert(Alert.AlertType.ERROR);
        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.getStylesheets().add(
                getClass().getResource("/fullpackstyling.css").toExternalForm());
        dialogPane.getStyleClass().add("myDialog");
        alert.setTitle("Ошибка");
        alert.setHeaderText(null);
        alert.setContentText(error);

        alert.showAndWait();
    }

    public void DialogInfo(String info) {//вывод диалогового окна
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Информация");
        alert.setHeaderText(null);
        alert.setContentText(info);
        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.getStylesheets().add(
                getClass().getResource("/fullpackstyling.css").toExternalForm());
        dialogPane.getStyleClass().add("myDialog");
        alert.showAndWait();
    }

    private boolean DialogConf(String conf) {//вывод диалогового окна
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Подтверждение");
        alert.setHeaderText(null);
        alert.setContentText(conf);
        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.getStylesheets().add(
                getClass().getResource("/fullpackstyling.css").toExternalForm());
        dialogPane.getStyleClass().add("myDialog");
        Optional<ButtonType> opcao = alert.showAndWait();

        if (opcao.get() == ButtonType.OK) {
            return true;
        }
        return false;
    }

    @FXML
    private void searchNameClient() {//поиск клиента по фамилии
        pasField.clear();
        try {

            List<Client> result = dao.searchClient2(nameField.getText());

            tableClient.setItems(FXCollections.observableList(result));


        } catch (Exception e) {
            DialogError("Ошибка!");
            e.printStackTrace();
        }
    }


    @FXML
    private void searchNameClientP() {//поиск клиента по данным паспорта
        nameField.clear();
        try {

            List<Client> result = dao.searchClient3(pasField.getText());

            tableClient.setItems(FXCollections.observableList(result));

        } catch (Exception e) {

            tableClient.setItems(dao.selectClient());
            e.printStackTrace();
        }
    }

}
