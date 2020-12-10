package ClassPackages.AccountantPackage.Controllers;

import ClassPackages.Interfaces.StoreIdInterface;
import ClassPackages.MainPackage.Controllers.Controller;
import ClassPackages.MainPackage.Models.Client;
import ClassPackages.MainPackage.Models.Handler;
import ClassPackages.MainPackage.Models.ScreenHandler;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ScholarshipTransController implements Initializable, StoreIdInterface {

    public Button backButton;
    public TextField changeDateButton;
    public ListView patronymic;
    public ListView name;
    public ListView lastName;
    public ChoiceBox cardButton;
    public ListView date;
    public ListView recordBook;
    int stored_id;
    Handler handler = Client.get_handler();

    public void backButton(ActionEvent actionEvent) throws IOException {
        handler.write("returnId");
        handler.write(get_stored_id());
        Controller.CurrentStage = (Stage) backButton.getScene().getWindow();
        Controller.CurrentStage.close();
        ScreenHandler screen = new ScreenHandler("../../AccountantPackage/FXML/AccountantActions.fxml", "BSUIR TASK 2020");
        Controller.CurrentStage = screen.get_new_stage();
    }

    @Override
    public int get_stored_id() {
        return this.stored_id;
    }

    @Override
    public void set_stored_id(int new_id) {
        this.stored_id = new_id;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            handler.write("AccScholarshipInfo");
            set_stored_id((Integer)handler.read());
            System.out.println(get_stored_id());
            handler.write(get_stored_id());
            while (true) {
                ArrayList data = (ArrayList) handler.read();
                if (data.get(0).equals("stop")) {
                    return;
                }
                name.getItems().add(data.get(0));
                lastName.getItems().add(data.get(1));
                patronymic.getItems().add(data.get(2));
                recordBook.getItems().add(data.get(3));
                date.getItems().add(data.get(4));
                cardButton.getItems().add(data.get(5));
            }
        } catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
        }
    }
    public void onClick() throws IOException {
        if (cardButton.getValue() != null && !changeDateButton.getText().equals("")) {
            handler.write("accountantChangeScolarship");
            handler.write((String) cardButton.getValue());
            handler.write((String) changeDateButton.getText());

            handler.write("returnId");
            handler.write(get_stored_id());
            Controller.CurrentStage = (Stage) backButton.getScene().getWindow();
            Controller.CurrentStage.close();
            ScreenHandler screen = new ScreenHandler("../../AccountantPackage/FXML/AccountantScholarshipTransaction.fxml", "BSUIR TASK 2020");
            Controller.CurrentStage = screen.get_new_stage();
        }
    }
}
