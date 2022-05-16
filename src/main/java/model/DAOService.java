package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DAOService {//Класс запросов с таблицей "услуги"
    private Connection connection;

    public DAOService() {
        connection = ConnectionUtil.conDB();
    }


    public ObservableList<Service> selectService() {//получение всех услуг

        ObservableList<Service> services = FXCollections.observableArrayList();

        String sql = "select * from service ";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {

                Service service = new Service();

                service.setIdService(resultSet.getInt("idservice"));
                service.setTypeservice(resultSet.getString("typeservice"));
                service.setPrice(Float.parseFloat(resultSet.getString("price")));
                service.setDescription(resultSet.getString("description"));

                services.add(service);
            }

            resultSet.close();
            statement.close();

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        return services;
    }


    public List<Service> searchService1(String typeservice) {//поиск услуги по названию

        List<Service> services = new ArrayList<>();

        String sql = "select * from service where typeservice like '%" + typeservice + "%'";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {

                Service service = new Service();

                service.setIdService(resultSet.getInt("idservice"));
                service.setTypeservice(resultSet.getString("typeservice"));
                service.setPrice(Float.parseFloat(resultSet.getString("price")));
                service.setDescription(resultSet.getString("description"));
                services.add(service);
            }

            resultSet.close();
            statement.close();

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        return services;
    }

    public List<Service> searchService2(float price) {//поиск услуги по цене

        List<Service> services = new ArrayList<>();

        String sql = String.format("select * from service where price = '%s' ", price);
        try {
            PreparedStatement statement = connection.prepareStatement(sql);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {

                Service service = new Service();

                service.setIdService(resultSet.getInt("idservice"));
                service.setTypeservice(resultSet.getString("typeservice"));
                service.setPrice(Float.parseFloat(resultSet.getString("price")));
                service.setDescription(resultSet.getString("description"));
                services.add(service);
            }

            resultSet.close();
            statement.close();

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        return services;
    }

    public void insertService(Service service) {//добавление новой услуги
        String sql = "insert into service (typeservice,price,description) values (?,?,?)";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setString(1, service.getTypeservice());
            statement.setFloat(2, service.getPrice());
            statement.setString(3, service.getDescription());
            statement.execute();
            statement.close();

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

    }

    public void updateService(Service service) {//изменение данных услуги

        String sql = "update service set typeservice=?, price=?, description=? where idservice=?";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setString(1, service.getTypeservice());
            statement.setFloat(2, service.getPrice());
            statement.setString(3, service.getDescription());
            statement.setInt(4, service.getId());
            statement.execute();
            statement.close();

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

    }


    public void deleteService(Integer idService) {//удаление услуги

        String sql = "delete from service where idservice = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setInt(1, idService);

            statement.execute();
            statement.close();

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }


}



