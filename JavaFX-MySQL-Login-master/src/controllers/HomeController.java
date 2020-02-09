/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;

import java.awt.*;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Spinner;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.util.Callback;
import utils.ConnectionUtil;


public class HomeController implements Initializable {

    @FXML
    private TextField txtNaziv;
    @FXML
    private TextField txtDetalji;
    @FXML
    private TextArea txtOpis;
    @FXML
    private Spinner količina;
    @FXML
    private Spinner cijena;
    @FXML
    private Button btnSave;
    @FXML
    private ComboBox<String> txtKategorija;
    @FXML
    Label lblStatus;

    @FXML
    TableView tblData;

    PreparedStatement preparedStatement;
    Connection connection;

    public HomeController() {
        connection = (Connection) ConnectionUtil.conDB();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        txtKategorija.getItems().addAll("Olovke", "Kistovi", "Platna");
        txtKategorija.getSelectionModel().select("Olovke");
        fetColumnList();
        fetRowList();
//kolicina
        SpinnerValueFactory<Integer> količinaValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0,10000000);
        this.količina.setValueFactory(količinaValueFactory);
        količina.setEditable(true);
//cijena
        SpinnerValueFactory<Double> cijenaValueFactory = new SpinnerValueFactory.DoubleSpinnerValueFactory(0,10000000);
        this.cijena.setValueFactory(cijenaValueFactory);
        cijena.setEditable(true);
    }

    @FXML
    private void HandleEvents(MouseEvent event) {
        //check if not empty
        if (txtNaziv.getText().isEmpty() || txtDetalji.getText().isEmpty()) {
            lblStatus.setTextFill(Color.TOMATO);
            lblStatus.setText("Unesite sve podatke");
        } else {
            saveData();
        }

    }

    private void clearFields() {
        txtNaziv.clear();
        txtDetalji.clear();
        txtOpis.clear();
    }

    private String saveData() {

        try {
            String st = "INSERT INTO products ( naziv, detalji, opis, kategorija, cijena, količina) VALUES (?,?,?,?,?,?)";
            preparedStatement = (PreparedStatement) connection.prepareStatement(st);
            preparedStatement.setString(1, txtNaziv.getText());
            preparedStatement.setString(2, txtDetalji.getText());
            preparedStatement.setString(3, txtOpis.getText());
            preparedStatement.setString(4, txtKategorija.getValue().toString());
            preparedStatement.setString(5, cijena.getValue().toString());
            preparedStatement.setString(6, količina.getValue().toString());


            preparedStatement.executeUpdate();
            lblStatus.setTextFill(Color.GREEN);
            lblStatus.setText("Uspješno dodano");

            fetRowList();
            //clear fields
            clearFields();
            return "Success";

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            lblStatus.setTextFill(Color.TOMATO);
            lblStatus.setText(ex.getMessage());
            return "Exception";
        }
    }

    private ObservableList<ObservableList> data;
    String SQL = "SELECT * from products";

    //only fetch columns
    private void fetColumnList() {

        try {
            ResultSet rs = connection.createStatement().executeQuery(SQL);

            //SQL FOR SELECTING ALL OF CUSTOMER
            for (int i = 0; i < rs.getMetaData().getColumnCount(); i++) {
                //We are using non property style for making dynamic table
                final int j = i;
                TableColumn col = new TableColumn(rs.getMetaData().getColumnName(i + 1).toUpperCase());
                col.setCellValueFactory(new Callback<CellDataFeatures<ObservableList, String>, ObservableValue<String>>() {
                    public ObservableValue<String> call(CellDataFeatures<ObservableList, String> param) {
                        return new SimpleStringProperty(param.getValue().get(j).toString());
                    }
                });

                tblData.getColumns().removeAll(col);
                tblData.getColumns().addAll(col);

                System.out.println("Column [" + i + "] ");

            }

        } catch (Exception e) {
            System.out.println("Error " + e.getMessage());

        }
    }

    //fetches rows and data from the list
    private void fetRowList() {
        data = FXCollections.observableArrayList();
        ResultSet rs;
        try {
            rs = connection.createStatement().executeQuery(SQL);

            while (rs.next()) {
                //Iterate Row
                ObservableList row = FXCollections.observableArrayList();
                for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
                    //Iterate Column
                    row.add(rs.getString(i));
                }
                System.out.println("Row [1] added " + row);
                data.add(row);

            }

            tblData.setItems(data);
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }

}
