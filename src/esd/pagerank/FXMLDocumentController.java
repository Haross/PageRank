/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package esd.pagerank;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
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
    @FXML TableView<TablaRangos> tablaPR;
    @FXML TableColumn<TablaRangos,String> columnPR, columnWeb;
    private ObservableList<TablaRangos> lista = FXCollections.observableArrayList();

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
             String[] split = aux[0].split("@PR@");
            matrizM.add(new Label(split[0]),  0, i+1);
            
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
             String[] split = aux[0].split("@PR@");
            matrizM.add(new Label(split[0]), i+1, 0);
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
        columnPR.setCellValueFactory(cellData -> cellData.getValue().prProperty());
        columnWeb.setCellValueFactory(cellData -> cellData.getValue().webProperty());
        try {
            getFiles();
        } catch (IOException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
        setMatrizM();
       startPR();
       imprimirPr();
    }   
    
    public void getFiles() throws IOException{
        PageRank a = new PageRank();
        info =  a.matrizM();
        
    }
    public void imprimirPr(){
        for (int i = 0; i < info.size(); i++) {
            String[] split = info.get(i).split(":");
            String[] split1 = split[0].split("@PR@");
            System.out.println("Pagina: "+split1[0]+ " PR: "+split1[1]);
            TablaRangos t = new TablaRangos();
            t.setPR(split1[1]);
            t.setWeb(split1[0]);
            lista.add(t);
        }
        Collections.sort(lista, comparator);
       tablaPR.setItems(lista);
       
    }
    Comparator<TablaRangos> comparator = new Comparator<TablaRangos>() {
     

        @Override
        public int compare(TablaRangos o1, TablaRangos o2) {
            double a = Double.parseDouble(o1.getPR());
            double b = Double.parseDouble(o2.getPR());
            return (int) (a-b); 
        }
    };
    public void startPR(){
        for (int i = 0; i < 50; i++) {
            for (int j = 0; j < info.size(); j++) {
               
                String[] split = info.get(j).split(":");
                String[] split1 = split[0].split("@PR@");
                double pr = pageRankFunction(split1[0]);
                System.out.println("PR: "+pr +" : "+ split1[0]);
                String cadena = split1[0]+"@PR@"+pr;
                
                for (int k = 1; k < split.length; k++) {
                    cadena += ":"+split[k]; 
                }
                info.add(j, cadena);
                System.out.println("cadena: "+cadena);
                int aux =  j+1;
                info.remove(aux);
            }
        }
    }
    
    //(nombredelArchivo:nombredelinks:numero),(nombredelArchivo:nombredelinks:numero)
    public double pageRankFunction(String pagina){
        double d = 0.5;
        double aux = (1-d);
        double aux2  = 0;
        for (int i = 0; i < info.size(); i++) {
            String[] split = info.get(i).split(":");
             String[] split1 = split[0].split("@PR@");
            for (int j = 1; j < split.length; j++) {
                if(!split1[0].equals(pagina)){ //descarta enlaces de la misma página
                    System.out.println("pagina: "+split1[0]);
                    if(j%2 != 0 && split[j].equals(pagina)){
                        j++;
                        System.out.println("pagina2: "+split[j-1]);
                       // aux2 =  aux2 + getPR(split[j-1]);
                        aux2 =  aux2 + getPR(split1[0]);
                        
                    }
                }
                System.out.println("-------");
                
            }
        }
        double suma = aux + d*aux2;  
        if(pagina.equals("3.html"))
            System.out.println("aux= "+aux+"+ "+d+"*"+aux2+"="+ suma);
        return suma;
    }
    
    public double getPR(String pagina){
        System.out.println("**PR**: "+pagina);
        double c2 = c(pagina);
        if(c2 > 1){
            System.out.println(getPRofPage(pagina)/c2);
            return getPRofPage(pagina)/c2;
        }
        System.out.println(getPRofPage(pagina));
        return  getPRofPage(pagina);
    }
    
    public double getPRofPage(String pagina){
        for (int i = 0; i < info.size(); i++) {
            String[] split = info.get(i).split(":");
            String[] split1 = split[0].split("@PR@");
            if(split1[0].equals(pagina)){
                return Double.parseDouble(split1[1]);
            }
        }
        return 1;
    }
    
    public double c(String pagina){
        double aux  = 0;
        for (int i = 0; i < info.size(); i++) {
            String[] split = info.get(i).split(":");
            String[] split1 = split[0].split("@PR@");
            for (int j = 1; j < split.length; j++) {
                if(split1[0].equals(pagina)){
                    if(j%2 != 0){
                        j++;
                        aux = aux + Integer.parseInt(split[j]);
                    }
                }
            }
        }
        System.out.println("C:"+aux);
        return aux;
    }
}
