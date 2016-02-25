package br.com.dedoduro.framework.business;

import br.com.dedoduro.framework.persistence.DataAccessObject;
import br.com.dedoduro.framework.persistence.DomainObject;
import java.util.List;
import java.util.UUID;

/**
 *
 * @author Rafael Quintino
 * @param <DO>
 * @param <DAO>
 */
public class CrudBusinessObject<DO extends DomainObject, DAO extends DataAccessObject<DO>> extends DatabaseBusinessObject<DO, DAO> implements ICrudBusinessObject<DO> {

    public CrudBusinessObject(DAO dao) {
        super(dao);
    }

    @Override
    public DO create(DO domainObject) {
        return dao.insert(domainObject);
    }

    @Override
    public DO retrieve(UUID id) {
        return dao.selectById(id);
    }

    @Override
    public List<DO> retrieveAll() {
        return dao.selectAll();
    }

    @Override
    public List<DO> retrieveAll(int first, int max) {
        return dao.selectAll(first, max);
    }

    @Override
    public List<DO> retrieveByExample(DO exampleDO) {
        return dao.selectUsingFilter(exampleDO);
    }

    @Override
    public List<DO> retrieveByExample(DO exampleDO, int first, int max) {
        return dao.selectUsingFilter(exampleDO, first, max);
    }

    @Override
    public DO update(DO domainObject) {
        return dao.update(domainObject);
    }

    @Override
    public void delete(DO domainObject) {
        dao.delete(domainObject);
    }

}
