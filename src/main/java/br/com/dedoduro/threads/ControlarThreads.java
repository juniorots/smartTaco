/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.dedoduro.threads;

/**
 *
 * @author Jose Alves
 */
public class ControlarThreads {
    
    public static void processar() {
        ThreadCespe tc = new ThreadCespe();
        Thread t1 = new Thread(tc);
        t1.start ();
        
        ThreadQuadrix tq = new ThreadQuadrix();
        Thread t2 = new Thread(tq);
        t2.start ();
        
        ThreadCesgranrio tg = new ThreadCesgranrio();
        Thread t3 = new Thread(tg);
        t3.start ();
        
        ThreadFcc tf = new ThreadFcc();
        Thread t4 = new Thread(tf);
        t4.start ();
        
        ThreadExercito te = new ThreadExercito();
        Thread t5 = new Thread(te);
        t5.start ();
        
        ThreadMarinha tm = new ThreadMarinha();
        Thread t6 = new Thread(tm);
        t6.start ();
        
        ThreadAeronautica ta = new ThreadAeronautica();
        Thread t7 = new Thread(ta);
        t7.start ();
    }
}
