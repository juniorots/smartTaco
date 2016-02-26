/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.smarttaco.negocio;

import br.com.smarttaco.util.EnviarEmail;
import br.com.smarttaco.util.Util;
import java.util.ArrayList;
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
public class UtilTest {
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
    public void model() {
        
    }
    
//    @Test
    public void cifrarRecuperacao() {
        assertNotNull( Util.cifrarRecuperacao("SenhaTeste") );
    }
    
//    @Test
    public void enviarEmail() {
        ArrayList<String> emails = new ArrayList<>();
        emails.add("juniormsd@gmail.com");
        EnviarEmail.recuperarSenha(emails, "SENHA_CRIPTOGRAFADA_AQUI!");
    }
}
