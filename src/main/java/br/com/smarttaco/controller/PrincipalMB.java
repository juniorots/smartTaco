/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.smarttaco.controller;

import java.io.Serializable;
import javax.faces.bean.ManagedBean;
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
        MindmapNode nutriente = new DefaultMindmapNode("Nutrientes", "Tagnames segundo INFOODS e USDA", 
                "FAEBD7", true);
        MindmapNode nomesCientificos = new DefaultMindmapNode("Nomes Científicos", "Nomes científicos dos alimentos contemplados na Tabela", 
                "FA8072", true);
        MindmapNode tabelasCentesimais = new DefaultMindmapNode("Tabelas Centesimais", "Composição de alimentos por 100 gramas de parte comestível", 
                "FFB5C5", true);
        
        raiz.addNode( acidosGraxos );
        raiz.addNode( nutriente );
        raiz.addNode( nomesCientificos );
        raiz.addNode( tabelasCentesimais );
    }
    
    public void onNodeSelect(SelectEvent event) {
        MindmapNode node = (MindmapNode) event.getObject();
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
