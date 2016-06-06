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
public class ComposicaoAcidos extends DomainObject {
    private String numeroAlimento;
    private String descricaoAlimento;
    private String saturados;
    private String monoInsaturados;
    private String poliInsaturados;
    private String doze;
    private String quatorze;
    private String dezesseis;
    private String dezoito;
    private String vinte;
    private String vinteDois;
    private String vinteQuatro;
    private String quatorzeUm;
    private String dezesseisUm;
    private String dezoitoUm;
    private String vinteUm;
    private String dezoitoDois;
    private String dezoitoTres;
    private String vinteQuatroSegundo;
    private String vinteCinco;
    private String vinteDoisCinco;
    private String vinteDoisSeis;
    private String dezoitoUmT;
    private String dezoitoDoisT;
    
    private String grupo; 

    public String getDezoitoDoisTLabel() {
        return "18:2t (g)";
    }
    
    public String getDezoitoUmTLabel() {
        return "18:1t (g)";
    }
    
    public String getVinteDoisSeisLabel() {
        return "22:6 (g)";
    }
    
    public String getVinteDoisCincoLabel() {
        return "22:5 (g)";
    }
    
    public String getVinteCincoLabel() {
        return "20:5 (g)";
    }
    
    public String getVinteQuatroSegundoLabel() {
        return "20:4 (g)";
    }
    
    public String getDezoitoTresLabel() {
        return "18:3 n-3 (g)";
    }
    
    public String getDezoitoDoisLabel() {
        return "18:2 n-6 (g)";
    }
    
    public String getVinteUmLabel() {
        return "20:1 (g)";
    }
    
    public String getDezoitoUmLabel() {
        return "18:1 (g)";
    }
    
    public String getDezesseisUmLabel() {
        return "16:1 (g)";
    }
    
    public String getQuatorzeUmLabel() {
        return "14:1 (g)";
    }
    
    public String getVinteQuatroLabel() {
        return "24:0 (g)";
    }
    
    public String getVinteDoisLabel() {
        return "22:0 (g)";
    }
    
    public String getVinteLabel() {
        return "20:0 (g)";
    }
    
    public String getDezoitoLabel() {
        return "18:0 (g)";
    }
    
    public String getDezesseisLabel() {
        return "16:0 (g)";
    }
    
    public String getQuatorzeLabel() {
        return "14:0 (g)";
    }
    
    public String getDozeLabel() {
        return "12:0 (g)";
    }
    
    public String getPoliInsaturadosLabel() {
        return "Poli-insaturados (g)";
    }
    
    public String getMonoInsaturadosLabel() {
        return "Monoinsaturados (g)";
    }
    
    public String getSaturadosLabel() {
        return "Saturados (g)";
    }
    
    public String getDescricaoAlimentoLabel() {
        return "Descrição alimento";
    }
    
    public String getNumeroAlimentoLabel() {
        return "Número do alimento";
    }

    public String getSaturados() {
        return saturados;
    }

    public void setSaturados(String saturados) {
        this.saturados = saturados;
    }

    public String getMonoInsaturados() {
        return monoInsaturados;
    }

    public void setMonoInsaturados(String monoInsaturados) {
        this.monoInsaturados = monoInsaturados;
    }

    public String getPoliInsaturados() {
        return poliInsaturados;
    }

    public void setPoliInsaturados(String poliInsaturados) {
        this.poliInsaturados = poliInsaturados;
    }

    public String getDoze() {
        return doze;
    }

    public void setDoze(String doze) {
        this.doze = doze;
    }

    public String getQuatorze() {
        return quatorze;
    }

    public void setQuatorze(String quatorze) {
        this.quatorze = quatorze;
    }

    public String getDezesseis() {
        return dezesseis;
    }

    public void setDezesseis(String dezesseis) {
        this.dezesseis = dezesseis;
    }

    public String getDezoito() {
        return dezoito;
    }

    public void setDezoito(String dezoito) {
        this.dezoito = dezoito;
    }

    public String getVinte() {
        return vinte;
    }

    public void setVinte(String vinte) {
        this.vinte = vinte;
    }

    public String getVinteDois() {
        return vinteDois;
    }

    public void setVinteDois(String vinteDois) {
        this.vinteDois = vinteDois;
    }

    public String getVinteQuatro() {
        return vinteQuatro;
    }

    public void setVinteQuatro(String vinteQuatro) {
        this.vinteQuatro = vinteQuatro;
    }

    public String getQuatorzeUm() {
        return quatorzeUm;
    }

    public void setQuatorzeUm(String quatorzeUm) {
        this.quatorzeUm = quatorzeUm;
    }

    public String getDezesseisUm() {
        return dezesseisUm;
    }

    public void setDezesseisUm(String dezesseisUm) {
        this.dezesseisUm = dezesseisUm;
    }

    public String getDezoitoUm() {
        return dezoitoUm;
    }

    public void setDezoitoUm(String dezoitoUm) {
        this.dezoitoUm = dezoitoUm;
    }

    public String getVinteUm() {
        return vinteUm;
    }

    public void setVinteUm(String vinteUm) {
        this.vinteUm = vinteUm;
    }

    public String getDezoitoDois() {
        return dezoitoDois;
    }

    public void setDezoitoDois(String dezoitoDois) {
        this.dezoitoDois = dezoitoDois;
    }

    public String getDezoitoTres() {
        return dezoitoTres;
    }

    public void setDezoitoTres(String dezoitoTres) {
        this.dezoitoTres = dezoitoTres;
    }

    public String getVinteQuatroSegundo() {
        return vinteQuatroSegundo;
    }

    public void setVinteQuatroSegundo(String vinteQuatroSegundo) {
        this.vinteQuatroSegundo = vinteQuatroSegundo;
    }

    public String getVinteCinco() {
        return vinteCinco;
    }

    public void setVinteCinco(String vinteCinco) {
        this.vinteCinco = vinteCinco;
    }

    public String getVinteDoisCinco() {
        return vinteDoisCinco;
    }

    public void setVinteDoisCinco(String vinteDoisCinco) {
        this.vinteDoisCinco = vinteDoisCinco;
    }

    public String getVinteDoisSeis() {
        return vinteDoisSeis;
    }

    public void setVinteDoisSeis(String vinteDoisSeis) {
        this.vinteDoisSeis = vinteDoisSeis;
    }

    public String getDezoitoUmT() {
        return dezoitoUmT;
    }

    public void setDezoitoUmT(String dezoitoUmT) {
        this.dezoitoUmT = dezoitoUmT;
    }

    public String getDezoitoDoisT() {
        return dezoitoDoisT;
    }

    public void setDezoitoDoisT(String dezoitoDoisT) {
        this.dezoitoDoisT = dezoitoDoisT;
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
        Class<ComposicaoAcidos> classe = ComposicaoAcidos.class;
        String valor = "";
        for ( Field atributo: classe.getDeclaredFields() ) {
            if ( nome.equalsIgnoreCase( atributo.getName() )) {
                try {
                    Method method = ComposicaoAcidos.class.getMethod( montarNomeMetodo( atributo.getName() ) );
                    valor = (String) method.invoke( new ComposicaoAcidos() );
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
        Class<ComposicaoAcidos> classe = ComposicaoAcidos.class;
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
