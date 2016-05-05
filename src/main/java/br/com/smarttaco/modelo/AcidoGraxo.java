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
    private String acidosGraxos;
    private String nomeSistematico;
    private String nomeComum;
    private String grupo; 

    public String getAcidosGraxosLabel() {
        return "Ácidos Graxos";
    }

    public String getNomeSistematicoLabel() {
        return "Nome Sistemático";
    }

    public String getNomeComumLabel() {
        return "Nome Comum";
    }
    
    public String getGrupo() {
        return grupo;
    }

    public void setGrupo(String grupo) {
        this.grupo = grupo;
    }

    public String getAcidosGraxos() {
        return acidosGraxos;
    }

    public void setAcidosGraxos(String acidosGraxos) {
        this.acidosGraxos = acidosGraxos;
    }

    public String getNomeSistematico() {
        return nomeSistematico;
    }

    public void setNomeSistematico(String nomeSistematico) {
        this.nomeSistematico = nomeSistematico;
    }

    public String getNomeComum() {
        return nomeComum;
    }

    public void setNomeComum(String nomeComum) {
        this.nomeComum = nomeComum;
    }
}
