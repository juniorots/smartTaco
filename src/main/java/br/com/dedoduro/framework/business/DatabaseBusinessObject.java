package br.com.dedoduro.framework.business;

import br.com.dedoduro.framework.persistence.DataAccessObject;
import br.com.dedoduro.framework.persistence.DomainObject;

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
