/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.smarttaco.negocio;

import br.com.smarttaco.leituraPaginas.ProcessarCespe;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author Jose Alves
 */
/**
 *
 * @author Jose Alves
 */
public class TratarPaginaTest {
    
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
    public void tratarPagina() {
        ProcessarCespe p = new ProcessarCespe();
        p.tratarPagina("http://www.cespe.unb.br/concursos/mp_15_enap/");
    }
    
}
