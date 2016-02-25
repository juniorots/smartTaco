/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.dedoduro.base;

import br.com.dedoduro.framework.persistence.DaoJpa2;
import br.com.dedoduro.modelo.Banca;
import javax.persistence.EntityManager;

/**
 *
 * @author Jose Alves
 */
public class BancaDAO extends DaoJpa2<Banca>{
    
    public BancaDAO(EntityManager entityManager) {
        super(Banca.class, entityManager);
    }
}
