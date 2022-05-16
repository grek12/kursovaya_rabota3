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
import model.DAOService;
import model.Service;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Controller2 implements Initializable {//класс контроллер главного экрана

    @FXML
    private TableView<Service> tableService;

    @FXML
    private TableColumn<Service, Integer> idService;

    @FXML
    private TableColumn<Service, String> nameService;

    @FXML
    private TableColumn<Service, Float> priceService;

    @FXML
    private TextField txtNameService;

    @FXML
    private TextField txtPriceService;

    @FXML
    private TextArea description;

    public DAOService dao;


    @FXML
    public void initialize(URL url, ResourceBundle resourceBundle) {//Блок инициализации
        dao = new DAOService();
        tableService.setItems(dao.selectService());
        idService.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameService.setCellValueFactory(new PropertyValueFactory<>("typeservice"));
        priceService.setCellValueFactory(new PropertyValueFactory<>("price"));
    }

    @FXML
    private void searchNameservice() {//поиск услуги по названию
        txtPriceService.clear();
        try {

            List<Service> result = dao.searchService1(txtNameService.getText());

            tableService.setItems(FXCollections.observableList(result));

        } catch (Exception e) {
            DialogError("Ошибка!");
            e.printStackTrace();
        }
    }

    @FXML
    private void searchPriceservice() {//поиск услуги по цене
        txtNameService.clear();
        try {

            List<Service> result = dao.searchService2(Float.parseFloat(txtPriceService.getText()));

            tableService.setItems(FXCollections.observableList(result));

        } catch (Exception e) {
            tableService.setItems(dao.selectService());
            e.printStackTrace();
        }
    }

    @FXML
    private void addService() {//открытие форсы добавление новой услуги
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/addService.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 410, 150);
            Stage stage = new Stage();
            stage.setTitle("Добавление новой услуги");
            stage.setScene(scene);
            stage.getIcons().add(new Image(Controller2.class.getResourceAsStream("/icon.png")));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setResizable(false);
            stage.show();
            Controller3 controller3 = fxmlLoader.getController();
            controller3.setMainController(this);
        } catch (IOException e) {
            Logger logger = Logger.getLogger(getClass().getName());
            logger.log(Level.SEVERE, "Не удалось создать новое окно.", e);

        }
    }

    public void updateService(ActionEvent event) {//открытие формы изменение данных услуги
        Service service = tableService.getSelectionModel().getSelectedItem();
        if (service != null) {
            updateService(service);
        } else {
            DialogError("Услуга не выбрана");
        }
    }

    @FXML
    private void updateService(Service service) {//открытие формы изменение данных услуги
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/updateService.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 410, 180);
            Stage stage = new Stage();
            stage.setTitle("Изменение данных услуги");
            stage.setScene(scene);
            stage.setResizable(false);
            stage.getIcons().add(new Image(Controller2.class.getResourceAsStream("/icon.png")));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.show();
            Controller4 controller = fxmlLoader.getController();
            controller.setPerson(service);
            controller.setMainController(this);
        } catch (IOException e) {
            Logger logger = Logger.getLogger(getClass().getName());
            logger.log(Level.SEVERE, "Не удалось создать новое окно.", e);

        }

    }

    @FXML
    private void deleteService() {//подтверждение удаления
        Service service = tableService.getSelectionModel().getSelectedItem();
        if (service == null) {
            DialogError("Удаление невозможно, список услуг пуст!");
        } else {
            if (DialogConf("Подтверждаете удаление услуги?")) {

                try {

                    dao.deleteService(Integer.valueOf(service.getId()));
                    DialogInfo("Услуга успешно удалена!");
                    updateTable();

                } catch (Exception e) {
                    DialogError("Ошибка при удалении");
                    e.printStackTrace();
                }
            }
        }
    }

    @FXML
    public void clickItem() {//отлов клика на таблицу
        tableService.setOnMouseClicked(event -> {
            description();
        });
    }

    @FXML
    public void description() {//вывод данных "описание услуги"
        Service service = tableService.getSelectionModel().getSelectedItem();

        description.setText(service.getDescription());
    }

    @FXML
    public void updateTable() {
        tableService.setItems(dao.selectService());
    }//обновление таблицы с услугами

    @FXML
    private void openClientForm() {//открытие формы клиентов
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/ClientForm.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 645, 515);
            Stage stage = new Stage();
            stage.setTitle("Данные клиентов");
            stage.setScene(scene);
            stage.setResizable(false);
            stage.getIcons().add(new Image(Controller2.class.getResourceAsStream("/icon.png")));
            stage.show();

        } catch (IOException e) {
            Logger logger = Logger.getLogger(getClass().getName());
            logger.log(Level.SEVERE, "Не удалось создать новое окно.", e);

        }
    }

    @FXML
    private void openContractForm() {//открытие формы оформленных  договоров
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/ContractForm.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 884, 484);
            Stage stage = new Stage();
            stage.setTitle("Данные договоров");
            stage.setScene(scene);
            stage.setResizable(false);
            stage.getIcons().add(new Image(Controller2.class.getResourceAsStream("/icon.png")));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.show();

        } catch (IOException e) {
            Logger logger = Logger.getLogger(getClass().getName());
            logger.log(Level.SEVERE, "Не удалось создать новое окно.", e);

        }
    }

    public void openAddContractForm(ActionEvent event) {//открытие формы добавления договора
        Service service = tableService.getSelectionModel().getSelectedItem();
        if (service != null) {
            openAddContractForm(service);
        } else {
            DialogError("Услуга не выбрана");
        }
    }

    @FXML
    private void openAddContractForm(Service service) {//открытие формы добавления договора
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/addContract.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 420, 253);
            Stage stage = new Stage();
            stage.setTitle("Добавление договора");
            stage.setScene(scene);
            stage.setResizable(false);
            stage.getIcons().add(new Image(Controller2.class.getResourceAsStream("/icon.png")));
            Controller9 controller = fxmlLoader.getController();
            controller.setService(service);
            controller.setMainController(this);
            stage.show();

        } catch (IOException e) {
            Logger logger = Logger.getLogger(getClass().getName());
            logger.log(Level.SEVERE, "Не удалось создать новое окно.", e);

        }
    }

    @FXML
    private void openLawyeerForm() {//открытие формы с мастерами
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/lawyeerForm.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 620, 524);
            Stage stage = new Stage();
            stage.setTitle("Данные мастеров");
            stage.setScene(scene);
            stage.setResizable(false);
            stage.getIcons().add(new Image(Controller2.class.getResourceAsStream("/icon.png")));
            stage.show();

        } catch (IOException e) {
            Logger logger = Logger.getLogger(getClass().getName());
            logger.log(Level.SEVERE, "Не удалось создать новое окно.", e);

        }
    }


    public void DialogError(String error) {//диалоговое сообщение
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

    public void DialogInfo(String info) {//диалоговое сообщение
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

    public boolean DialogConf(String conf) {//диалоговое сообщение
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


}
