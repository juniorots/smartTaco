/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.smarttaco.controller;

import java.io.IOException;
import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.mindmap.DefaultMindmapNode;
import org.primefaces.model.mindmap.MindmapNode;

/**
 *
 * @author Jose Alves
 */
@ManagedBean
public class PrincipalMB implements Serializable {
    private MindmapNode raiz;
    private MindmapNode folhaSelecionada;
    
    public PrincipalMB() {
        raiz = new DefaultMindmapNode("TACO", "Tabela Brasileira de Composição de Alimentos", "66CDAA", false);
        MindmapNode acidosGraxos = new DefaultMindmapNode("Ácidos Graxos", "Nomes Sistemáticos e comuns dos ácidos graxos", 
                "87CEFA", true);
        MindmapNode tagnames = new DefaultMindmapNode("Tagnames segundo INFOODS e USDA", "Tagnames segundo INFOODS e USDA", 
                "FAEBD7", true);
        MindmapNode nomesCientificos = new DefaultMindmapNode("Nomes Científicos", "Nomes científicos dos alimentos contemplados na Tabela", 
                "FA8072", true);
        MindmapNode tabelasCentesimais = new DefaultMindmapNode("Tabelas Centesimais", "Composição de alimentos por 100 gramas de parte comestível", 
                "FFB5C5", true);
        
        raiz.addNode( acidosGraxos );
        raiz.addNode( tagnames );
        raiz.addNode( nomesCientificos );
        raiz.addNode( tabelasCentesimais );
    }
    
    public void onNodeSelect(SelectEvent event) {
        MindmapNode node = (MindmapNode) event.getObject();
        
        String label = node.getLabel();
        if ( "Tabelas Centesimais".equalsIgnoreCase( label ) ) {
             node.addNode(new DefaultMindmapNode("Centesimal, minerais, vitaminas e colesterol", 
                     "Centesimal, minerais, vitaminas e colesterol", "FFE4E1", false));
             
             node.addNode(new DefaultMindmapNode("Ácidos graxos", 
                     "Ácidos graxos", "FFE4E1", false));
             
             node.addNode(new DefaultMindmapNode("Aminoácidos", 
                     "Aminoácidos", "FFE4E1", false));
        }
//        try {
//            ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
//            ec.redirect(((HttpServletRequest) ec.getRequest()).getRequestURI());
//        } catch (IOException io) {
//            io.printStackTrace();
//        }
    }
     
    public void onNodeDblselect(SelectEvent event) {
        this.folhaSelecionada = (MindmapNode) event.getObject();        
    }

    public MindmapNode getRaiz() {
        return raiz;
    }

    public void setRaiz(MindmapNode raiz) {
        this.raiz = raiz;
    }

    public MindmapNode getFolhaSelecionada() {
        return folhaSelecionada;
    }

    public void setFolhaSelecionada(MindmapNode folhaSelecionada) {
        this.folhaSelecionada = folhaSelecionada;
    }
    
    
}
