/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.workshop.controllers;

import static edu.workshop.controllers.AfficherCategorieBackController.C;
import edu.workshop.services.Categorie1CRUD;
import edu.workshop.services.Produit1CRUD;
import edu.worshop.interfaces.CategorieCRUD;
import edu.worshop.interfaces.ProduitCRUD;
import edu.worshop.model.Categorie;
import edu.worshop.model.Produit;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import java.util.logging.Logger;
import java.util.logging.Level;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.stage.StageStyle;



/**
 * FXML Controller class
 *
 * @author lenovo
 */
public class AfficherProduitBackController implements Initializable {

    @FXML
    private ListView<Produit> AfficherProduitBackfx;
      static int categorie_id;
static String nom_produit;
static int prix_produit;
static String marque_produit, image;
static int quantite;
static Produit P = new Produit();
    @FXML
    private Button stat;
    private ObservableList<Produit> userList;
    @FXML
    private TextField searchField;

    
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
          ListView<Produit> list1 = AfficherProduitBackfx;
ProduitCRUD inter = new Produit1CRUD();
List<Produit> list2 = inter.afficherProduit();
  userList = FXCollections.observableArrayList(list2);
for (int i = 0; i < list2.size(); i++) {
    Produit P = list2.get(i);
    list1.getItems().add(P); // add Evenement to ListView
}
 // Ajouter une fonction de recherche
        FilteredList<Produit> filteredList = new FilteredList<>(userList, p -> true);
        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredList.setPredicate(Produit -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                String lowerCaseFilter = newValue.toLowerCase();

                if (Produit.getNom_produit().toLowerCase().contains(lowerCaseFilter)) {
                    return true; // Recherche par nom complet
                } else if (Produit.getMarque_produit().toLowerCase().contains(lowerCaseFilter)) {
                    return true; // Recherche par adresse e-mail
                } 

                return false; // Aucune correspondance trouvée
            });
        });

        SortedList<Produit> sortedList = new SortedList<>(filteredList);
        list1.setItems(sortedList);

        // TODO
    }    

    @FXML
    private void ModifierProduitBack(ActionEvent event) {
         ListView<Produit> list = AfficherProduitBackfx;
        ProduitCRUD inter = new Produit1CRUD();
        int selectedIndex = list.getSelectionModel().getSelectedIndex();
        
  if (selectedIndex >=0){
            
        
        Produit p = list.getSelectionModel().getSelectedItem();
 
        int id = p.getId();
        String nom_produit = p.getNom_produit();
        double prix_produit = p.getPrix_produit();
        String marque_produit = p.getMarque_produit();
        String image = p.getImage();
        int quantite = p.getQuantite();
        P=p;
      
        
        
        
        try {

            Parent page1
                    = FXMLLoader.load(getClass().getResource("/edu/worshop/gui/ModifierProduitBack.fxml"));
            Scene scene = new Scene(page1);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(AfficherProduitBackController.class.getName()).log(Level.SEVERE, null, ex);
        
        }
        }else{showAlert("Veuillez sélectionner une réservation à modifier.");
  }
    }
     private void showAlert(String message) {
         Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    
    }

    @FXML
    private void SupprimerProduitBack(ActionEvent event) {
           ListView<Produit> list = (ListView<Produit>) AfficherProduitBackfx;
    ProduitCRUD inter = new Produit1CRUD();

    int selectedIndex = list.getSelectionModel().getSelectedIndex();
    if (selectedIndex >= 0) {
        Produit P = list.getSelectionModel().getSelectedItem();
        inter.supprimerProduit(P.getId());
        list.getItems().remove(selectedIndex);
    } else {
        showAlert("Veuillez sélectionner une réservation à supprimer.");
    }
    }

    @FXML
    private void ListeCategorieBack(ActionEvent event) {
       
        try {

            Parent page1 = FXMLLoader.load(getClass().getResource("/edu/worshop/gui/AfficherCategorieBack.fxml"));
            Scene scene = new Scene(page1);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(AfficherCategorieBackController.class.getName()).log(Level.SEVERE, null, ex);
            

        }
    }

    @FXML
    private void OnClickedStatistique(ActionEvent event) {
          try {
                   
            Parent parent = FXMLLoader.load(getClass().getResource("/edu/worshop/gui/StatProduit.fxml"));
            Scene scene = new Scene(parent);
            
            Stage stage = new Stage();
            stage.getIcons().add(new Image("/edu/worshop/image/logos.png"));
            stage.setScene(scene);
            stage.initStyle(StageStyle.UTILITY);
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(AfficherProduitBackController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void statbutton(ActionEvent event) {
        
        // Create a map to store the frequency of each type
        Map<String, Integer> typeFrequency = new HashMap<>();

        // Loop through the items in the TableView
        for (Produit p : AfficherProduitBackfx.getItems()) {
            String nom_produit = p.getNom_produit();

            if (typeFrequency.containsKey(nom_produit)) {
                typeFrequency.put(nom_produit, typeFrequency.get(nom_produit) + 1);
            } else {
                typeFrequency.put(nom_produit, 1);
            }
        }
    
        // Create a PieChart data set
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
        for (String nom_produit: typeFrequency.keySet()) {
            int frequency = typeFrequency.get(nom_produit);
            double percentage = (double) frequency /  AfficherProduitBackfx.getItems().size() * 100;

            String percentageText = String.format("%.2f%%", percentage);


            PieChart.Data slice = new PieChart.Data("NomProduit" + " " + percentageText, frequency);
            pieChartData.add(slice);
        }


    
         // Create a PieChart with the data set
        PieChart chart = new PieChart(pieChartData);
     
        // Show percentage values in the chart's tooltip
        for (final PieChart.Data data : chart.getData()) {
            Tooltip tooltip = new Tooltip();
            tooltip.setText(String.format("%.2f%%", (data.getPieValue() /  AfficherProduitBackfx.getItems().size() * 200)));
            Tooltip.install(data.getNode(), tooltip);
        }

        // Show the chart in a new window
        Scene scene = new Scene(chart);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
       }
    
    
    }
    
    
    
    
    
