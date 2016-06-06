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
    private String umidade;
    private String energia;
    private String proteina;
    private String lipideos;
    private String colesterol;
    private String carboidrato;
    private String fibraAlimentar;
    private String cinzas;
    private String calcio;
    private String magnesio;
    private String manganes;
    private String fosforo;
    private String ferro;
    private String sodio;
    private String potassio;
    private String cobre;
    private String zinco;
    private String retinol;
    private String re;
    private String rae;
    private String tiamina;
    private String riboflavina;
    private String piridoxina;
    private String niacina;
    private String vitaminaC;
    private String grupo; 

    public String getVitaminaCLabel() {
        return "Vitamina C (mg)";
    }
    
    public String getNiacinaLabel() {
        return "Niacina (mg)";
    }
    
    public String getPiridoxinaLabel() {
        return "Piridoxina (mg)";
    }
    
    public String getRiboflavinaLabel() {
        return "Riboflavina (mg)";
    }
    
    public String getTiaminaLabel() {
        return "Tiamina (mg)";
    }
    
    public String getRaeLabel() {
        return "RAE (μg)";
    }
    
    public String getReLabel() {
        return "RE (μg)";
    }
    
    public String getRetinolLabel() {
        return "Retinol (μg)";
    }
    
    public String getZincoLabel() {
        return "Zinco (mg)";
    }
    
    public String getCobreLabel() {
        return "Cobre (mg)";
    }
    
    public String getPotassioLabel() {
        return "Potássio (mg)";
    }
    
    public String getSodioLabel() {
        return "Sódio (mg)";
    }
    
    public String getFerroLabel() {
        return "Ferro (mg)";
    }
    
    public String getFosforoLabel() {
        return "Fósforo (mg)";
    }
    
    public String getManganesLabel() {
        return "Manganês (mg)";
    }
    
    public String getMagnesioLabel() {
        return "Magnésio (mg)";
    }
    
    public String getCalcioLabel() {
        return "Cálcio (mg)";
    }
    
    public String getCinzasLabel() {
        return "Cinzas (g)";
    }
    
    public String getFibraAlimentarLabel() {
        return "Fibra Alimentar (g)";
    }
    
    public String getCarboidratoLabel() {
        return "Carboidrato (g)";
    }
    
    public String getColesterolLabel() {
        return "Colesterol (mg)";
    }
    
    public String getLipideosLabel() {
        return "Lipídeos (g)";
    }
    
    public String getProteinaLabel() {
        return "Proteína (g)";
    }
    
    public String getEnergiaLabel() {
        return "Energia";
    }
    
    public String getUmidadeLabel() {
        return "Umidade (%)";
    }
    
    public String getNumeroAlimentoLabel() {
        return "Número do Alimento";
    }

    public String getDescricaoAlimentoLabel() {
        return "Descrição alimento";
    }

    public String getUmidade() {
        return umidade;
    }

    public void setUmidade(String umidade) {
        this.umidade = umidade;
    }

    public String getEnergia() {
        return energia;
    }

    public void setEnergia(String energia) {
        this.energia = energia;
    }

    public String getProteina() {
        return proteina;
    }

    public void setProteina(String proteina) {
        this.proteina = proteina;
    }

    public String getLipideos() {
        return lipideos;
    }

    public void setLipideos(String lipideos) {
        this.lipideos = lipideos;
    }

    public String getColesterol() {
        return colesterol;
    }

    public void setColesterol(String colesterol) {
        this.colesterol = colesterol;
    }

    public String getCarboidrato() {
        return carboidrato;
    }

    public void setCarboidrato(String carboidrato) {
        this.carboidrato = carboidrato;
    }

    public String getFibraAlimentar() {
        return fibraAlimentar;
    }

    public void setFibraAlimentar(String fibraAlimentar) {
        this.fibraAlimentar = fibraAlimentar;
    }

    public String getCinzas() {
        return cinzas;
    }

    public void setCinzas(String cinzas) {
        this.cinzas = cinzas;
    }

    public String getCalcio() {
        return calcio;
    }

    public void setCalcio(String calcio) {
        this.calcio = calcio;
    }

    public String getMagnesio() {
        return magnesio;
    }

    public void setMagnesio(String magnesio) {
        this.magnesio = magnesio;
    }

    public String getManganes() {
        return manganes;
    }

    public void setManganes(String manganes) {
        this.manganes = manganes;
    }

    public String getFosforo() {
        return fosforo;
    }

    public void setFosforo(String fosforo) {
        this.fosforo = fosforo;
    }

    public String getFerro() {
        return ferro;
    }

    public void setFerro(String ferro) {
        this.ferro = ferro;
    }

    public String getSodio() {
        return sodio;
    }

    public void setSodio(String sodio) {
        this.sodio = sodio;
    }

    public String getPotassio() {
        return potassio;
    }

    public void setPotassio(String potassio) {
        this.potassio = potassio;
    }

    public String getCobre() {
        return cobre;
    }

    public void setCobre(String cobre) {
        this.cobre = cobre;
    }

    public String getZinco() {
        return zinco;
    }

    public void setZinco(String zinco) {
        this.zinco = zinco;
    }

    public String getRetinol() {
        return retinol;
    }

    public void setRetinol(String retinol) {
        this.retinol = retinol;
    }

    public String getRe() {
        return re;
    }

    public void setRe(String re) {
        this.re = re;
    }

    public String getRae() {
        return rae;
    }

    public void setRae(String rae) {
        this.rae = rae;
    }

    public String getTiamina() {
        return tiamina;
    }

    public void setTiamina(String tiamina) {
        this.tiamina = tiamina;
    }

    public String getRiboflavina() {
        return riboflavina;
    }

    public void setRiboflavina(String riboflavina) {
        this.riboflavina = riboflavina;
    }

    public String getPiridoxina() {
        return piridoxina;
    }

    public void setPiridoxina(String piridoxina) {
        this.piridoxina = piridoxina;
    }

    public String getNiacina() {
        return niacina;
    }

    public void setNiacina(String niacina) {
        this.niacina = niacina;
    }

    public String getVitaminaC() {
        return vitaminaC;
    }

    public void setVitaminaC(String vitaminaC) {
        this.vitaminaC = vitaminaC;
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
