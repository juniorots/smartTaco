/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.dedoduro.util;

import java.util.ArrayList;
import java.util.List;
import org.apache.camel.Exchange;
import org.apache.camel.processor.aggregate.AggregationStrategy;

/**
 *
 * @author Jose Alves
 */
public class EstruturarPagina implements AggregationStrategy {
    private final String filtro;
    
    public EstruturarPagina(String filtro) {
        this.filtro = filtro;
    }
    
    public Exchange aggregate(Exchange oldExchange, Exchange newExchange) {
        List<String> result = null == oldExchange ? result = new ArrayList<String>() : oldExchange.getIn().getBody(List.class);
 
        String node = newExchange.getIn().getBody(String.class);
//        String content = node.getNodeValue().trim();
        if (node.matches(this.filtro)) {
            result.add(node.trim());
        }
 
        newExchange.getIn().setBody(result);
        return newExchange;
    }
            
}
