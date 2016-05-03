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
    

    public String getCampo01Label() {
        return "Teste campo 01";
    }

    public String getCampo02Label() {
        return "Teste campo 02";
    }

    public String getCampo03Label() {
        return "Teste campo 03";
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
