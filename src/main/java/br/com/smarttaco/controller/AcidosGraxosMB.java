/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.smarttaco.controller;

import br.com.smarttaco.modelo.AcidoGraxo;
import br.com.smarttaco.modelo.ColunaDinamica;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.CustomScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;

/**
 *
 * @author Jose Alves
 */
@ManagedBean
@CustomScoped(value = "#{window}")
public class AcidosGraxosMB implements Serializable {
    
    private List<AcidoGraxo> listaAcido;
    private List<Integer> ordenando = new ArrayList<Integer>();
    private List<ColunaDinamica> listaColuna;
    private List<SelectItem> checkBoxes = new ArrayList<SelectItem>();
    private List<String> selectedCheckBoxes = new ArrayList<String>();
    
    public AcidosGraxosMB() {
        this.listaColuna = new ArrayList<ColunaDinamica>();
        
        /*
         * Definindo a estrutura da tabela 
         */
        Class<AcidoGraxo> classe = AcidoGraxo.class;
        int i = 1;
        try {
            for ( Field atributo: classe.getDeclaredFields() ) {
                Method method = classe.getMethod( "get"+
                        atributo.getName().substring(0, 1).toUpperCase()+
                        atributo.getName().substring(1, atributo.getName().length())+"Label" );    
                String valor = (String) method.invoke( new AcidoGraxo() );
                this.listaColuna.add (new ColunaDinamica(atributo.getName(), valor ) );
                this.checkBoxes.add (new SelectItem(atributo.getName(), valor ) );
                
                if ( i <= 2) {
                    this.selectedCheckBoxes.add( atributo.getName() );
                }
                i++;
            }
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
        this.listaAcido = new ArrayList();
        AcidoGraxo a1 = new AcidoGraxo();
        a1.setCampo01("Valor do Campo 01");
        AcidoGraxo a2 = new AcidoGraxo();
        a1.setCampo02("Valor do Campo 02");
        AcidoGraxo a3 = new AcidoGraxo();
        a1.setCampo03("Valor do Campo 03");
        this.listaAcido.add( a1 );
        this.listaAcido.add( a2 );
        this.listaAcido.add( a3 );
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
            Method method = AcidoGraxo.class.getMethod( name+"Label" );
            valor = (String) method.invoke( new AcidoGraxo() );
        } catch (Exception e) {
            valor = "Campo com problema...";
            e.printStackTrace();
        }
        listaColuna.add( new ColunaDinamica( name, valor ) );
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
    
    public List<AcidoGraxo> getListaAcido() {
        return listaAcido;
    }

    public void setListaAcido(List<AcidoGraxo> listaAcido) {
        this.listaAcido = listaAcido;
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
