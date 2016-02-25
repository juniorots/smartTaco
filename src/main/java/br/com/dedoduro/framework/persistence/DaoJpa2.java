package br.com.dedoduro.framework.persistence;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.Attribute;
import javax.persistence.metamodel.EntityType;
import javax.persistence.metamodel.Metamodel;
import lombok.extern.log4j.Log4j;

/**
 *
 * @author Rafael Quintino
 */
@Log4j
public class DaoJpa2<DO extends DomainObject> implements DataAccessObject<DO> {

    private static final String NOT_SUPPORTED_YET = "Not supported yet.";

    private Class<DO> domainClass;
    private EntityManager entityManager;

    public DaoJpa2(Class<DO> domainClass, EntityManager entityManager) {
        this.entityManager = entityManager;
        this.domainClass = domainClass;
        if (log.isDebugEnabled()) {
            log.debug(this.getClass().getName() + " instanciado");
        }
    }

    @Override
    public List<DO> selectAll() {
        CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<DO> criteriaQuery = criteriaBuilder.createQuery(getDomainClass());
        criteriaQuery.from(getDomainClass());
        TypedQuery<DO> query = getEntityManager().createQuery(criteriaQuery);
        return query.getResultList();
    }

    @Override
    public List<DO> selectAll(int first, int max) {
        CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<DO> criteriaQuery = criteriaBuilder.createQuery(getDomainClass());
        criteriaQuery.from(getDomainClass());
        TypedQuery<DO> query = getEntityManager().createQuery(criteriaQuery);
        query.setFirstResult(first);
        query.setMaxResults(max);
        return query.getResultList();
    }

    /**
     * Normalmente utilizado para consultas onde o codigo do registro sera
     * fonte de pesquisa
     * @param campo
     * @param filtro
     * @return 
     */
    public DO selectByCodigo(String campo, Long filtro) {
        CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<DO> criteriaQuery = criteriaBuilder.createQuery(getDomainClass());
        Root<DO> root = criteriaQuery.from(getDomainClass());

        List<Predicate> predicates = new ArrayList<>();
            Path path = this.getField(root, campo);
            if (path != null) {
                Predicate predicate;
                predicate = criteriaBuilder.equal(path, filtro );
                predicates.add(predicate);
            }
        
        criteriaQuery.where(predicates.toArray(new Predicate[]{}));
        TypedQuery<DO> query = getEntityManager().createQuery(criteriaQuery);

        DO result = null;
        try {
            result = query.getSingleResult();
        } catch ( NoResultException ne ) {
            // TO-DO nothing! :-) Por nao ter encontrado nenhum registro
        }

        return result;
    }
    
    @Override
    public DO selectById(UUID id) {
        return getEntityManager().find(getDomainClass(), id);
    }

    protected Path getField(Root<DO> root, String field) {
        Path path;
        String[] fields = field.split("\\.");

        if (fields.length > 1) {
            path = root.get(fields[0]);
            for (int i = 1; i < fields.length; i++) {
                path = path.get(fields[i]);
            }
        } else {
            path = root.get(field);
        }

        return path;
    }

    protected <T> Path<T> getTemplateField(Root<DO> root, String field) {
        return (Path<T>) getField(root, field);
    }
    
