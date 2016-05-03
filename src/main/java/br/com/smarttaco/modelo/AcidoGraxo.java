/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.smarttaco.modelo;

import br.com.smarttaco.framework.persistence.DomainObject;
import javax.persistence.Entity;

/**
 *
 * @author Jose Alves
 */
@Entity
public class AcidoGraxo extends DomainObject {
    private String campo01;
    private String campo02;
    private String campo03;
    
    private String campo01Label = "Teste campo 01";
    private String campo02Label = "Teste campo 02";
    private String campo03Label = "Teste campo 03";

    public String getCampo01Label() {
        return campo01Label;
    }

    public void setCampo01Label(String campo01Label) {
        this.campo01Label = campo01Label;
    }

    public String getCampo02Label() {
        return campo02Label;
    }

    public void setCampo02Label(String campo02Label) {
        this.campo02Label = campo02Label;
    }

    public String getCampo03Label() {
        return campo03Label;
    }

    public void setCampo03Label(String campo03Label) {
        this.campo03Label = campo03Label;
    }

    public String getCampo01() {
        return campo01;
    }

    public void setCampo01(String campo01) {
        this.campo01 = campo01;
    }

    public String getCampo02() {
        return campo02;
    }

    public void setCampo02(String campo02) {
        this.campo02 = campo02;
    }

    public String getCampo03() {
        return campo03;
    }

    public void setCampo03(String campo03) {
        this.campo03 = campo03;
    }
    
    
}
