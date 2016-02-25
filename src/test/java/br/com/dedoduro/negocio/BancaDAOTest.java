/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.dedoduro.negocio;

import br.com.dedoduro.base.BancaDAO;
import br.com.dedoduro.modelo.Banca;
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
public class BancaDAOTest {
    
    public BancaDAOTest() {
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
    public void testMain() {
    }
    
//    @Test
    public void cargaInicial() {
        @Cleanup
        final EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("databaseDefault");

        @Cleanup
        final EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        
        Banca banca = null;
        BancaDAO dao = new BancaDAO(entityManager);
        ConcursoDAOTest testeConcurso = new ConcursoDAOTest();
        
        banca = new Banca();
        banca.setNomeBanca("CESPE");
        banca.setConcursos( testeConcurso.inserirConcursos( banca.getNomeBanca().toLowerCase() ) );
        dao.insert(banca);
        
        banca = new Banca();
        banca.setNomeBanca("CESGRANRIO");
        banca.setConcursos( testeConcurso.inserirConcursos( banca.getNomeBanca().toLowerCase() ) );
        dao.insert(banca);
        
        banca = new Banca();
        banca.setNomeBanca("FCC");
        banca.setConcursos( testeConcurso.inserirConcursos( banca.getNomeBanca().toLowerCase() ) );
        dao.insert(banca);
        
        banca = new Banca();
        banca.setNomeBanca("QUADRIX");
        banca.setConcursos( testeConcurso.inserirConcursos( banca.getNomeBanca().toLowerCase() ) );
        dao.insert(banca);
        
        banca = new Banca();
        banca.setNomeBanca("MARINHA");
        banca.setConcursos( testeConcurso.inserirConcursos( banca.getNomeBanca().toLowerCase() ) );
        dao.insert(banca);
        
        banca = new Banca();
        banca.setNomeBanca("EXERCITO");
        banca.setConcursos( testeConcurso.inserirConcursos( banca.getNomeBanca().toLowerCase() ) );
        dao.insert(banca);
        
        banca = new Banca();
        banca.setNomeBanca("AERONAUTICA");
        banca.setConcursos( testeConcurso.inserirConcursos( banca.getNomeBanca().toLowerCase() ) );
        dao.insert(banca);
        
        entityManager.getTransaction().commit();
    }
}
