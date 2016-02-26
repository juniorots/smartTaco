package br.com.smarttaco.framework.business;

import br.com.smarttaco.framework.persistence.DataAccessObject;
import br.com.smarttaco.framework.persistence.DomainObject;

/**
 *
 * @author Rafael Quintino
 * @param <DO>
 * @param <DAO>
 */
public class DatabaseBusinessObject<DO extends DomainObject, DAO extends DataAccessObject<DO>> {

    protected DAO dao;

    public DatabaseBusinessObject(DAO dao) {
        this.dao = dao;
    }

}
