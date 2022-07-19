package controller;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import model.CustomerTM;

import java.util.Optional;

public class TableFormController {
    public TableView<CustomerTM> tblCustomers;
    public TextField txtId;
    public TextField txtName;
    public TextField txtAddress;
    public Button btnSaveCustomer;
    public Button btnNewCustomer;
    public Button btnDeleteCustomer;

    public void initialize() {

        btnDeleteCustomer.setDisable(true);

        tblCustomers.getColumns().get(0).
                setCellValueFactory(new PropertyValueFactory<>("id"));

        tblCustomers.getColumns().get(1).
                setCellValueFactory(new PropertyValueFactory<>("name"));

        tblCustomers.getColumns().get(2).
                setCellValueFactory(new PropertyValueFactory<>("address"));

        ObservableList<CustomerTM> olRows = tblCustomers.getItems();
        CustomerTM c001 = new CustomerTM("C001", "Eranga", "Gampaha");
        olRows.add(c001);
        olRows.add(new CustomerTM("C002", "Nuwan", "Moratuwa"));

        btnSaveCustomer.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                //ObservableList<CustomerTM> olCustomers = tblCustomers.getItems();


                /*Validation Part*/
                if (txtId.getText().isBlank() || txtName.getText().isBlank()
                        || txtAddress.getText().isBlank()) {
                    new Alert(Alert.AlertType.ERROR, "All fields should be filled.").showAndWait();
                    txtId.requestFocus();
                    return;
                }

                for (CustomerTM olRow : olRows) {
                    //System.out.println(olRow);
                    if (olRow.getId().equals(txtId.getText())) {
                        new Alert(Alert.AlertType.ERROR, "Duplicate Customer IDs are not allowed").showAndWait();
                        txtId.requestFocus();
                        return;
                    }
                }
//--------------------------------------------------------------------------------------
              /*  String id = txtId.getText();
                String name = txtName.getText();
                String address = txtAddress.getText();*/

                /*CustomerTM newCustomer = new CustomerTM(id, name, address);
                olCustomers.add(newCustomer);*/

                olRows.add(new CustomerTM(txtId.getText(), txtName.getText(), txtAddress.getText()));

               /* txtId.clear();
                txtName.clear();
                txtAddress.clear();*/
                txtId.requestFocus();

                tblCustomers.getSelectionModel().clearSelection();
            }
        });

        btnNewCustomer.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                txtId.clear();
                txtName.clear();
                txtAddress.clear();
                txtId.requestFocus();
            }
        });

        tblCustomers.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<CustomerTM>() {
            @Override
            public void changed(ObservableValue<? extends CustomerTM> observableValue,
                                CustomerTM previouslySelectedCustomer,
                                CustomerTM currentlySelectedCustomer) {
                /*System.out.println("Selecting");
                System.out.println("Previous : " + previouslySelectedCustomer);
                System.out.println("Current : " + currentlySelectedCustomer);*/

                if(currentlySelectedCustomer == null){
                    btnDeleteCustomer.setDisable(true);
                    txtId.clear();
                    txtName.clear();
                    txtAddress.clear();
                    txtId.setEditable(true);
                    btnSaveCustomer.setText("Save Customer");
                    return;
                }
                btnDeleteCustomer.setDisable(false);
                txtId.setText(currentlySelectedCustomer.getId());
                //txtId.setDisable(true);
                txtId.setEditable(false);
                txtName.setText(currentlySelectedCustomer.getName());
                txtAddress.setText(currentlySelectedCustomer.getAddress());

                btnSaveCustomer.setText("Update Customer");
            }
        });


        btnDeleteCustomer.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
               /* CustomerTM selectedCustomer = tblCustomers.getSelectionModel().getSelectedItem();
                int focusedIndex = tblCustomers.getSelectionModel().getFocusedIndex();
                System.out.println(selectedCustomer);
                System.out.println(focusedIndex);*/

                ObservableList<CustomerTM> olCustomer = tblCustomers.getItems();
                CustomerTM selectedCustomer = tblCustomers.getSelectionModel().getSelectedItem();

               /* if(selectedCustomer == null){
                    return;
                }*/

                Optional<ButtonType> selectedOption = new Alert(Alert.AlertType.CONFIRMATION,
                        "Are you sure to delete this customer?",ButtonType.YES,ButtonType.NO
                ).showAndWait();
                if(selectedOption.get() == ButtonType.YES){
                    olCustomer.remove(selectedCustomer);
                    tblCustomers.getSelectionModel().clearSelection();
                }

                //tblCustomers.getSelectionModel().clearSelection();
            }
        });
    }
}
