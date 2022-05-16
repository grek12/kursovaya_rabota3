package controller;


import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import model.ConnectionUtil;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;


public class LoginController implements Initializable {//класс контроллер формы авторизации

    @FXML
    private Label lblErrors;

    @FXML
    private TextField txtUsername;

    @FXML
    private TextField txtPassword;

    @FXML
    private Button btnSignin;


    Connection con = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;

    @FXML
    public void handleButtonAction(MouseEvent event) {//действие при нажатии на кнопку "войти"

        if (event.getSource() == btnSignin) {

            if (logIn().equals("Успешно")) {
                try {


                    Node node = (Node) event.getSource();
                    Stage stage = (Stage) node.getScene().getWindow();
                    stage.close();
                    Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/AppForm.fxml")));
                    stage.getIcons().add(new Image(LoginController.class.getResourceAsStream("/icon.png")));
                    stage.setScene(scene);
                    stage.show();

                } catch (IOException ex) {
                    System.err.println(ex.getMessage());
                }

            }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {//вывод сообщения после проверки соединение с бд
        // TODO
        if (con == null) {
            lblErrors.setTextFill(Color.TOMATO);
            lblErrors.setText("Ошибка подключение к серверу: проверьте подключение");
        } else {
            lblErrors.setTextFill(Color.GREEN);
            lblErrors.setText("Подключение к серверу установлено");
        }
    }

    public LoginController() {
        con = ConnectionUtil.conDB();
    }//подключение к бд


    private String logIn() {//проверка данных, отправка запроса в бд с введенными данным
        String status = "Успешно";
        String login = txtUsername.getText();
        String password = txtPassword.getText();
        if (login.isEmpty() || password.isEmpty()) {
            setLblError(Color.TOMATO, "Учетные данные не введены!");
            status = "Ошибка";
        } else {

            String sql = "SELECT * FROM admin Where login= ? and password=?";
            try {
                preparedStatement = con.prepareStatement(sql);
                preparedStatement.setString(1, login);
                preparedStatement.setString(2, password);
                resultSet = preparedStatement.executeQuery();
                if (!resultSet.next()) {
                    setLblError(Color.TOMATO, "Введите верные логин/пароль");
                    status = "Error";
                } else {
                    setLblError(Color.GREEN, "Вход в систему прошел Успешно..Перенаправление..");
                }
            } catch (SQLException ex) {
                System.err.println(ex.getMessage());
                status = "Exception";
            }
        }

        return status;
    }

    private void setLblError(Color color, String text) {//настройка сообщений
        lblErrors.setTextFill(color);
        lblErrors.setText(text);
        System.out.println(text);
    }

    public void nextfield() {//перенос строки при нажатии 'enter'
        txtUsername.setOnKeyPressed(event -> {
            if (event.getCode().equals(KeyCode.ENTER)) {
                txtPassword.requestFocus();
            }
        });
    }

    @FXML
    public void nextBut() {//эмуляция нажатия на кнопку войти при нажатии 'enter'
        txtPassword.setOnKeyPressed(event -> {
            if (event.getCode().equals(KeyCode.ENTER)) {
                handleButtonAction2(event);
            }

        });

    }

    @FXML
    public void handleButtonAction2(KeyEvent event) {//запуск второй формы

        if (logIn().equals("Успешно")) {
            try {

                Node node = (Node) event.getSource();
                Stage stage = (Stage) node.getScene().getWindow();
                stage.close();
                Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/AppForm.fxml")));
                stage.setScene(scene);
                stage.show();

            } catch (IOException ex) {
                System.err.println(ex.getMessage());
            }


        }
    }

    public void DialogInfo() {//вывод информационного сообщения
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Информация");
        alert.setHeaderText(null);
        alert.setContentText("Для восстановления логина/пароля, обратитесь к системному администратору!");
        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.getStylesheets().add(
                getClass().getResource("/fullpackstyling.css").toExternalForm());
        dialogPane.getStyleClass().add("myDialog");
        alert.showAndWait();
    }

}
