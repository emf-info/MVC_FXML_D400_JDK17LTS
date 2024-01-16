package views;

import models.PrevisionMeteo;
import models.Voiture;
import ctrl.Controller;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class View implements Initializable {

    private final Controller controller;

    @FXML
    private TextField textFieldPrevisionMeteo;
    @FXML
    private ListView<Voiture> listViewVoituresDisponibles;
    @FXML
    private ListView<Voiture> listViewVoituresLouees;

    public View(Controller controller) {
        this.controller = controller;
    }

    public void start() {
        Platform.startup(() -> {
            try {
                Stage mainStage = new Stage();
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("view.fxml"));
                fxmlLoader.setControllerFactory(type -> {
                    return this;
                });
                Parent root = (Parent) fxmlLoader.load();
                Scene principalScene = new Scene(root);
                mainStage.setScene(principalScene);
                mainStage.setTitle("Application de démonstration");
                mainStage.show();
            } catch (IOException ex) {
                ex.printStackTrace();
                Platform.exit();
            }
        });
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    @FXML
    private void actionRafraichirPrevisionMeteo(ActionEvent event) {
        controller.actionRafraichirPrevisionMeteo();
    }

    @FXML
    private void actionLouerVoiture(ActionEvent event) {
        Voiture voitureSelectionnee = listViewVoituresDisponibles.getSelectionModel().getSelectedItem();
        if (voitureSelectionnee != null) {
            controller.actionLouerUneVoiture(voitureSelectionnee);
        } else {
            messageErreur("Il faut sélectionner une voiture d'abord !");
        }
    }

    @FXML
    private void actionRestituerVoiture(ActionEvent event) {
        Voiture voitureSelectionnee = listViewVoituresLouees.getSelectionModel().getSelectedItem();
        if (voitureSelectionnee != null) {
            controller.actionRestituerUneVoiture(voitureSelectionnee);
        } else {
            messageErreur("Il faut sélectionner une voiture d'abord !");
        }
    }

    public void messageInfo(String message) {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, message, ButtonType.OK);
            alert.setHeaderText(null);
            alert.showAndWait();
        });
    }

    public void messageErreur(String message) {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.ERROR, message, ButtonType.OK);
            alert.setHeaderText(null);
            alert.showAndWait();
        });
    }

    public void afficheDernierePrevision(PrevisionMeteo prevision) {
        Platform.runLater(() -> {
            textFieldPrevisionMeteo.setText(prevision.getPrevision());
        });
    }

    private ArrayList<Voiture> tableauEnListeSansNulls(Voiture[] voitures) {
        ArrayList<Voiture> liste = new ArrayList<>(Arrays.asList(voitures));
        liste.removeIf(Objects::isNull);
        return liste;
    }

    public void afficheVoituresDisponibles(Voiture[] voituresDisponibles, Voiture voitureASelectionner) {
        Platform.runLater(() -> {
            listViewVoituresDisponibles.setItems(FXCollections.observableArrayList(tableauEnListeSansNulls(voituresDisponibles)));
            if (voitureASelectionner != null) {
                listViewVoituresDisponibles.getSelectionModel().select(voitureASelectionner);
                Platform.runLater(listViewVoituresDisponibles::requestFocus);
            } else {
                listViewVoituresDisponibles.getSelectionModel().clearSelection();
            }
        });
    }

    public void afficheVoituresLouees(Voiture[] voituresLouees, Voiture voitureASelectionner) {
        Platform.runLater(() -> {
            listViewVoituresLouees.setItems(FXCollections.observableArrayList(tableauEnListeSansNulls(voituresLouees)));
            if (voitureASelectionner != null) {
                listViewVoituresLouees.getSelectionModel().select(voitureASelectionner);
                Platform.runLater(listViewVoituresLouees::requestFocus);
            } else {
                listViewVoituresLouees.getSelectionModel().clearSelection();
            }
        });
    }
}
