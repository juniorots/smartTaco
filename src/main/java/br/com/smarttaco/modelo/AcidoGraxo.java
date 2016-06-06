/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.smarttaco.modelo;

import br.com.smarttaco.framework.persistence.DomainObject;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
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
    
    /*
     * Captando o descritivo legivel do campo...
     */
    public String getLabelCorrente(String nome) {
        Class<AcidoGraxo> classe = AcidoGraxo.class;
        String valor = "";
        for ( Field atributo: classe.getDeclaredFields() ) {
            if ( nome.equalsIgnoreCase( atributo.getName() )) {
                try {
                    Method method = AcidoGraxo.class.getMethod( montarNomeMetodo( atributo.getName() ) );
                    valor = (String) method.invoke( new AcidoGraxo() );
                } catch (Exception e) {
                    valor = "error...";
                    e.printStackTrace();
                }
            }
        }
        return valor;
    }
    
    /*
     * Captando o nome definitivo do atributo
     */
    public String getNomeAtributo(String nome) {
        Class<AcidoGraxo> classe = AcidoGraxo.class;
        String valor = "";
        for ( Field atributo: classe.getDeclaredFields() ) {
            if ( nome.equalsIgnoreCase( atributo.getName() )) {
                valor = atributo.getName();
            }
        }
        return valor;
    }
    
    /*
     * Descrevendo o nome do metodo...
     */
    private String montarNomeMetodo( String nome ) {
        return "get"+ nome.substring(0, 1).toUpperCase()+
            nome.substring(1, nome.length() )+"Label";
    }
    
    /**
     * Nome simples de metodo...
     * @param nome
     * @return 
     */
    public String montarNomeSimplesMetodo( String nome ) {
        return "get"+ nome.substring(0, 1).toUpperCase()+
            nome.substring(1, nome.length() );
    }
}
