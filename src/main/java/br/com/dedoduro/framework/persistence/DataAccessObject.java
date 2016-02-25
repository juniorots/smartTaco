package br.com.dedoduro.framework.persistence;

import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 *
 * @author Rafael Quintino
 * @param <DO>
 */
public interface DataAccessObject<DO extends DomainObject> {

    List<DO> selectAll();

    List<DO> selectAll(int first, int max);

    DO selectById(UUID id);

    List<DO> findByStringFields(Map<String, String> fieldsAndLikesMap, boolean ignoreCase, int first, int max);

    List<DO> findByStringField(String field, String likes, boolean ignoreCase, int first, int max);

    List<String> selectStringListByFieldLikeString(String field, String likes, boolean ignoreCase, int first, int max);

    List<DO> selectUsingFilter(DO filter);

    List<DO> selectUsingFilter(DO filter, int first, int max);

    List<DO> selectUsingFilter(DO filter, String... excludeProperties);

    List<DO> selectUsingFilter(DO filter, int first, int max, String... excludeProperties);

    DO update(DO object);

    void updateUsingFilter(DO filter, DO object);

    void deleteById(UUID id);

    void delete(DO object);

    void deleteUsingFilter(DO filter);

    DO insert(DO object);

    Long count();
}
