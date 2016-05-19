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
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import org.primefaces.context.RequestContext;
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
                "FFB5C5", true);
        
        raiz.addNode( acidosGraxos );
        raiz.addNode( tagnames );
        raiz.addNode( nomesCientificos );
        raiz.addNode( tabelasCentesimais );
    }
    
    public String onNodeSelect(SelectEvent event) {
        MindmapNode node = (MindmapNode) event.getObject();
        
        String label = node.getLabel();
        if ( Constantes.ELIPSE_CENTESIMAIS.equalsIgnoreCase( label ) ) {
             node.addNode(new DefaultMindmapNode(Constantes.ELIPSE_ELEMENTOS, 
                     "Centesimal, minerais, vitaminas e colesterol", "FFE4E1", false));
             
             node.addNode(new DefaultMindmapNode(Constantes.ELIPSE_ACIDOS, 
                     "Ácidos graxos", "FFE4E1", false));
             
             node.addNode(new DefaultMindmapNode(Constantes.ELIPSE_AMINOACIDOS, 
                     "Aminoácidos", "FFE4E1", false));
        }
        
        if ( !Util.isEmpty( Util.captarUsuarioSessao() ) ) {
            if ( Constantes.ELIPSE_ACIDOS.equalsIgnoreCase( label ) ) {
                forward( Constantes.ACIDOS_GRAXOS );
            }
        } else {
            FacesMessage mensagem = new FacesMessage(FacesMessage.SEVERITY_ERROR, "É porque não dá mesmo!", 
                    "Ops... Identifiquei que você não entrou no sistema!!<br /> "
                            + "Preciso que você faça isso, para liberar a consulta!!");
            RequestContext.getCurrentInstance().showMessageInDialog(mensagem);
        }
        // default return...
        return Constantes.INICIO_SISTEMA;
    }

    /*
    * redirecionando...
    */
    public void forward( String caminho ) {
        try {
            ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
            ec.redirect( ((HttpServletRequest) ec.getRequest()).getContextPath() + caminho );            
//            ec.redirect( ((HttpServletRequest) ec.getRequest()).getRequestURI() );            
        } catch ( Exception e) {
            e.printStackTrace();
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
