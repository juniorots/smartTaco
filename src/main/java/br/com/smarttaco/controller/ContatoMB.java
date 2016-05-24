/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.smarttaco.controller;

import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import org.primefaces.event.SelectEvent;

/**
 *
 * @author Jose Alves
 */
@ManagedBean
public class ContatoMB implements Serializable {
    
    private String assunto;
    private String mensagem;

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }
    
    public String getAssunto() {
        return assunto;
    }

    public void setAssunto(String assunto) {
        this.assunto = assunto;
    }
    
    
}
