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
public class TabelaCarrocel {
    private String url; // representara o nome fisico da imagem referente a tabela

    public TabelaCarrocel() {
    }
    
    public TabelaCarrocel(String url) {
        this.url = url;
    }
    
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
    
}
