/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.smarttaco.controller;

import br.com.smarttaco.modelo.AcidoGraxo;
import br.com.smarttaco.modelo.ColunaDinamica;
import br.com.smarttaco.util.Constantes;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.CustomScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;
import org.icefaces.ace.model.table.RowStateMap;

/**
 *
 * @author Jose Alves
 */
@ManagedBean
@CustomScoped(value = "#{window}")
public class AcidosGraxosMB implements Serializable {
    
    private List<AcidoGraxo> listaItens;
    private List<Integer> ordenando = new ArrayList<Integer>();
    private List<ColunaDinamica> listaColuna;
    
    private List<SelectItem> checkBoxes = new ArrayList<SelectItem>();
    private List<String> selectedCheckBoxes = new ArrayList<String>();
    private Class<AcidoGraxo> classe = AcidoGraxo.class;
    private RowStateMap stateMap = new RowStateMap();
    
    private int totalColunas;
    
    public AcidosGraxosMB() {
        this.listaColuna = new ArrayList<ColunaDinamica>();
        
        /*
         * Definindo a estrutura da tabela 
         */
        int i = 1;
        try {
            for ( Field atributo: classe.getDeclaredFields() ) {
                Method method = classe.getMethod( montarNomeMetodo( atributo.getName() ) );    
                String valor = (String) method.invoke( new AcidoGraxo() );
             
                if (!"grupo".equalsIgnoreCase( atributo.getName() )) {
                    this.checkBoxes.add (new SelectItem(atributo.getName(), valor ) );

                    if ( i <= Constantes.LIMITE_COLUNAS) {
                        this.listaColuna.add (new ColunaDinamica(atributo.getName(), valor ) );
                        this.selectedCheckBoxes.add( atributo.getName() );
                    }
                    i++;
                }
            }
            this.totalColunas = i;
        } catch (NoSuchMethodException me) {
            me.printStackTrace();
        } catch (IllegalAccessException ae) {
            ae.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        /*
         * Trabalhando no conteudo
         */
        this.listaItens = new ArrayList();
        for (int j = 1; j <= 200; j++) {
            AcidoGraxo tmp = new AcidoGraxo();
            tmp.setCampo01("Linha "+j);
            
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
            if (listaColuna.get(i).getValue().equals(name))
                listaColuna.remove(i);
    }
    
    /*
     * Re-adicionando colunas
     */
    public void addColumn(String name) {
        String valor;

        try {
            Method method = AcidoGraxo.class.getMethod( montarNomeMetodo(name) );
            valor = (String) method.invoke( new AcidoGraxo() );
        } catch (Exception e) {
            valor = "error...";
            e.printStackTrace();
        }
        listaColuna.add( new ColunaDinamica( name, valor ) );
    }
    
    /*
     * Descrevendo o nome do metodo...
     */
    private String montarNomeMetodo( String nome ) {
        return "get"+ nome.substring(0, 1).toUpperCase()+
            nome.substring(1, nome.length() )+"Label";
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

    public List<AcidoGraxo> getListaItens() {
        return listaItens;
    }

    public void setListaItens(List<AcidoGraxo> listaItens) {
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
