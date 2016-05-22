/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.smarttaco.controller;

import br.com.smarttaco.util.Constantes;
import br.com.smarttaco.util.Util;
import java.io.Serializable;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.mindmap.DefaultMindmapNode;
import org.primefaces.model.mindmap.MindmapNode;

/**
 *
 * @author Jose Alves
 */
@ManagedBean
@SessionScoped
public class PrincipalMB implements Serializable {
    private MindmapNode raiz;
    private MindmapNode folhaSelecionada;
    UsuarioMB usuario = new UsuarioMB();
    
    public PrincipalMB() {
        raiz = new DefaultMindmapNode(Constantes.ELIPSE_TACO, "Tabela Brasileira de Composição de Alimentos", "66CDAA", false);
        MindmapNode acidosGraxos = new DefaultMindmapNode(Constantes.ELIPSE_ACIDOS, "Nomes Sistemáticos e comuns dos ácidos graxos", 
                "87CEFA", true);
        MindmapNode tagnames = new DefaultMindmapNode(Constantes.ELIPSE_TAGNAMES, "Tagnames segundo INFOODS e USDA", 
                "FAEBD7", true);
        MindmapNode nomesCientificos = new DefaultMindmapNode(Constantes.ELIPSE_CIENTIFICOS, "Nomes científicos dos alimentos contemplados na Tabela", 
                "FA8072", true);
        MindmapNode tabelasCentesimais = new DefaultMindmapNode(Constantes.ELIPSE_CENTESIMAIS, "Composição de alimentos por 100 gramas de parte comestível", 
                "FFA500", true);
        
        raiz.addNode( acidosGraxos );
        raiz.addNode( tagnames );
        raiz.addNode( nomesCientificos );
        raiz.addNode( tabelasCentesimais );
    }
    
    public void onNodeSelect(SelectEvent event) {
        MindmapNode node = (MindmapNode) event.getObject();
        
        String label = node.getLabel();
        if ( Constantes.ELIPSE_CENTESIMAIS.equalsIgnoreCase( label ) ) {
             node.getChildren().clear();
             node.addNode(new DefaultMindmapNode(Constantes.ELIPSE_ELEMENTOS, 
                     "Centesimal, minerais, vitaminas e colesterol", "FFDEAD", true));
             
             node.addNode(new DefaultMindmapNode(Constantes.ELIPSE_CENTESIMAL_ACIDOS, 
                     "Centesimal Ácidos graxos", "FFDEAD", true));
             
             node.addNode(new DefaultMindmapNode(Constantes.ELIPSE_AMINOACIDOS, 
                     "Centesimal Aminoácidos", "FFDEAD", true));
        }
        
//        if ( !isUsuarioLogado() ) return;
        
        // select your destiny! MK
        if ( Constantes.ELIPSE_ACIDOS.equalsIgnoreCase( label ) ) {
            Util.forward( Constantes.ACIDOS_GRAXOS );
        }
        
        if ( Constantes.ELIPSE_TAGNAMES.equalsIgnoreCase( label ) ) {
            Util.forward( Constantes.TAGNAMES );
        } 
        
        if ( Constantes.ELIPSE_CIENTIFICOS.equalsIgnoreCase( label ) ) {
            Util.forward( Constantes.NOMES_CIENTIFICOS );
        } 
        
        if ( Constantes.ELIPSE_ELEMENTOS.equalsIgnoreCase( label ) ) {
            Util.forward( Constantes.COMPOSICAO_ELEMENTOS );
        } 
        
        if ( Constantes.ELIPSE_CENTESIMAL_ACIDOS.equalsIgnoreCase( label ) ) {
            Util.forward( Constantes.COMPOSICAO_ACIDOS );
        } 
        
        if ( Constantes.ELIPSE_AMINOACIDOS.equalsIgnoreCase( label ) ) {
            Util.forward( Constantes.COMPOSICAO_AMINOACIDOS );
        }
    }

    /*
    * Verificando se o usuario esta logado no sistema.
    */
    public boolean isUsuarioLogado() {
        if ( Util.isEmpty( Util.captarUsuarioSessao() ) ) {
            FacesMessage mensagem = new FacesMessage(FacesMessage.SEVERITY_ERROR, "É porque não dá mesmo!", 
                    "Ops... Identifiquei que você não entrou no sistema!!<br /> "
                            + "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"
                            + "Preciso que você faça isso, para liberar a consulta!!");
            RequestContext.getCurrentInstance().showMessageInDialog(mensagem);
            return false;
        } else {
            return true;
        }
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
