/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.dedoduro.modelo;

import br.com.dedoduro.framework.persistence.DomainObject;
import java.util.ArrayList;
import java.util.Collection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;

/**
 *
 * @author Jose Alves
 */
@Entity
public class Banca extends DomainObject {
    
    private String nomeBanca;
    private String urlImagem;
    
    @ManyToMany(fetch=FetchType.EAGER)
    @JoinColumn(name = "id")
    private Collection<Concurso> concursos;

    public Collection<Concurso> getConcursos() {
        if (concursos == null) {
            concursos = new ArrayList();
        }
        return concursos;
    }
    
    public void setConcursos(Collection<Concurso> concursos) {
        this.concursos = concursos;
    }

    public String getNomeBanca() {
        return nomeBanca;
    }

    public void setNomeBanca(String nomeBanca) {
        this.nomeBanca = nomeBanca;
    }

    public String getUrlImagem() {
        return urlImagem;
    }

    public void setUrlImagem(String urlImagem) {
        this.urlImagem = urlImagem;
    }
    
}
