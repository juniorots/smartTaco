/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.smarttaco.base;

import br.com.smarttaco.framework.persistence.DaoJpa2;
import br.com.smarttaco.modelo.Compra;
import javax.persistence.EntityManager;

/**
 *
 * @author Jose Alves
 */
public class CompraDAO extends DaoJpa2<Compra>{
    
    public CompraDAO(EntityManager entityManager) {
        super(Compra.class, entityManager);
    }
}
