/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.dedoduro.leituraPaginas;

import br.com.dedoduro.util.Constantes;
import br.com.dedoduro.util.PageExtractorRoutes;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.impl.DefaultCamelContext;

/**
 *
 * @author Jose Alves
 */
public class ProcessarCespe {
    final DefaultCamelContext camelContext = new DefaultCamelContext();
    PageExtractorRoutes pageRoutes = new PageExtractorRoutes();

    public void tratarPagina(String url) {
        try {
            pageRoutes.setxPath("//html");
            pageRoutes.setFiltro("");
            
            camelContext.addRoutes(pageRoutes);
            camelContext.start();
            
            ProducerTemplate template = camelContext.createProducerTemplate();
            String retorno = template.requestBody("direct:page_extractor", url, String.class);

            System.out.println("Retorno: "+retorno);
//            Thread.sleep(30 * Constantes.UM_MINUTO);
            Thread.sleep(Constantes.UM_SEGUNDO);
            camelContext.stop();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
