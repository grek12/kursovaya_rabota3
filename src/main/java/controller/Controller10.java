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
import model.DAOLawyer;
import model.Lawyer;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Controller10 implements Initializable {//класс контроллер формы с адвокатами
    @FXML
    private TableView<Lawyer> tableLawyer;

    @FXML
    private TableColumn<Lawyer, Integer> idClient;

    @FXML
    private TableColumn<Lawyer, String> lastName;

    @FXML
    private TableColumn<Lawyer, String> firstName;

    @FXML
    private TableColumn<Lawyer, String> patronymic;

    @FXML
    private TableColumn<Lawyer, Integer> experience;

    @FXML
    private TableColumn<Lawyer, String> numberPhone;
    @FXML
    private TextField nameField;
    public DAOLawyer dao;


    @FXML
    public void initialize(URL url, ResourceBundle resourceBundle) {//Блок инициализации
        dao = new DAOLawyer();
        tableLawyer.setItems(dao.selectLawyer());
        idClient.setCellValueFactory(new PropertyValueFactory<>("id"));
        firstName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        lastName.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        patronymic.setCellValueFactory(new PropertyValueFactory<>("patronymic"));
        experience.setCellValueFactory(new PropertyValueFactory<>("experience"));
        numberPhone.setCellValueFactory(new PropertyValueFactory<>("numberPhone"));
    }

    @FXML
    public void updateTable() {
        tableLawyer.setItems(dao.selectLawyer());
    }//обновление таблицы

    @FXML
    private void addLawyer() {//открытие форы добавления адвоката
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/addLawyer.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 420, 253);
            Stage stage = new Stage();
            stage.setTitle("Добавление нового адвоката");
            stage.setScene(scene);
            stage.setResizable(false);
            stage.getIcons().add(new Image(Controller10.class.getResourceAsStream("/icon.png")));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.show();
            Controller11 controller11 = fxmlLoader.getController();
            controller11.setMainController(this);
        } catch (IOException e) {
            Logger logger = Logger.getLogger(getClass().getName());
            logger.log(Level.SEVERE, "Не удалось создать новое окно.", e);

        }


    }

    public void updateLawyer(ActionEvent event) {//открытие формы изменение данных адвоката
        Lawyer lawyer = tableLawyer.getSelectionModel().getSelectedItem();
        if (lawyer != null) {
            updateLawyer(lawyer);
        } else {
            DialogError("Адвокат не выбран");
        }
    }

    @FXML
    public void updateLawyer(Lawyer lawyer) {//открытие формы изменение данных адвоката
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/updateLawyer.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 420, 303);
            Stage stage = new Stage();
            stage.setTitle("Изменение данных адвоката");
            stage.setScene(scene);
            stage.setResizable(false);
            stage.getIcons().add(new Image(Controller10.class.getResourceAsStream("/icon.png")));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.show();
            Controller12 controller = fxmlLoader.getController();
            controller.setPerson(lawyer);
            controller.setMainController(this);
        } catch (IOException e) {
            Logger logger = Logger.getLogger(getClass().getName());
            logger.log(Level.SEVERE, "Не удалось создать новое окно.", e);

        }

    }

    @FXML
    private void deleteLawyer() {//удаление адвоката
        Lawyer lawyer = tableLawyer.getSelectionModel().getSelectedItem();
        if (lawyer == null) {
            DialogError("Удаление невозможно, список адвокатов пуст!");
        } else {
            if (DialogConf("Подтверждаете удаление адвоката?")) {

                try {

                    dao.deleteLawyer(Integer.valueOf(lawyer.getId()));
                    DialogInfo("Адвокат успешно удален!");
                    updateTable();

                } catch (Exception e) {
                    DialogError("Ошибка при удалении");
                    e.printStackTrace();
                }
            }
        }
    }

    public void DialogError(String error) {//вывод сообщения
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

    public void DialogInfo(String info) {//вывод сообщения
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

    public boolean DialogConf(String conf) {//вывод сообщения
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
    private void searchNameClient() {//поиск по фамилии

        try {

            List<Lawyer> result = dao.searchLawyer2(nameField.getText());

            tableLawyer.setItems(FXCollections.observableList(result));


        } catch (Exception e) {
            DialogError("Ошибка!");
            e.printStackTrace();
        }
    }

}
