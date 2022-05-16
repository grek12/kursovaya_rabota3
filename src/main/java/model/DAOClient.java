package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class DAOClient { //Класс запросов с таблицей "клиент"
    private Connection connection;

    public DAOClient() {
        connection = ConnectionUtil.conDB();
    }

    public ObservableList<Client> selectClient() {//запрос на получения все клиентов

        ObservableList<Client> clients = FXCollections.observableArrayList();

        String sql = "select * from client ";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {

                Client client = new Client();

                client.setId(resultSet.getInt("idclient"));
                client.setLastName(resultSet.getString("firstname"));
                client.setFirstName(resultSet.getString("lastname"));
                client.setPatronymic(resultSet.getString("patronymic"));
                client.setBirthDate(resultSet.getDate("birthdate"));
                client.setPasport(resultSet.getString("passport"));
                client.setNumberPhone(resultSet.getString("phonenumber"));


                clients.add(client);
            }

            resultSet.close();
            statement.close();

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        return clients;
    }


    public void insertClient(Client client) {//запрос на добавление клиента
        String sql = "insert into client (firstname,lastname,patronymic,birthdate,passport,phonenumber) values (?,?,?,?,?,?)";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setString(1, client.getFirstName());
            statement.setString(2, client.getLastName());
            statement.setString(3, client.getPatronymic());
            statement.setDate(4, client.getBirthDate());
            statement.setString(5, client.getPasport());
            statement.setString(6, client.getNumberPhone());
            statement.execute();
            statement.close();

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

    }

    public void updateClient(Client client) {//запрос на изменение данных клиента

        String sql = "update client set firstname=?, lastname=?, patronymic=?, birthdate=?, passport=?, phonenumber=? where idclient=?";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setString(1, client.getFirstName());
            statement.setString(2, client.getLastName());
            statement.setString(3, client.getPatronymic());
            statement.setDate(4, client.getBirthDate());
            statement.setString(5, client.getPasport());
            statement.setString(6, client.getNumberPhone());
            statement.setInt(7, client.getId());
            statement.execute();
            statement.close();

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

    }

    public void deleteCleint(Integer id) {////запрос на удаление клиента

        String sql = "delete from client where idclient = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setInt(1, id);

            statement.execute();
            statement.close();

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }


    public ObservableList<Client> searchCleint(int id) {//запрос на поиск клиента по ID

        ObservableList<Client> clients = FXCollections.observableArrayList();

        String sql = String.format("select * from client where idclient = '%s' ", id);
        try {
            PreparedStatement statement = connection.prepareStatement(sql);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {

                Client client = new Client();
                client.setId(resultSet.getInt("idclient"));
                client.setFirstName(resultSet.getString("firstname"));
                client.setLastName(resultSet.getString("lastname"));
                client.setPatronymic(resultSet.getString("patronymic"));
                client.setNumberPhone(resultSet.getString("phonenumber"));
                clients.add(client);
            }

            resultSet.close();
            statement.close();

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        return clients;
    }

    public List<Client> searchClient2(String name) {//запрос на поиск клиента по фамилии

        List<Client> clients = new ArrayList<>();

        String sql = "select * from client where firstname like '%" + name + "%'";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {

                Client client = new Client();

                client.setId(resultSet.getInt("idclient"));
                client.setLastName(resultSet.getString("firstname"));
                client.setFirstName(resultSet.getString("lastname"));
                client.setPatronymic(resultSet.getString("patronymic"));
                client.setBirthDate(resultSet.getDate("birthdate"));
                client.setPasport(resultSet.getString("passport"));
                client.setNumberPhone(resultSet.getString("phonenumber"));
                clients.add(client);
            }

            resultSet.close();
            statement.close();

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        return clients;
    }


    public List<Client> searchClient3(String pas) {//запрос на поиск клиента по данным паспорта

        List<Client> clients = new ArrayList<>();

        String sql = String.format("select * from client where passport = '%s' ", pas);
        try {
            PreparedStatement statement = connection.prepareStatement(sql);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {

                Client client = new Client();

                client.setId(resultSet.getInt("idclient"));
                client.setLastName(resultSet.getString("firstname"));
                client.setFirstName(resultSet.getString("lastname"));
                client.setPatronymic(resultSet.getString("patronymic"));
                client.setBirthDate(resultSet.getDate("birthdate"));
                client.setPasport(resultSet.getString("passport"));
                client.setNumberPhone(resultSet.getString("phonenumber"));
                clients.add(client);
            }

            resultSet.close();
            statement.close();

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        return clients;
    }

}
