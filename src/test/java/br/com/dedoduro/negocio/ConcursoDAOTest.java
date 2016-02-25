/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.dedoduro.negocio;

import br.com.dedoduro.base.ConcursoDAO;
import br.com.dedoduro.modelo.Concurso;
import br.com.dedoduro.modelo.Usuario;
import java.util.ArrayList;
import java.util.Collection;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import lombok.Cleanup;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author Jose Alves
 */
public class ConcursoDAOTest {
    public ConcursoDAOTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }   

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    @Test
    public void mainTest(){
    }
    
    public Collection<Concurso> inserirConcursos(String banca) {
        Collection<Concurso> retorno = new ArrayList();
        
        @Cleanup
        final EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("databaseDefault");

        @Cleanup
        final EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        
        Concurso concurso = new Concurso();
        ConcursoDAO daoConcurso = new ConcursoDAO(entityManager);
        
        if ( banca.equals("cespe") ) {
            concurso.setNomeConcurso("Ministério do Planejamento, Orçamento e Gestão");
            concurso.setUrlImagem("mpog.jpg");
            concurso.setUrl("http://www.cespe.unb.br/concursos/mp_15_enap/");
            Concurso concursoInserido = daoConcurso.insert(concurso);
            retorno.add(concursoInserido);

            concurso = new Concurso();
            concurso.setNomeConcurso("Ministério da Educação");
            concurso.setUrlImagem("mec.jpg");
            concurso.setUrl("http://www.cespe.unb.br/concursos/mec_14_temporario/");
            concursoInserido = daoConcurso.insert(concurso);
            retorno.add(concursoInserido);
            
            entityManager.getTransaction().commit();
        }
        
        if ( banca.equals("cesgranrio") ) {
            concurso.setNomeConcurso("Banco da Amazônia S.A");
            concurso.setUrlImagem("bcoAmazonia.jpg");
            concurso.setUrl("http://www.cesgranrio.org.br/concursos/evento.aspx?id=basa0115");
            Concurso concursoInserido = daoConcurso.insert(concurso);
            retorno.add(concursoInserido);

            concurso = new Concurso();
            concurso.setNomeConcurso("Liquigás Distribuidora S.A");
            concurso.setUrlImagem("liguigas.jpg");
            concurso.setUrl("http://www.cesgranrio.org.br/concursos/evento.aspx?id=liquigas0115");
            concursoInserido = daoConcurso.insert(concurso);
            retorno.add(concursoInserido);
            
            entityManager.getTransaction().commit();
        }
        
        if ( banca.equals("fcc") ) {
            concurso.setNomeConcurso("TRT 15ª Região - Campinas/SP");
            concurso.setUrlImagem("trt15.jpg");
            concurso.setUrl("http://www.concursosfcc.com.br/concursos/trt15113/");
            Concurso concursoInserido = daoConcurso.insert(concurso);
            retorno.add(concursoInserido);

            Usuario usuario = new Usuario();
            
            
            concurso = new Concurso();
            concurso.setNomeConcurso("METRÔ - São Paulo");
            concurso.setUrlImagem("metro.png");
            concurso.setUrl("http://www.concursosfcc.com.br/concursos/metro115/index.html");
            concursoInserido = daoConcurso.insert(concurso);
            retorno.add(concursoInserido);
            
            entityManager.getTransaction().commit();
        }
        
        if ( banca.equals("quadrix") ) {
            concurso.setNomeConcurso("BB Tecnologia e Serviços");
            concurso.setUrlImagem("bbservicos.jpg");
            concurso.setUrl("http://www.quadrix.org.br/concursopublico2015cobra.aspx");
            Concurso concursoInserido = daoConcurso.insert(concurso);
            retorno.add(concursoInserido);

            concurso = new Concurso();
            concurso.setNomeConcurso("Conselho Regional de Educação Física 7");
            concurso.setUrlImagem("cref.jpg");
            concurso.setUrl("http://www.quadrix.org.br/concursopublico2015cref7.aspx");
            concursoInserido = daoConcurso.insert(concurso);
            retorno.add(concursoInserido);
            
            entityManager.getTransaction().commit();
        }
        
        if ( banca.equals("marinha") ) {
            concurso.setNomeConcurso("CPA-CAP");
            concurso.setUrlImagem("marinha.jpg");
            concurso.setUrl("https://www.ensino.mar.mil.br/marinha/index_concursos.jsp?id_concurso=316");
            Concurso concursoInserido = daoConcurso.insert(concurso);
            retorno.add(concursoInserido);

            concurso = new Concurso();
            concurso.setNomeConcurso("Escola Naval - CPAEN");
            concurso.setUrlImagem("escNaval.jpg");
            concurso.setUrl("https://www.ensino.mar.mil.br/marinha/index_concursos.jsp?id_concurso=308");
            concursoInserido = daoConcurso.insert(concurso);
            retorno.add(concursoInserido);
            
            entityManager.getTransaction().commit();
        }
        
        if ( banca.equals("exercito") ) {
            concurso.setNomeConcurso("EsPCEx");
            concurso.setUrlImagem("espcex.jpg");
            concurso.setUrl("http://www.espcex.ensino.eb.br/index.php?option=com_content&view=article&id=876&Itemid=60&lang=pt");
            Concurso concursoInserido = daoConcurso.insert(concurso);
            retorno.add(concursoInserido);

            concurso = new Concurso();
            concurso.setNomeConcurso("ESFCEX");
            concurso.setUrlImagem("esfcex.jpg");
            concurso.setUrl("http://www.esfcex.ensino.eb.br/index.php?option=com_content&view=article&id=954&Itemid=273");
            concursoInserido = daoConcurso.insert(concurso);
            retorno.add(concursoInserido);
            
            entityManager.getTransaction().commit();
        }
        
        if ( banca.equals("aeronautica") ) {
            concurso.setNomeConcurso("EEAR");
            concurso.setUrlImagem("eear.png");
            concurso.setUrl("http://concursos.eear.aer.mil.br/");
            Concurso concursoInserido = daoConcurso.insert(concurso);
            retorno.add(concursoInserido);

            concurso = new Concurso();
            concurso.setNomeConcurso("EPCAR");
            concurso.setUrlImagem("epcar.jpg");
            concurso.setUrl("http://concursos.epcar.aer.mil.br/");
            concursoInserido = daoConcurso.insert(concurso);
            retorno.add(concursoInserido);
            
            entityManager.getTransaction().commit();
        }
        
        return retorno;
    }
}
