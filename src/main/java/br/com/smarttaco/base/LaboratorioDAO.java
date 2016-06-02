/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.smarttaco.base;

import br.com.smarttaco.framework.persistence.DaoJpa2;
import br.com.smarttaco.modelo.Laboratorio;
import javax.persistence.EntityManager;

/**
 *
 * @author Jose Alves
 */
public class LaboratorioDAO extends DaoJpa2<Laboratorio>{

    public LaboratorioDAO(EntityManager entityManager) {
        super(Laboratorio.class, entityManager);
    }
    
}
