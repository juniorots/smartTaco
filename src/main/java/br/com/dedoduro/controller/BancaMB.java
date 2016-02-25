/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.dedoduro.controller;

import br.com.dedoduro.base.BancaDAO;
import br.com.dedoduro.modelo.Banca;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import lombok.Cleanup;
import org.primefaces.event.TabChangeEvent;
import org.primefaces.event.TabCloseEvent;

/**
 *
 * @author Jose Alves
 */
@ManagedBean
@RequestScoped
public class BancaMB {

    private Banca banca = new Banca();
    private Collection<Banca> listaGeralBanca;
    
    private Collection<Banca> listaCespe;
    private Collection<Banca> listaAeronautica;
    private Collection<Banca> listaCesgranrio;
    private Collection<Banca> listaExercito;
    private Collection<Banca> listaFcc;
    private Collection<Banca> listaMarinha;
    private Collection<Banca> listaQuadrix;
    
    /**
     * Creates a new instance of BancaMB
     */
    public BancaMB() {
    }

    public Collection<Banca> getListaAeronautica() {
        listaAeronautica = carregarLista("AERONAUTICA");
        return listaAeronautica;
    }

    public void setListaAeronautica(Collection<Banca> listaAeronautica) {
        this.listaAeronautica = listaAeronautica;
    }

    public Collection<Banca> getListaCesgranrio() {
        listaCesgranrio = carregarLista("CESGRANRIO");
        return listaCesgranrio;
    }

    public void setListaCesgranrio(Collection<Banca> listaCesgranrio) {
        this.listaCesgranrio = listaCesgranrio;
    }

    public Collection<Banca> getListaExercito() {
        listaExercito = carregarLista("EXERCITO");
        return listaExercito;
    }

    public void setListaExercito(Collection<Banca> listaExercito) {
        this.listaExercito = listaExercito;
    }

    public Collection<Banca> getListaFcc() {
        listaFcc = carregarLista("FCC");
        return listaFcc;
    }

    public void setListaFcc(Collection<Banca> listaFcc) {
        this.listaFcc = listaFcc;
    }

    public Collection<Banca> getListaMarinha() {
        listaMarinha = carregarLista("MARINHA");
        return listaMarinha;
    }

    public void setListaMarinha(Collection<Banca> listaMarinha) {
        this.listaMarinha = listaMarinha;
    }

    public Collection<Banca> getListaQuadrix() {
        listaQuadrix = carregarLista("QUADRIX");
        return listaQuadrix;
    }

    public void setListaQuadrix(Collection<Banca> listaQuadrix) {
        this.listaQuadrix = listaQuadrix;
    }

    
    
    public Collection<Banca> getListaCespe() {
        listaCespe = carregarLista("CESPE");
        return listaCespe;
    }

    public void setListaCespe(Collection<Banca> listaCespe) {
        this.listaCespe = listaCespe;
    }

    public Banca getBanca() {
        return banca;
    }

    public void setBanca(Banca banca) {
        this.banca = banca;
    }

    public Collection<Banca> getListaGeralBanca() {
        listaGeralBanca = listarTodosConcursos();
        return listaGeralBanca;
    }

    public void setListaBanca(Collection<Banca> listaGeralBanca) {
        this.listaGeralBanca = listaGeralBanca;
    }
    
    public void onTabChange(TabChangeEvent event) {
        FacesMessage msg = new FacesMessage("Ativado", event.getTab().getTitle());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
         
    public void onTabClose(TabCloseEvent event) {
        FacesMessage msg = new FacesMessage("Desativado", event.getTab().getTitle());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
    
    /**
     * Trata todos os concursos contidos na base
     */
    public ArrayList<Banca> listarTodosConcursos() {
        @Cleanup
        final EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("databaseDefault");
        
        @Cleanup
        final EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        
        BancaDAO dao = new BancaDAO(entityManager);
        ArrayList<Banca> bancas = (ArrayList<Banca>) dao.selectAll();
        
        return bancas;
    }
    
    /**
     * Carregamento de lista com limite de 7 registros
     * prezando pela estetica da pagina
     * @return 
     */
    public Collection<Banca> carregarLista(String banca) {
        Collection<Banca> retorno = new ArrayList();
        
        @Cleanup
        final EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("databaseDefault");
        
        @Cleanup
        final EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        
        BancaDAO dao = new BancaDAO(entityManager);
        HashMap<String, String> parametros = new HashMap();
        parametros.put("nomeBanca", banca.toUpperCase() );
        
        retorno = (ArrayList<Banca>) dao.findByStringFields(parametros, true, 0, 6);
        return retorno;
    }
}