    @SuppressWarnings({"unchecked", "rawtypes"})
    public DO findByStringDateOperatorEqual(Map<String, String> firstFieldsMap, Map<String, Date> secondFieldsMap, boolean ignoreCase, int first, int max) {
        CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<DO> criteriaQuery = criteriaBuilder.createQuery(getDomainClass());
        Root<DO> root = criteriaQuery.from(getDomainClass());

        List<Predicate> predicates = new ArrayList<>();
        for (Map.Entry<String, String> entry : firstFieldsMap.entrySet()) {
            Path path = this.getField(root, entry.getKey());
            if (path != null) {
                Predicate predicate;

                if (ignoreCase) {
                    predicate = criteriaBuilder.equal(criteriaBuilder.upper(path), entry.getValue().toUpperCase());
                } else {
                    predicate = criteriaBuilder.equal(path, entry.getValue());
                }

                predicates.add(predicate);
            }
        }
        
        for (Map.Entry<String, Date> entry : secondFieldsMap.entrySet()) {
            Path path = this.getField(root, entry.getKey());
            if (path != null) {
                Predicate predicate;
                predicate = criteriaBuilder.equal(path, entry.getValue());
                predicates.add(predicate);
            }
        }
        
        criteriaQuery.where(predicates.toArray(new Predicate[]{}));
        TypedQuery<DO> query = getEntityManager().createQuery(criteriaQuery);
        query.setFirstResult(first);
        query.setMaxResults(max);

        DO result = null;
        try {
            result = query.getSingleResult();
        } catch ( NoResultException ne ) {
            // TO-DO nothing! :-) Por nao ter encontrado nenhum registro
        }

        return result;
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    @Override
    public List<DO> findByStringFields(Map<String, String> fieldsAndLikesMap, boolean ignoreCase, int first, int max) {
        //public List<DO> findByAttributes(Map<String, String> attributes, int first, int max) {
        /* http://dominikdorn.com/2010/06/jpa2-abstract-dao-criteria-query-like-operator/ */

        CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<DO> criteriaQuery = criteriaBuilder.createQuery(getDomainClass());
        Root<DO> root = criteriaQuery.from(getDomainClass());

        List<Predicate> predicates = new ArrayList<>();
        for (Map.Entry<String, String> entry : fieldsAndLikesMap.entrySet()) {
            Path path = this.getField(root, entry.getKey());
            if (path != null) {
                Predicate predicate;

                if (ignoreCase) {
                    if (entry.getKey().equalsIgnoreCase("dtNascimento")) {
                        ParameterExpression<Date> param = criteriaBuilder.parameter(Date.class, entry.getValue().toUpperCase() );
                        predicate = criteriaBuilder.equal(criteriaBuilder.upper(path), param);
                    } else {
                        predicate = criteriaBuilder.like(criteriaBuilder.upper(path), entry.getValue().toUpperCase());
                    }
                } else {
                    predicate = criteriaBuilder.like(path, entry.getValue());
                }

                predicates.add(predicate);
            }
        }
        criteriaQuery.where(predicates.toArray(new Predicate[]{}));
        TypedQuery<DO> query = getEntityManager().createQuery(criteriaQuery);
        query.setFirstResult(first);
        query.setMaxResults(max);

        List<DO> results = query.getResultList();

        return results;
    }

    public List<DO> findByStringFieldsUsingPredicateOR(Map<String, String> fieldsAndLikesMap, boolean ignoreCase, int first, int max) {
        Set<DO> set = new HashSet<>();

        List<DO> doList;
        for (Map.Entry<String, String> entry : fieldsAndLikesMap.entrySet()) {
            doList = findByStringField(entry.getKey(), entry.getValue(), ignoreCase, first, max);
            set.addAll(doList);
        }
        doList = new LinkedList<>();
        doList.addAll(set);

        return doList.subList(0, Math.min(doList.size(), max));
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    @Override
    public List<DO> findByStringField(String field, String likes, boolean ignoreCase, int first, int max) {
        CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<DO> criteriaQuery = criteriaBuilder.createQuery(getDomainClass());
        Root<DO> root = criteriaQuery.from(getDomainClass());

        List<Predicate> predicates = new ArrayList<>();

        Path path = this.getField(root, field);
        if (path != null) {
            if (ignoreCase) {
                predicates.add(criteriaBuilder.like(criteriaBuilder.upper(path), likes.toUpperCase()));
            } else {
                predicates.add(criteriaBuilder.like(path, likes));
            }
        }

        criteriaQuery.where(predicates.toArray(new Predicate[]{}));
        TypedQuery<DO> query = getEntityManager().createQuery(criteriaQuery);
        query.setFirstResult(first);
        query.setMaxResults(max);

        List<DO> results = query.getResultList();

        return results;
    }

    @Override
    public List<String> selectStringListByFieldLikeString(String field, String likes, boolean ignoreCase, int first, int max) {
        //TODO - Validar parâmetros de entrada.

        CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<String> criteriaQuery = criteriaBuilder.createQuery(String.class);
        Root<DO> root = criteriaQuery.from(getDomainClass());
        //criteriaQuery.select(root.<String>get(field));
        criteriaQuery.select(this.<String>getTemplateField(root, field));
        if (ignoreCase) {
            //TODO - Tentar fazer mesma coisa do else só que com o ignoreCase!!!
            //criteriaQuery.where(criteriaBuilder.lower(root.<String>get(field)) criteriaBuilder.like(root.<String>get(field), "%" + likes + "%"));
            criteriaQuery.where(criteriaBuilder.like(criteriaBuilder.upper(this.<String>getTemplateField(root, field)), likes.toUpperCase()));
        } else {
            criteriaQuery.where(criteriaBuilder.like(this.<String>getTemplateField(root, field), likes));
        }
        TypedQuery<String> query = getEntityManager().createQuery(criteriaQuery);
        query.setFirstResult(first);
        query.setMaxResults(max);

        List<String> results = query.getResultList();

        return results;
    }

    @Override
    public List<DO> selectUsingFilter(DO filter) {
        TypedQuery<DO> query = createQueryUsingFilter(filter, (String[]) null);
        return query.getResultList();
    }

    @Override
    public List<DO> selectUsingFilter(DO filter, int first, int max) {
        TypedQuery<DO> query = createQueryUsingFilter(filter, (String[]) null);
        query.setFirstResult(first);
        query.setMaxResults(max);
        return query.getResultList();
    }

    @Override
    public List<DO> selectUsingFilter(DO filter, String... excludeProperties) {
        TypedQuery<DO> query = createQueryUsingFilter(filter, excludeProperties);
        return query.getResultList();
    }

    @Override
    public List<DO> selectUsingFilter(DO filter, int first, int max, String... excludeProperties) {
        TypedQuery<DO> query = createQueryUsingFilter(filter, excludeProperties);
        query.setFirstResult(first);
        query.setMaxResults(max);
        return query.getResultList();
    }

    private TypedQuery<DO> createQueryUsingFilter(DO filter, String... excludeProperties) {
        String[] excluded = excludeProperties;
        if (excluded == null) {
            excluded = new String[0];
        }
        Arrays.sort(excluded);

        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<DO> cq = cb.createQuery(domainClass);
        Root<DO> r = cq.from(domainClass);
        Predicate p = cb.conjunction();
        EntityType<DO> et = this.getDomainObjectMetaModel();
        Set<Attribute<? super DO, ?>> attrs = et.getAttributes();
        for (Attribute<? super DO, ?> a : attrs) {
            String name = a.getName();
            if (Arrays.binarySearch(excluded, name) < 0) {
                String javaName = a.getJavaMember().getName();
                String getter = "get" + javaName.substring(0, 1).toUpperCase() + javaName.substring(1);
                try {
                    Method m = domainClass.getMethod(getter, (Class<?>[]) null);
                    if (m.invoke(filter, (Object[]) null) != null) {
                        p = cb.and(p, cb.equal(r.get(name), m.invoke(filter, (Object[]) null)));
                    }
                } catch (Exception e) {
                    log.error(e);
                }
            }
        }
        cq.select(r).where(p);
        return entityManager.createQuery(cq);
    }

    @Override
    public DO update(DO object) {
        return getEntityManager().merge(object);
    }

    @Override
    public void updateUsingFilter(DO filter, DO object) {
        throw new UnsupportedOperationException(NOT_SUPPORTED_YET);
    }

    @Override
    public void deleteById(UUID id) {
        DO object = this.selectById(id);
        if (object != null) {
            this.delete(object);
        } else {
            log.warn("Id = " + id + " inexistente, portanto não pode ser deletado.");
        }
    }

    @Override
    public void delete(DO object) {
        getEntityManager().remove(object);
        if (log.isDebugEnabled()) {
            log.debug(object.getClass().getName() + ".id = " + object.getId() + " deletado.");
        }
    }

    @Override
    public void deleteUsingFilter(DO filter) {
        throw new UnsupportedOperationException(NOT_SUPPORTED_YET);
    }

    @Override
    public DO insert(DO object) {
        try {
            getEntityManager().persist(object);
            getEntityManager().flush();
            if (log.isDebugEnabled()) {
                log.debug(object.getClass().getName() + ".id=" + object.getId() + " persistido");
            }
        } catch (RuntimeException ex) {
            log.error(ex);
            throw ex;
        }
        return object;
    }

    @Override
    public Long count() {
        CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);
        criteriaQuery.select(criteriaBuilder.count(criteriaQuery.from(getDomainClass())));
        return getEntityManager().createQuery(criteriaQuery).getSingleResult();
    }

    public Class<DO> getDomainClass() {
        return domainClass;
    }

    public void setDomainClass(Class<DO> domainClass) {
        this.domainClass = domainClass;
    }

    public EntityManager getEntityManager() {
        return entityManager;
    }

    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    protected EntityType<DO> getDomainObjectMetaModel() {
        Metamodel metamodel = getEntityManager().getMetamodel();
        EntityType<DO> domainObjectModel = metamodel.entity(domainClass);
        return domainObjectModel;
    }
}
