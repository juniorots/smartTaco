/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.smarttaco.controller;

import br.com.smarttaco.base.LaboratorioDAO;
import br.com.smarttaco.modelo.Laboratorio;
import br.com.smarttaco.modelo.ColunaDinamica;
import br.com.smarttaco.util.Constantes;
import br.com.smarttaco.util.Util;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import lombok.Cleanup;
import org.icefaces.ace.model.table.RowStateMap;

/**
 *
 * @author Jose Alves
 */
@ManagedBean
//@SessionScoped
@RequestScoped
public class LaboratorioMB implements Serializable {
    
    private List<Laboratorio> listaItens = new ArrayList<Laboratorio>();
    private List<Integer> ordenando = new ArrayList<Integer>();
    private List<ColunaDinamica> listaColuna;
    
    private List<SelectItem> checkBoxes = new ArrayList<SelectItem>();
    private List<String> selectedCheckBoxes = new ArrayList<String>();
    private Class<Laboratorio> classe = Laboratorio.class;
    
    private RowStateMap stateMap = new RowStateMap();
    private Laboratorio obj = new Laboratorio();
    private CabecalhoMB cabecalho = CabecalhoMB.getInstance();
    
    private int totalColunas;
    
    public LaboratorioMB() {
        this.listaColuna = new ArrayList<ColunaDinamica>();
        
        if ( Util.isEmpty( Util.captarUsuarioSessao() ) ) 
            Util.forward( Constantes.INICIO_SISTEMA );
        
        /*
         * Definindo a estrutura da tabela 
         */
        int i = 0;
        try {
            for ( Field atributo: classe.getDeclaredFields() ) {
                if (!Constantes.GRUPO.equalsIgnoreCase( atributo.getName() ) ) {
                    String tmp = obj.getLabelCorrente( atributo.getName() );
                    if ( tmp.contains( "<" ) ) {
                        tmp = tmp.substring(0, tmp.indexOf("<") ).trim();
                    }
                    this.checkBoxes.add (new SelectItem(atributo.getName(), tmp ) );

                    if ( i < Constantes.LIMITE_COLUNAS) {
                        this.listaColuna.add (new ColunaDinamica(atributo.getName(), 
                                obj.getLabelCorrente( atributo.getName() ) ) );
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
         * Trabalhando no conteudo...
         */
        @Cleanup
        final EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("databaseDefault");
        
        @Cleanup
        final EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        
        LaboratorioDAO dao = new LaboratorioDAO(entityManager);
        this.listaItens.addAll( dao.selectAll() );
        
        /* ESTE BLOCO DE CODIGO DEVERA SER REMOVIDOS POS OS TESTES */
        Laboratorio l = new Laboratorio();
        l.setNoLaboratorio(Util.montarLink("Nome de laboratorio para efeito de teste...", "2"));
        l.setEstado(Util.montarLink("", "5"));
        this.listaItens.add(l);
        /* ESTE BLOCO DE CODIGO DEVERA SER REMOVIDOS APOS OS TESTES */
        
        /*
         * Identificando as marcacoes com <nota>N</nota>
         */
        for (Laboratorio tmp : listaItens) {
            for ( Field atributo: classe.getDeclaredFields() ) {
                try {
                    Method method = Laboratorio.class.getMethod( tmp.montarNomeSimplesMetodo( atributo.getName() ) );
                    cabecalho.indexarResultado( (String) method.invoke( tmp ) );
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
//            cabecalho.indexarResultado( tmp.getNoLaboratorio() );
//            cabecalho.indexarResultado( tmp.getEstado() );
//            cabecalho.indexarResultado( tmp.getGrupo() );
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

    public CabecalhoMB getCabecalho() {
        return cabecalho;
    }

    public void setCabecalho(CabecalhoMB cabecalho) {
        this.cabecalho = cabecalho;
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

    public List<Laboratorio> getListaItens() {
        return listaItens;
    }

    public void setListaItens(List<Laboratorio> listaItens) {
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
