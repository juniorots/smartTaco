package br.com.dedoduro.framework.business;

import br.com.dedoduro.framework.persistence.DomainObject;
import java.util.List;
import java.util.UUID;

/**
 *
 * @author Rafael Quintino
 * @param <DO>
 */
public interface ICrudBusinessObject<DO extends DomainObject> {

    DO create(DO domainObject);

    DO retrieve(UUID id);

    List<DO> retrieveAll();

    List<DO> retrieveAll(int first, int max);

    List<DO> retrieveByExample(DO exampleDO);

    List<DO> retrieveByExample(DO exampleDO, int first, int max);

    DO update(DO domainObject);

    void delete(DO domainObject);
}
