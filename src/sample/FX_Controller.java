package sample;

import controller.Controller;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.converter.IntegerStringConverter;
import model.Smartphone;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.net.URL;
import java.util.LinkedList;
import java.util.Optional;
import java.util.ResourceBundle;

public class FX_Controller implements Initializable {
    private Controller controller = new Controller();
    final String SMARTPHONE_FILE = "SmartPhone.dat";
    LinkedList<Smartphone> listFont = new LinkedList<>();
    LinkedList<Smartphone> listSM = new LinkedList<>();
    LinkedList<Smartphone> listBack = new LinkedList<>();
    @FXML
    private TableView<Smartphone> table;
    @FXML
    private TableColumn<Smartphone,String> code;
    @FXML
    private TableColumn<Smartphone,String> name;
    @FXML
    private TableColumn<Smartphone,String> color;
    @FXML
    private TableColumn<Smartphone,String> design;
    @FXML
    private TableColumn<Smartphone,Integer> price;
    @FXML
    private TableColumn<Smartphone,Integer> quantity;
    @FXML
    private TextField findText;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        table.setEditable(true);

        code.setCellValueFactory(new PropertyValueFactory<>("productCode"));
        code.setCellFactory(TextFieldTableCell.forTableColumn());
        code.setOnEditCommit((TableColumn.CellEditEvent<Smartphone,String> event)->{
            TablePosition<Smartphone,String> pos = event.getTablePosition();
            String newProductCode = event.getNewValue();
            int row = pos.getRow();
            Smartphone smartphone = event.getTableView().getItems().get(row);
            smartphone.setProductCode(newProductCode);
        });

        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        name.setCellFactory(TextFieldTableCell.forTableColumn());
        name.setOnEditCommit((TableColumn.CellEditEvent<Smartphone,String> event)->{
            TablePosition<Smartphone,String> pos = event.getTablePosition();
            String newName = event.getNewValue();
            int row = pos.getRow();
            Smartphone smartphone = event.getTableView().getItems().get(row);
            smartphone.setName(newName);
        });

        color.setCellValueFactory(new PropertyValueFactory<>("color"));
        color.setCellFactory(TextFieldTableCell.forTableColumn());
        color.setOnEditCommit((TableColumn.CellEditEvent<Smartphone,String> event)->{
            TablePosition<Smartphone,String> pos = event.getTablePosition();
            String newColor = event.getNewValue();
            int row = pos.getRow();
            Smartphone smartphone = event.getTableView().getItems().get(row);
            smartphone.setColor(newColor);
        });

        design.setCellValueFactory(new PropertyValueFactory<>("designBy"));
        design.setCellFactory(TextFieldTableCell.forTableColumn());
        design.setOnEditCommit((TableColumn.CellEditEvent<Smartphone,String> event)->{
            TablePosition<Smartphone,String> pos = event.getTablePosition();
            String newDesign = event.getNewValue();
            int row = pos.getRow();
            Smartphone smartphone = event.getTableView().getItems().get(row);
            smartphone.setDesignBy(newDesign);
        });

        price.setCellValueFactory(new PropertyValueFactory<>("price"));
        price.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        price.setOnEditCommit((TableColumn.CellEditEvent<Smartphone,Integer> event)->{
            TablePosition<Smartphone,Integer> pos = event.getTablePosition();
            int newPrice = event.getNewValue();
            int row = pos.getRow();
            Smartphone smartphone = event.getTableView().getItems().get(row);
            smartphone.setPrice(newPrice);
        });

        quantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        quantity.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        quantity.setOnEditCommit((TableColumn.CellEditEvent<Smartphone,Integer> event)->{
            TablePosition<Smartphone,Integer> pos = event.getTablePosition();
            int newQuantity = event.getNewValue();
            int row = pos.getRow();
            Smartphone smartphone = event.getTableView().getItems().get(row);
            smartphone.setQuantity(newQuantity);
        });

        try {
            listFont = controller.readSmartPhoneFromFile(SMARTPHONE_FILE);
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < 15; i++) {
            Smartphone smartphone = listFont.poll();
            listSM.add(smartphone);
        }
        ObservableList<Smartphone> list = FXCollections.observableArrayList(listSM);
        table.setItems(list);
    }

    public void back(ActionEvent actionEvent) {
        if(!listBack.isEmpty()){
            while (!listSM.isEmpty()){
                Smartphone smartphone = listSM.removeLast();
                listFont.addFirst(smartphone);
            }
            for (int i = 0; i < 15; i++) {
                if(listBack.isEmpty()) break;
                Smartphone smartphone = listBack.removeLast();
                listSM.addFirst(smartphone);
            }
        }
        ObservableList<Smartphone> list = FXCollections.observableArrayList(listSM);
        table.setItems(list);
    }

    public void next(ActionEvent actionEvent) {
        if(!listFont.isEmpty()){
            while (!listSM.isEmpty()){
                Smartphone smartphone = listSM.poll();
                listBack.add(smartphone);
            }
            for (int i = 0; i < 15; i++) {
                if(listFont.isEmpty()) break;
                Smartphone smartphone = listFont.poll();
                listSM.add(smartphone);
            }
        }
        ObservableList<Smartphone> list = FXCollections.observableArrayList(listSM);
        table.setItems(list);
    }

    public void saveChange(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Save change");
        alert.setContentText("Are you sure want to save all ?");
        Optional<ButtonType> option = alert.showAndWait();
        if (option.get() == ButtonType.OK) {
            LinkedList<Smartphone> smartphones = new LinkedList<>(listBack);
            smartphones.addAll(listSM);
            smartphones.addAll(listFont);
            controller.writeSmartPhoneToFile(SMARTPHONE_FILE,smartphones);
        }
    }

    public void showAll(ActionEvent actionEvent) throws IOException {
        LinkedList<Smartphone> smartphones = new LinkedList<>(listBack);
        smartphones.addAll(listSM);
        smartphones.addAll(listFont);
        ObservableList<Smartphone> list = FXCollections.observableArrayList(smartphones);
        table.setItems(list);
    }

    public void findProduct(ActionEvent actionEvent) {
        LinkedList<Smartphone> smartphones = new LinkedList<>(listBack);
        smartphones.addAll(listSM);
        smartphones.addAll(listFont);
        String str = findText.getText();
        LinkedList<Smartphone> smFind;
        if(str.matches("^\\d+$")){
            int price = Integer.parseInt(str);
            smFind = controller.findProduct(price,smartphones);
        } else {
            smFind = controller.findProduct(str,smartphones);
        }
        ObservableList<Smartphone> list = FXCollections.observableArrayList(smFind);
        table.setItems(list);
    }

    public void addProduct(ActionEvent actionEvent) {
        Smartphone newProduct = new Smartphone("000000","------",
                "------",0,"------", 0);
        while (listFont.size() >=15) {
            while (!listSM.isEmpty()){
                Smartphone smartphone = listSM.poll();
                listBack.add(smartphone);
            }
            for (int i = 0; i < 15; i++) {
                Smartphone smartphone = listFont.poll();
                listSM.add(smartphone);
            }
        }
        if(listFont.size() != 0) {
            listFont.add(newProduct);
            table.setItems(FXCollections.observableArrayList(listFont));
        }else {
            listSM.add(newProduct);
            table.setItems(FXCollections.observableArrayList(listSM));
        }
    }
}
