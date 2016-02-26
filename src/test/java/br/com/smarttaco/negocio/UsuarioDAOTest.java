package br.com.smarttaco.negocio;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import br.com.smarttaco.base.UsuarioDAO;
import br.com.smarttaco.modelo.Usuario;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import lombok.Cleanup;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertNotNull;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author Jose Alves
 */
public class UsuarioDAOTest {
    
    public UsuarioDAOTest() {
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
    public void example() {
    }
    
//    @Test
    public void mainTest() {

        @Cleanup
        final EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("databaseDefault");

        @Cleanup
        final EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();

        Usuario usuario = new Usuario();    
        UsuarioDAO dao = new UsuarioDAO(entityManager);
        
        usuario.setEmail("juniorots@gmail.com");
        usuario.setSenha("12345");
//        usuario.setReceberEmail("S");
        
        Usuario usInserido = dao.insert(usuario);
        
        entityManager.getTransaction().commit();

        List<Usuario> usuarios = dao.selectAll();
        assertNotNull(usInserido.getId());
//        assertEquals(1, usuario.size());
    }
    
}
