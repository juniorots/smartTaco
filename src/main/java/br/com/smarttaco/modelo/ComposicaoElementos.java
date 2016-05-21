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
public class ComposicaoElementos extends DomainObject {
    private String numeroAlimento;
    private String descricaoAlimento;
    private String grupo; 

    public String getNumeroAlimentoLabel() {
        return "Número do Alimento";
    }

    public String getDescricaoAlimentoLabel() {
        return "Descrição dos alimentos";
    }

    public String getGrupo() {
        return grupo;
    }

    public void setGrupo(String grupo) {
        this.grupo = grupo;
    }

    public String getNumeroAlimento() {
        return numeroAlimento;
    }

    public void setNumeroAlimento(String numeroAlimento) {
        this.numeroAlimento = numeroAlimento;
    }

    public String getDescricaoAlimento() {
        return descricaoAlimento;
    }

    public void setDescricaoAlimento(String descricaoAlimento) {
        this.descricaoAlimento = descricaoAlimento;
    }

    /*
     * Captando o descritivo legivel do campo...
     */
    public String getLabelCorrente(String nome) {
        Class<ComposicaoElementos> classe = ComposicaoElementos.class;
        String valor = "";
        for ( Field atributo: classe.getDeclaredFields() ) {
            if ( nome.equalsIgnoreCase( atributo.getName() )) {
                try {
                    Method method = ComposicaoElementos.class.getMethod( montarNomeMetodo( atributo.getName() ) );
                    valor = (String) method.invoke( new ComposicaoElementos() );
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
        Class<ComposicaoElementos> classe = ComposicaoElementos.class;
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
}
