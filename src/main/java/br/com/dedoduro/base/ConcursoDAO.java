/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.dedoduro.base;

import br.com.dedoduro.framework.persistence.DaoJpa2;
import br.com.dedoduro.modelo.Concurso;
import javax.persistence.EntityManager;

/**
 *
 * @author Jose Alves
 */
public class ConcursoDAO extends DaoJpa2<Concurso>{
    
    public ConcursoDAO(EntityManager entityManager) {
        super(Concurso.class, entityManager);
    }
    
}
