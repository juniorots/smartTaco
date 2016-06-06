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
public class ComposicaoAminoacidos extends DomainObject {
    private String numeroAlimento;
    private String descricaoAlimento;
    private String triptofano;
    private String treonina;
    private String isoleucina;
    private String leucina;
    private String lisina;
    private String metionina;
    private String cistina;
    private String fenilalanina;
    private String tirosina;
    private String valina;
    private String arginina;
    private String histidina;
    private String alanina;
    private String acidoAspartico;
    private String acidoGlutamico;
    private String glicina;
    private String prolina;
    private String serina;
    
    private String grupo; 

    public String getSerinaLabel() {
        return "Serina (g)";
    }
    
    public String getProlinaLabel() {
        return "Prolina (g)";
    }
    
    public String getGlicinaLabel() {
        return "Glicina (g)";
    }
    
    public String getAcidoGlutamicoLabel() {
        return "Ácido Glutâmico (g)";
    }
    
    public String getAcidoAsparticoLabel() {
        return "Ácido Aspártico (g)";
    }
    
    public String getAlaninaLabel() {
        return "Alanina (g)";
    }
    
    public String getHistidinaLabel() {
        return "Histidina (g)";
    }
    
    public String getArgininaLabel() {
        return "Arginina (g)";
    }
    
    public String getValinaLabel() {
        return "Valina (g)";
    }
    
    public String getTirosinaLabel() {
        return "Tirosina (g)";
    }
    
    public String getFenilalaninaLabel() {
        return "Fenilalanina (g)";
    }
    
    public String getCistinaLabel() {
        return "Cistina (g)";
    }
    
    public String getMetioninaLabel() {
        return "Metionina (g)";
    }
    
    public String getLisinaLabel() {
        return "Lisina (g)";
    }
    
    public String getLeucinaLabel() {
        return "Leucina (g)";
    }
    
    public String getIsoleucinaLabel() {
        return "Isoleucina (g)";
    }
    
    public String getTreoninaLabel() {
        return "Treonina (g)";
    }
    
    public String getTriptofanoLabel() {
        return "Triptofano (g)";
    }
    
    public String getDescricaoAlimentoLabel() {
        return "Descrição alimento";
    }
    
    public String getNumeroAlimentoLabel() {
        return "Número do Alimento";
    }

    public String getTriptofano() {
        return triptofano;
    }

    public void setTriptofano(String triptofano) {
        this.triptofano = triptofano;
    }

    public String getTreonina() {
        return treonina;
    }

    public void setTreonina(String treonina) {
        this.treonina = treonina;
    }

    public String getIsoleucina() {
        return isoleucina;
    }

    public void setIsoleucina(String isoleucina) {
        this.isoleucina = isoleucina;
    }

    public String getLeucina() {
        return leucina;
    }

    public void setLeucina(String leucina) {
        this.leucina = leucina;
    }

    public String getLisina() {
        return lisina;
    }

    public void setLisina(String lisina) {
        this.lisina = lisina;
    }

    public String getMetionina() {
        return metionina;
    }

    public void setMetionina(String metionina) {
        this.metionina = metionina;
    }

    public String getCistina() {
        return cistina;
    }

    public void setCistina(String cistina) {
        this.cistina = cistina;
    }

    public String getFenilalanina() {
        return fenilalanina;
    }

    public void setFenilalanina(String fenilalanina) {
        this.fenilalanina = fenilalanina;
    }

    public String getTirosina() {
        return tirosina;
    }

    public void setTirosina(String tirosina) {
        this.tirosina = tirosina;
    }

    public String getValina() {
        return valina;
    }

    public void setValina(String valina) {
        this.valina = valina;
    }

    public String getArginina() {
        return arginina;
    }

    public void setArginina(String arginina) {
        this.arginina = arginina;
    }

    public String getHistidina() {
        return histidina;
    }

    public void setHistidina(String histidina) {
        this.histidina = histidina;
    }

    public String getAlanina() {
        return alanina;
    }

    public void setAlanina(String alanina) {
        this.alanina = alanina;
    }

    public String getAcidoAspartico() {
        return acidoAspartico;
    }

    public void setAcidoAspartico(String acidoAspartico) {
        this.acidoAspartico = acidoAspartico;
    }

    public String getAcidoGlutamico() {
        return acidoGlutamico;
    }

    public void setAcidoGlutamico(String acidoGlutamico) {
        this.acidoGlutamico = acidoGlutamico;
    }

    public String getGlicina() {
        return glicina;
    }

    public void setGlicina(String glicina) {
        this.glicina = glicina;
    }

    public String getProlina() {
        return prolina;
    }

    public void setProlina(String prolina) {
        this.prolina = prolina;
    }

    public String getSerina() {
        return serina;
    }

    public void setSerina(String serina) {
        this.serina = serina;
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
        Class<ComposicaoAminoacidos> classe = ComposicaoAminoacidos.class;
        String valor = "";
        for ( Field atributo: classe.getDeclaredFields() ) {
            if ( nome.equalsIgnoreCase( atributo.getName() )) {
                try {
                    Method method = ComposicaoAminoacidos.class.getMethod( montarNomeMetodo( atributo.getName() ) );
                    valor = (String) method.invoke( new ComposicaoAminoacidos() );
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
        Class<ComposicaoAminoacidos> classe = ComposicaoAminoacidos.class;
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
