/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package esd.pagerank;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.stage.FileChooser;

/**
 *
 * @author Javier
 */
public class FXMLDocumentController implements Initializable {
    
    @FXML
    private ScrollPane scrollPaneM;
    
    GridPane matrizM =  new GridPane();
    ArrayList<String> info = new ArrayList();
     ArrayList<Integer> sumasM = new ArrayList();
    
    
    
    
    //(nombredelArchivo:nombredelinks:numero),(nombredelArchivo:nombredelinks:numero)
    private void setContenidoTabla(){
        System.out.println("filas: "+matrizM.getRowConstraints().size());
        
        System.out.println("columnas: "+matrizM.getColumnConstraints().size());
        for (int i = 1; i < matrizM.getColumnConstraints().size(); i++) {
            for (int j = 1; j <= matrizM.getRowConstraints().size(); j++) {
                String[] aux = info.get(j-1).split(":");
                for (int k = 1; k < aux.length; k++) {
                    if(k%2 != 0){
                        int columna = findColumna(aux[k]);
                        matrizM.add(new Label(aux[k+1]),  columna, j);
                    }
                }
                
            }
        }
    }
    private int findColumna(String archivo){
        for (int k = 0; k < matrizM.getColumnConstraints().size(); k++) {
            Label aux = (Label) getCelda(0, k, matrizM);
            if(aux.getText().equals(archivo)){
                return k;
            }
        }
        return 0;
    }
     public Node getCelda(final int row,final int column,GridPane gridPane) {
        Node result = null;
        ObservableList<Node> childrens = gridPane.getChildren();
        for(Node node : childrens) {
            if(matrizM.getRowIndex(node) == row && matrizM.getColumnIndex(node) == column) {
                result = node;
                break;
            }
        }
        return result;
    }
     private void getSumasMatrizM(){
         
     }
    
    private void setFilas(){
        
         for (int i = 0; i < info.size(); i++) {
            RowConstraints con = new RowConstraints();
            // Here we set the pref height of the row, but you could also use .setPercentHeight(double) if you don't know much space you will need for each label.
            con.setPrefHeight(40);
            
            matrizM.getRowConstraints().add(con);
            String[] aux = info.get(i).split(":");
            
            matrizM.add(new Label(aux[0]),  0, i+1);
            
        }
         
    }
    
    private void setColumnas(){
       
        ColumnConstraints column = new ColumnConstraints(100);
        matrizM.getColumnConstraints().add(column);
        matrizM.add(new Label(), 0, 0);
         for (int i = 0; i < info.size(); i++) {
            column = new ColumnConstraints(100);
            matrizM.getColumnConstraints().add(column);
            String[] aux = info.get(i).split(":");
            matrizM.add(new Label(aux[0]), i+1, 0);
        }
    }
    
    public void setMatrizM(){
        setFilas();
        setColumnas();
        setContenidoTabla();
        matrizM.setGridLinesVisible(true);
        scrollPaneM.setContent(matrizM);
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            getFiles();
        } catch (IOException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
        setMatrizM();
    }   
    
    public void getFiles() throws IOException{
        PageRank a = new PageRank();
        info =  a.matrizM();
        
    }
    
}
