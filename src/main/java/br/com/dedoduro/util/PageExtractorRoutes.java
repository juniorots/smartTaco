/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.dedoduro.util;

import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;

/**
 *
 * @author junior
 */
public class PageExtractorRoutes extends RouteBuilder {
    
    private String xPath;
    
    private String filtro;
    
    @Override
    public void configure() throws Exception {
        from("direct:page_extractor")
                .setHeader(Exchange.HTTP_URI, body())
                .log("Coletando informacoes de: '${body}'")
                .filter().xpath( getxPath() )
                .to("http:extractor")
//                .split(xpath( getxPath() ), new EstruturarPagina( getFiltro() ) )
                .end();
    }

    public String getxPath() {
        return xPath;
    }

    public void setxPath(String xPath) {
        this.xPath = xPath;
    }

    public String getFiltro() {
        return filtro;
    }

    public void setFiltro(String filtro) {
        this.filtro = filtro;
    }
}
