/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.smarttaco.threads;

import br.com.smarttaco.util.Constantes;

/**
 *
 * @author Jose Alves
 */
public class ThreadCesgranrio implements Runnable {
    
    // utilizado em controle wait / notify
    private boolean livre = true;
    
    public void run() {
        
        synchronized(this) {
            // Varrendo todos os concursos validos
            for (;;) {
                
                while (livre == false) {
                    try {
                        wait();
                    } catch (InterruptedException ie) {
                        ie.printStackTrace();
                    }
                }
                
                // Tomando posse da execucao
                livre = false;
                try {
                    
                    /*
                     * TO-DO Processamento das informacoes contidas nas paginas
                     */
                    
                        
                    livre = true;
                    notifyAll();
                
                    // Aguardando 30 minutos ate a proxima leitura
                    Thread.sleep(30 * Constantes.UM_MINUTO);
                } catch (InterruptedException ie) {
                    ie.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } // for
        }
    }
}
