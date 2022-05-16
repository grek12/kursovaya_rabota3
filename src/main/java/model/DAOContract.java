package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DAOContract {//Класс запросов с таблицей "Договор"
    private Connection connection;

    public DAOContract() {
        connection = ConnectionUtil.conDB();
    }


    public void insertContract(Contract contract) { //запрос на добавление договора
        String sql = "insert into contract (idclient,idservice,idemployee,date,amount) values (?,?,?,?,?)";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setInt(1, contract.getIdClient());
            statement.setInt(2, contract.getIdService());
            statement.setInt(3, contract.getIdEmployee());
            statement.setDate(4, contract.getDate());
            statement.setFloat(5, contract.getAmount());
            statement.execute();
            statement.close();

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

    }


    public ObservableList<ContractJOIN> test() {//запрос на получение данных из нескольких таблиц
        ObservableList<ContractJOIN> contractJOINS = FXCollections.observableArrayList();
        String sql = "SELECT contract.idcontract,client.idclient, client.firstname as clientname,employee.idemployee, employee.firstname as emp, service.idservice, service.typeservice,  contract.date, contract.amount\n" +
                "FROM contract JOIN client ON contract.idclient = client.idclient\n" +
                "JOIN employee ON  contract.idemployee = employee.idemployee\n" +
                "JOIN service ON contract.idservice = service.idservice";

        try {
            PreparedStatement statement = connection.prepareStatement(sql);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {

                ContractJOIN contractJOIN = new ContractJOIN();

                contractJOIN.setIdContract(resultSet.getInt("idcontract"));
                contractJOIN.setIdClient(resultSet.getInt("idclient"));
                contractJOIN.setClientName(resultSet.getString("clientname"));
                contractJOIN.setIdEmployee(resultSet.getInt("idemployee"));
                contractJOIN.setLawyerName(resultSet.getString("emp"));
                contractJOIN.setIdService(resultSet.getInt("idservice"));
                contractJOIN.setNameService(resultSet.getString("typeservice"));
                contractJOIN.setDate(resultSet.getDate("date"));
                contractJOIN.setAmount(resultSet.getFloat("amount"));


                contractJOINS.add(contractJOIN);
            }

            resultSet.close();
            statement.close();

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        return contractJOINS;
    }

    public List<ContractJOIN> searchDate(Date date) {//запрос поиск по дате

        List<ContractJOIN> contractJOINS = new ArrayList<>();
        String sql = String.format("SELECT contract.idcontract,client.idclient, client.firstname as clientname,employee.idemployee, employee.firstname as emp, service.idservice, service.typeservice,  contract.date, contract.amount\n" +
                "FROM contract JOIN client ON contract.idclient = client.idclient\n" +
                "JOIN employee ON  contract.idemployee = employee.idemployee\n" +
                "JOIN service ON contract.idservice = service.idservice " +
                "WHERE date = '%s' ", date);

        try {
            PreparedStatement statement = connection.prepareStatement(sql);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {

                ContractJOIN contractJOIN = new ContractJOIN();

                contractJOIN.setIdContract(resultSet.getInt("idcontract"));
                contractJOIN.setIdClient(resultSet.getInt("idclient"));
                contractJOIN.setClientName(resultSet.getString("clientname"));
                contractJOIN.setIdEmployee(resultSet.getInt("idemployee"));
                contractJOIN.setLawyerName(resultSet.getString("emp"));
                contractJOIN.setIdService(resultSet.getInt("idservice"));
                contractJOIN.setNameService(resultSet.getString("typeservice"));
                contractJOIN.setDate(resultSet.getDate("date"));
                contractJOIN.setAmount(resultSet.getFloat("amount"));


                contractJOINS.add(contractJOIN);
            }

            resultSet.close();
            statement.close();

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        return contractJOINS;
    }

    public List<ContractJOIN> searchClient(String name) {//поиск по фамилии клиента

        List<ContractJOIN> contractJOINS = new ArrayList<>();
        String sql = "SELECT contract.idcontract,client.idclient, client.firstname as clientname,employee.idemployee, employee.firstname as emp, service.idservice, service.typeservice,  contract.date, contract.amount\n" +
                "                FROM contract JOIN client ON contract.idclient = client.idclient\n" +
                "                JOIN employee ON  contract.idemployee = employee.idemployee\n" +
                "                JOIN service ON contract.idservice = service.idservice\n" +
                "                WHERE client.firstname like '%" + name + "%' ";

        try {
            PreparedStatement statement = connection.prepareStatement(sql);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {

                ContractJOIN contractJOIN = new ContractJOIN();

                contractJOIN.setIdContract(resultSet.getInt("idcontract"));
                contractJOIN.setIdClient(resultSet.getInt("idclient"));
                contractJOIN.setClientName(resultSet.getString("clientname"));
                contractJOIN.setIdEmployee(resultSet.getInt("idemployee"));
                contractJOIN.setLawyerName(resultSet.getString("emp"));
                contractJOIN.setIdService(resultSet.getInt("idservice"));
                contractJOIN.setNameService(resultSet.getString("typeservice"));
                contractJOIN.setDate(resultSet.getDate("date"));
                contractJOIN.setAmount(resultSet.getFloat("amount"));


                contractJOINS.add(contractJOIN);
            }

            resultSet.close();
            statement.close();

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        return contractJOINS;
    }

}
