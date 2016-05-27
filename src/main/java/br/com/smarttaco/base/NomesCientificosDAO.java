/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.smarttaco.base;

import br.com.smarttaco.framework.persistence.DaoJpa2;
import br.com.smarttaco.modelo.NomesCientificos;
import javax.persistence.EntityManager;

/**
 *
 * @author Jose Alves
 */
public class NomesCientificosDAO extends DaoJpa2<NomesCientificos>{

    public NomesCientificosDAO(EntityManager entityManager) {
        super(NomesCientificos.class, entityManager);
    }
    
}
