/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.smarttaco.controller;

import br.com.smarttaco.util.Util;
import java.io.Serializable;
import java.util.HashMap;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
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
    private HashMap<String, String> textoLegivel = new HashMap<>();
    private HashMap<String, String> codigoNota = new HashMap<>();

    private static CabecalhoMB cabecalho;
    
    public void showAppropriateButton(ActionEvent e) {
        visible = !visible;
    }
    
    public static CabecalhoMB getInstance() {
        if ( Util.isEmpty( cabecalho ) ) {
            cabecalho = new CabecalhoMB();
        }
        return cabecalho;
    }

    public void closeListener(AjaxBehaviorEvent event) {
         visible = false; 
    }

    public boolean contemTag(String arg) {
        if ( !Util.isEmpty(arg) ) {
            return arg.contains("<nota>");
        } else {
            return false;
        }
            
    }
    
    public void gerarDescricao( String codigos ) {
        visible = !visible;
        StringTokenizer tmp = new StringTokenizer(codigos, ",");
        setDescricao("");
        while ( tmp.hasMoreElements() ) {
            setDescricao( getDescricao() + Util.montarDescricaoLink( tmp.nextToken().replaceAll(" ", "") ));
        }
    }

    /**
     * Procurara por texto legivel ao usuario
     * @param arg
     * @return 
     */
    public String gerarTextoLegivel(String arg) {
        String retorno = arg;
        if ( !Util.isEmpty( textoLegivel.get(arg) ) ) {
            retorno = textoLegivel.get(arg);
        }
        return retorno;
    }
    
    /**
     * Indexando as notas de comentario
     * @param arg
     * @return 
     */
    public String gerarCodigoNota(String arg) {
        return codigoNota.get(arg);
    }
    
    /**
     * Identificando cada celula da tabela que devera
     * conter nota de descricao
     * @param arg 
     */
    public void indexarResultado(String arg) {
        if (arg.contains("<nota>")) {
            textoLegivel.put(arg, arg.substring(0, arg.indexOf("<")).trim());
            Pattern p = Pattern.compile("(?<=\\<nota\\>)(\\s*.*\\s*)(?=\\<\\/nota\\>)");
            Matcher m = p.matcher(arg);    

            if ( m.find() == true ) {
                CabecalhoMB.getInstance().getCodigoNota().put(arg, m.group(1) );
            }
        }
    }
    
    public HashMap<String, String> getTextoLegivel() {
        return textoLegivel;
    }

    public void setTextoLegivel(HashMap<String, String> textoLegivel) {
        this.textoLegivel = textoLegivel;
    }

    public HashMap<String, String> getCodigoNota() {
        return codigoNota;
    }

    public void setCodigoNota(HashMap<String, String> codigoNota) {
        this.codigoNota = codigoNota;
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
