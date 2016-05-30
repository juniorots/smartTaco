/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.smarttaco.negocio;

import br.com.smarttaco.util.EnviarEmail;
import br.com.smarttaco.util.Helena;
import br.com.smarttaco.util.Util;
import java.util.ArrayList;
import java.util.List;
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

//    @Test
    public void model() {
    }
    
    @Test
    public void carregarDadosPdf(){
        List<Integer> paginas = new ArrayList<Integer>();
        try {
            for (int i = 19; i<= 164; i++) {
                paginas.add(i);
            }
//            paginas.add(19);
            Helena.executar("/home/junior/Downloads/taco.txt", paginas, "/home/junior/Downloads/taco_4_edicao_ampliada_e_revisada.pdf");
        } catch (Exception e) {
            e.printStackTrace();
        }
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
