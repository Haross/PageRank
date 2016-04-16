/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package esd.pagerank;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author Javier
 */
public class TablaRangos {
      StringProperty web;
    StringProperty pr;

    
    
    
     public String getWeb() {
        return web.get();
    }

    public void setWeb(String web) {
        this.web = new SimpleStringProperty(web);
    }

    public StringProperty webProperty() {
        return web;
    }
     public String getPR() {
        return pr.get();
    }

    public void setPR(String pr) {
        this.pr = new SimpleStringProperty(pr);
    }

    public StringProperty prProperty() {
        return pr;
    }
   
}
