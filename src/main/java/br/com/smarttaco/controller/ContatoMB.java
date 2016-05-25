/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.smarttaco.controller;

import br.com.smarttaco.util.EnviarEmail;
import br.com.smarttaco.util.Util;
import java.io.Serializable;
import java.util.ArrayList;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import org.primefaces.context.RequestContext;

/**
 *
 * @author Jose Alves
 */
@ManagedBean
public class ContatoMB implements Serializable {
    
    private String assunto;
    private String mensagem;

    public void enviarNotificacao() {
        FacesMessage message = null;
        if ( Util.isEmpty( Util.captarUsuarioSessao() )) {
            message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "É porque não dá mesmo!", 
                "Ops... Identifiquei que você não entrou no sistema!!<br /> "
                    + "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"
                    + "Preciso que você faça isso, para enviar o e-mail!!");
        } else {
            ArrayList<String> email = new ArrayList<String>();
            String tmp = "&nbsp;&nbsp;<strong><span style='background-color: #E6E6FA'>Email do remetente: "+
                    Util.captarUsuarioSessao().getEmail()+"</strong></span><br /><br />"+
                    "Conteúdo digitado pelo usuário: <br /><br />"+
                    "<span style='background-color: #FFFFF0; width:400px; height: 400px;'>";
            setMensagem( tmp+getMensagem()+"</span>");
            EnviarEmail.tratarEnvio(email, getAssunto(), getMensagem());
            setMensagem("");
            
            message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Hum...", 
                "OK!!! :-) Em breve entraremos em contato...");
        }
        RequestContext.getCurrentInstance().showMessageInDialog(message);
    }
    
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
