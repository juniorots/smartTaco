/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.smarttaco.modelo;

/**
 *
 * @author Jose Alves
 */
public class ColunaDinamica {
    String value;
    String headerText;

    public ColunaDinamica( String value, String headerText) {
        this.value = value;
        this.headerText = headerText;
    }
    
    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getHeaderText() {
        return headerText;
    }

    public void setHeaderText(String headerText) {
        this.headerText = headerText;
    }
}
