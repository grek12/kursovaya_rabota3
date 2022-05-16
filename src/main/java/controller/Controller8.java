package controller;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.StringConverter;
import model.*;

import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ResourceBundle;

public class Controller8 implements Initializable {//класс контроллер формы с заключенными договорами

    @FXML
    private TableView<Client> tableClient;

    @FXML
    private TableColumn<Client, Integer> idClient2;

    @FXML
    private TableColumn<Client, String> lastName;

    @FXML
    private TableColumn<Client, String> firstName;

    @FXML
    private TableColumn<Client, String> patronymic;

    @FXML
    private TableColumn<Client, String> numberPhone;

    @FXML
    private TableView<ContractJOIN> tableContract;

    @FXML
    private TableColumn<ContractJOIN, Integer> idContract;

    @FXML
    private TableColumn<ContractJOIN, String> idClient;

    @FXML
    private TableColumn<ContractJOIN, String> idService;

    @FXML
    private TableColumn<ContractJOIN, String> idEmployee;

    @FXML
    private TableColumn<ContractJOIN, Date> date;

    @FXML
    private TableColumn<ContractJOIN, Float> sum;

    @FXML
    private TableView<Lawyer> tableLawyer;
    @FXML
    private TableColumn<Lawyer, Integer> idLawyer;
    @FXML
    private TableColumn<Lawyer, String> firstName1;
    @FXML
    private TableColumn<Lawyer, String> lastName1;
    @FXML
    private TableColumn<Lawyer, String> patronymic1;
    @FXML
    private TableColumn<Lawyer, String> numberPhone1;
    @FXML
    private DatePicker datepick2;
    @FXML
    private TextField clientNameField;

    public DAOContract dao;
    public DAOClient dao2;
    public DAOLawyer dao3;

    @FXML
    public void initialize(URL url, ResourceBundle resourceBundle) {//Блок инициализации
        dao = new DAOContract();
        tableContract.setItems(dao.test());
        idContract.setCellValueFactory(new PropertyValueFactory<>("idContract"));
        idClient.setCellValueFactory(new PropertyValueFactory<>("clientName"));
        idService.setCellValueFactory(new PropertyValueFactory<>("nameService"));
        idEmployee.setCellValueFactory(new PropertyValueFactory<>("lawyerName"));
        date.setCellValueFactory(new PropertyValueFactory<>("date"));
        sum.setCellValueFactory(new PropertyValueFactory<>("amount"));


        lastName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        firstName.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        patronymic.setCellValueFactory(new PropertyValueFactory<>("patronymic"));
        numberPhone.setCellValueFactory(new PropertyValueFactory<>("numberPhone"));
        idClient2.setCellValueFactory(new PropertyValueFactory<>("id"));


        lastName1.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        firstName1.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        patronymic1.setCellValueFactory(new PropertyValueFactory<>("patronymic"));
        numberPhone1.setCellValueFactory(new PropertyValueFactory<>("numberPhone"));
        idLawyer.setCellValueFactory(new PropertyValueFactory<>("id"));


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
        datepick2.setConverter(converter);
        datepick2.setPromptText("yyyy-MM-dd");


    }

    @FXML
    public void clickItem() {//обработчик события клика на таблицу
        tableContract.setOnMouseClicked(event -> {
            showCLient();
        });
    }

    @FXML
    public void showCLient() {//вывод данных в таблицы
        dao2 = new DAOClient();
        ContractJOIN contract = tableContract.getSelectionModel().getSelectedItem();
        int idClient = contract.getIdClient();
        tableClient.setItems(dao2.searchCleint(idClient));

        dao3 = new DAOLawyer();
        int idLawyer = contract.getIdEmployee();
        tableLawyer.setItems(dao3.searchLawyer(idLawyer));
    }

    @FXML
    private void searchDate() {//поиск договора по дате
        clientNameField.clear();
        try {
            if (!(datepick2.getValue() == null)) {
                List<ContractJOIN> result = dao.searchDate(Date.valueOf(datepick2.getValue()));
                tableContract.setItems(FXCollections.observableList(result));
            }else {
                tableContract.setItems(dao.test());
            }

        } catch (Exception e) {

            e.printStackTrace();
        }
    }
    @FXML
    private void searchClient() {//поиск договора по клиенту
        datepick2.getEditor().clear();
        try {

            List<ContractJOIN> result = dao.searchClient(clientNameField.getText());

            tableContract.setItems(FXCollections.observableList(result));

        } catch (Exception e) {
            tableContract.setItems(dao.test());
            e.printStackTrace();
        }
    }
}