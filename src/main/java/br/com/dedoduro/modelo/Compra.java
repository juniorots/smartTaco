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
public class Compra extends DomainObject {
    
    @ManyToMany(fetch=FetchType.LAZY)
    @JoinColumn(name="id")
    private Collection<Concurso> concursos;
    
    @ManyToMany(fetch=FetchType.LAZY)
    @JoinColumn(name="id")
    private Collection<Usuario> usuarios;
    
    private boolean enviarEmail = false;

    public Collection<Concurso> getConcursos() {
        if (concursos == null) {
            concursos = new ArrayList();
        }
        return concursos;
    }

    public void setConcursos(Collection<Concurso> concursos) {
        this.concursos = concursos;
    }

    public Collection<Usuario> getUsuarios() {
        if (usuarios == null) {
            usuarios = new ArrayList();
        }
        return usuarios;
    }

    public void setUsuarios(Collection<Usuario> usuarios) {
        this.usuarios = usuarios;
    }

    public boolean isEnviarEmail() {
        return enviarEmail;
    }

    public void setEnviarEmail(boolean enviarEmail) {
        this.enviarEmail = enviarEmail;
    }
}
