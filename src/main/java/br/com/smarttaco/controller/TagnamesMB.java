/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.smarttaco.controller;

import br.com.smarttaco.modelo.Tagnames;
import br.com.smarttaco.modelo.ColunaDinamica;
import br.com.smarttaco.util.Constantes;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;
import org.icefaces.ace.model.table.RowStateMap;

/**
 *
 * @author Jose Alves
 */
@ManagedBean
public class TagnamesMB implements Serializable {
    
    private List<Tagnames> listaItens;
    private List<Integer> ordenando = new ArrayList<Integer>();
    private List<ColunaDinamica> listaColuna;
    
    private List<SelectItem> checkBoxes = new ArrayList<SelectItem>();
    private List<String> selectedCheckBoxes = new ArrayList<String>();
    private Class<Tagnames> classe = Tagnames.class;
    
    private RowStateMap stateMap = new RowStateMap();
    private Tagnames obj = new Tagnames();
    
    private int totalColunas;
    
    public TagnamesMB() {
        this.listaColuna = new ArrayList<ColunaDinamica>();
        
        /*
         * Definindo a estrutura da tabela 
         */
        int i = 0;
        try {
            for ( Field atributo: classe.getDeclaredFields() ) {
                if (!"grupo".equalsIgnoreCase( atributo.getName() )) {
                    this.checkBoxes.add (new SelectItem(atributo.getName(), obj.getLabelCorrente( atributo.getName() ) ) );

                    if ( i < Constantes.LIMITE_COLUNAS) {
                        this.listaColuna.add (new ColunaDinamica(atributo.getName(), obj.getLabelCorrente( atributo.getName() ) ) );
                        this.selectedCheckBoxes.add( atributo.getName() );
                    }
                    i++;
                }
            }
            this.totalColunas = i;
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        /*
         * Trabalhando no conteudo
         */
        this.listaItens = new ArrayList();
        for (int j = 1; j <= 200; j++) {
            Tagnames tmp = new Tagnames();
            tmp.setNutriente("Linha "+j);
            
            if ( j < 30) {
                tmp.setGrupo("Alfa");
            } else {
                tmp.setGrupo("Lambda");
            }
            this.listaItens.add(tmp);
        }
    }

    /*
     * Removendo colunas...
     */
    public void removeColumn(String name) {
        for (int i = 0; i < listaColuna.size(); i++)
            if (listaColuna.get(i).getValue().equalsIgnoreCase( name ))
                listaColuna.remove(i);
    }
    
    /*
     * Re-adicionando colunas
     */
    public void addColumn(String name) {
        listaColuna.add( new ColunaDinamica( obj.getNomeAtributo( name ), obj.getLabelCorrente( name ) ) );
    }
    
    /*
     * Evento do check..
     */
    public void checkboxChange(ValueChangeEvent event) {
        List<String> oldVal = (List<String>) event.getNewValue();
        List<String> newVal = (List<String>) event.getOldValue();

        List<String> added = new ArrayList<String>(oldVal);
        added.removeAll(newVal);
        for (String s : added) {
            addColumn(s.toLowerCase());
        }

        List<String> removed = new ArrayList<String>(newVal);
        removed.removeAll(oldVal);
        for (String s : removed) {
            removeColumn(s.toLowerCase());
        }
    }

    public int getTotalColunas() {
        return totalColunas;
    }

    public void setTotalColunas(int totalColunas) {
        this.totalColunas = totalColunas;
    }

    public RowStateMap getStateMap() {
        return stateMap;
    }

    public void setStateMap(RowStateMap stateMap) {
        this.stateMap = stateMap;
    }

    public List<Tagnames> getListaItens() {
        return listaItens;
    }

    public void setListaItens(List<Tagnames> listaItens) {
        this.listaItens = listaItens;
    }
    
    public List<Integer> getOrdenando() {
        return ordenando;
    }

    public void setOrdenando(List<Integer> ordenando) {
        this.ordenando = ordenando;
    }

    public List<ColunaDinamica> getListaColuna() {
        return listaColuna;
    }

    public void setListaColuna(List<ColunaDinamica> listaColuna) {
        this.listaColuna = listaColuna;
    }

    public List<SelectItem> getCheckBoxes() {
        return checkBoxes;
    }

    public void setCheckBoxes(List<SelectItem> checkBoxes) {
        this.checkBoxes = checkBoxes;
    }

    public List<String> getSelectedCheckBoxes() {
        return selectedCheckBoxes;
    }

    public void setSelectedCheckBoxes(List<String> selectedCheckBoxes) {
        this.selectedCheckBoxes = selectedCheckBoxes;
    }
}
