/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.smarttaco.modelo;

import br.com.smarttaco.controller.CabecalhoMB;
import br.com.smarttaco.framework.persistence.DomainObject;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.persistence.Entity;

/**
 *
 * @author Jose Alves
 */
@Entity
public class Laboratorio extends DomainObject {
    
    private String noLaboratorio;
    private String estado;
    private String grupo;
    
    public String getNoLaboratorioLabel() {
        return "Laboratório";
    }
    
    public String getEstadoLabel() {
        return "Estado";
    }

    public String getNoLaboratorio() {
        return noLaboratorio;
    }

    public void setNoLaboratorio(String noLaboratorio) {
        this.noLaboratorio = noLaboratorio;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getGrupo() {
        return grupo;
    }

    public void setGrupo(String grupo) {
        this.grupo = grupo;
    }
    
    /*
     * Captando o descritivo legivel do campo...
     */
    public String getLabelCorrente(String nome) {
        Class<Laboratorio> classe = Laboratorio.class;
        String valor = "";
        for ( Field atributo: classe.getDeclaredFields() ) {
            if ( nome.equalsIgnoreCase( atributo.getName() )) {
                try {
                    Method method = Laboratorio.class.getMethod( montarNomeMetodo( atributo.getName() ) );
                    valor = (String) method.invoke( new Laboratorio() );
                    
                    if ( valor.contains("<nota>") ) {
                        Pattern p = Pattern.compile("(?<=\\<nota\\>)(\\s*.*\\s*)(?=\\<\\/nota\\>)");
                        Matcher m = p.matcher(valor);    
                        
                        CabecalhoMB.getInstance().getTextoLegivel().put(valor, valor.substring(0, valor.indexOf("<") ) ); 
                        if ( m.find() == true ) {
                            CabecalhoMB.getInstance().getCodigoNota().put(valor, m.group(1) );
                        }
                    }
                } catch (Exception e) {
                    valor = "error...";
                    e.printStackTrace();
                }
            }
        }
        return valor;
    }
    
    /*
     * Conteudo completo do atributo...
     */
    public String getConteudoAtributo( String nome, Laboratorio lab ) {
        Class<Laboratorio> classe = Laboratorio.class;
        String valor = "";
        for ( Field atributo: classe.getDeclaredFields() ) {
            if ( nome.equalsIgnoreCase( atributo.getName() )) {
                try {
                    Method method = Laboratorio.class.getMethod( montarNomeMetodo( atributo.getName() ) );
                    valor = (String) method.invoke( lab );
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
        Class<Laboratorio> classe = Laboratorio.class;
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
