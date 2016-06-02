/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.smarttaco.controller;

import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.event.ActionEvent;
import javax.faces.event.AjaxBehaviorEvent;

/**
 * @author Jose Alves
 */
@ManagedBean
@SessionScoped
public class CabecalhoMB implements Serializable {
    
    private boolean visible;
    private String urlImagem = "/resources/images/book64.png";
    private String titleImagem = "Notas descritivas";
    private String descricao;

    public void showAppropriateButton(ActionEvent e) {
        visible = !visible;
    }

    public void closeListener(AjaxBehaviorEvent event) {
         visible = false; 
    }

    public void gerarDescricao(String codigo) {
        visible = !visible;
        descricao = "RAE (equivalente de atividade de retinol) = 1 μg de retinol + 1/12 μg de trans beta-caroteno + 1/24 μg de cis\n" +
"beta-caroteno + 1/24 μg de outros trans carotenóides pró-vitamínicos A + 1/48 de outros cis carotenóides pró-\n" +
"vitamínicos A (25, 26).";
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public String getUrlImagem() {
        return urlImagem;
    }

    public void setUrlImagem(String urlImagem) {
        this.urlImagem = urlImagem;
    }

    public String getTitleImagem() {
        return titleImagem;
    }

    public void setTitleImagem(String titleImagem) {
        this.titleImagem = titleImagem;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}
