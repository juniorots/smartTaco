/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.dedoduro.controller;

import br.com.dedoduro.base.CompraDAO;
import br.com.dedoduro.base.ConcursoDAO;
import br.com.dedoduro.modelo.Compra;
import br.com.dedoduro.modelo.Concurso;
import br.com.dedoduro.modelo.Usuario;
import br.com.dedoduro.util.Util;
import java.util.UUID;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import lombok.Cleanup;
import org.primefaces.context.RequestContext;

/**
 *
 * @author Jose Alves
 */
@ManagedBean
@RequestScoped
public class ConcursoMB {
    
    private static String PAGINA_DESCRICAO = "descricaoConcurso";
    
    Concurso concurso = null;
    
    public ConcursoMB() {
        concurso = Util.captarConcursoSessao();
        if (Util.isEmpty( concurso )) {
            concurso = new Concurso();
        }
    }

    public Concurso getConcurso() {
        return concurso;
    }

    public void setConcurso(Concurso concurso) {
        this.concurso = concurso;
    }
    
    /**
     * Carregara a descricao do concurso
     * selecionado
     */
    public String carregarCaracteristica() {
        String codConcurso = (String) FacesContext.getCurrentInstance().
                             getExternalContext().getRequestParameterMap().get("codConcurso");
        
        @Cleanup
        final EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("databaseDefault");
        
        @Cleanup
        final EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        
        ConcursoDAO dao = new ConcursoDAO(entityManager);
        setConcurso( (Concurso) dao.selectById( UUID.fromString( codConcurso ) ) );

        Util.gravarConcursoSessao( getConcurso() );
        
        return PAGINA_DESCRICAO;
    }
    
    /**
     * Responsavel por validar o interesse do usuario
     * em comprar o pacote de atualizacao existente
     * na sessao, ou seja, o ultimo visualizado
     */
    public void computarCompra() {
        @Cleanup
        final EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("databaseDefault");
        
        @Cleanup
        final EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        
        Usuario usuarioSessao = Util.captarUsuarioSessao();
        Concurso concursoSessao = Util.captarConcursoSessao();
        Compra compra = new Compra();      
        
        compra.getConcursos().add(concursoSessao);
        compra.getUsuarios().add(usuarioSessao);
        
        CompraDAO dao = new CompraDAO(entityManager);
        dao.insert( compra );
        
        entityManager.getTransaction().commit();
        
        /*
         * Procurando finalizar o processo de compra
         */
        carregarPayPal();
    }
    
    /*
     * Realizando chamada externa do paypal visando efetivacao do pagamento
     * da possivel compra realizada
     */
    public void carregarPayPal() {
        String cmd = (String) FacesContext.getCurrentInstance().
                             getExternalContext().getRequestParameterMap().get("cmd");
        String hosted_button_id = (String) FacesContext.getCurrentInstance().
                             getExternalContext().getRequestParameterMap().get("hosted_button_id");
        String currency_code = (String) FacesContext.getCurrentInstance().
                             getExternalContext().getRequestParameterMap().get("currency_code");
        
        RequestContext.getCurrentInstance().execute(
                "window.open('https://www.paypal.com/cgi-bin/webscr?cmd="+cmd+"&"
                        + "hosted_button_id="+hosted_button_id+"&"
                        + "currency_code="+currency_code+"', "
                        + "'_self')");
    }
}
