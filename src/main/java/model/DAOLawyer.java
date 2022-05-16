package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DAOLawyer {//Класс запросов с таблицей "сотрудник"
    private Connection connection;

    public DAOLawyer() {
        connection = ConnectionUtil.conDB();
    }


    public ObservableList<Lawyer> selectLawyer() {//запрос на получение всех данных из таблицы адвокатов

        ObservableList<Lawyer> lawyers = FXCollections.observableArrayList();

        String sql = "select * from  employee";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {

                Lawyer lawyer = new Lawyer();

                lawyer.setId(resultSet.getInt("idemployee"));
                lawyer.setLastName(resultSet.getString("firstname"));
                lawyer.setFirstName(resultSet.getString("lastname"));
                lawyer.setPatronymic(resultSet.getString("patronymic"));
                lawyer.setExperience(resultSet.getInt("experience"));
                lawyer.setNumberPhone(resultSet.getString("phonenumber"));


                lawyers.add(lawyer);
            }

            resultSet.close();
            statement.close();

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        return lawyers;
    }

    public void insertLawyer(Lawyer lawyer) {//запрос на добавление адвоката
        String sql = "insert into employee (firstname,lastname,patronymic,experience,phonenumber) values (?,?,?,?,?)";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setString(1, lawyer.getFirstName());
            statement.setString(2, lawyer.getLastName());
            statement.setString(3, lawyer.getPatronymic());
            statement.setInt(4, lawyer.getExperience());
            statement.setString(5, lawyer.getNumberPhone());
            statement.execute();
            statement.close();

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

    }

    public void updateLawyer(Lawyer lawyer) {//запрос на изменение  данных из адвоката

        String sql = "update employee set firstname =?, lastname=?, patronymic=?, experience=?, phonenumber=? where idemployee=?";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setString(1, lawyer.getFirstName());
            statement.setString(2, lawyer.getLastName());
            statement.setString(3, lawyer.getPatronymic());
            statement.setInt(4, lawyer.getExperience());
            statement.setString(5, lawyer.getNumberPhone());
            statement.setInt(6, lawyer.getId());
            statement.execute();
            statement.close();

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

    }

    public void deleteLawyer(Integer id) {////запрос на удаление адвоката

        String sql = "delete from employee where idemployee = ?";
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

    public ObservableList<Lawyer> searchLawyer(int id) {//поиск адвоката по ID

        ObservableList<Lawyer> lawyers = FXCollections.observableArrayList();

        String sql = String.format("select * from employee where idemployee = '%s' ", id);
        try {
            PreparedStatement statement = connection.prepareStatement(sql);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {

                Lawyer lawyer = new Lawyer();
                lawyer.setId(resultSet.getInt("idemployee"));
                lawyer.setFirstName(resultSet.getString("firstname"));
                lawyer.setLastName(resultSet.getString("lastname"));
                lawyer.setPatronymic(resultSet.getString("patronymic"));
                lawyer.setNumberPhone(resultSet.getString("phonenumber"));
                lawyers.add(lawyer);
            }

            resultSet.close();
            statement.close();

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        return lawyers;
    }

    public ObservableList<Lawyer> searchLawyer2(String name) {//поиск адвоката по фамилии

        ObservableList<Lawyer> lawyers = FXCollections.observableArrayList();

        String sql = "select * from employee where firstname like '%" + name + "%'";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {

                Lawyer lawyer = new Lawyer();
                lawyer.setId(resultSet.getInt("idemployee"));
                lawyer.setLastName(resultSet.getString("firstname"));
                lawyer.setFirstName(resultSet.getString("lastname"));
                lawyer.setPatronymic(resultSet.getString("patronymic"));
                lawyer.setExperience(resultSet.getInt("experience"));
                lawyer.setNumberPhone(resultSet.getString("phonenumber"));

                lawyers.add(lawyer);
            }

            resultSet.close();
            statement.close();

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        return lawyers;
    }
}
