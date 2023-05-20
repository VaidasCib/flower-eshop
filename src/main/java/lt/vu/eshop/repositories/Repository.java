package lt.vu.eshop.repositories;

import lombok.Getter;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;

public abstract class Repository<T> implements Serializable {
    @Getter
    @Inject
    private EntityManager em;

    public List<T> getAll() {
        CriteriaQuery<T> cq = em.getCriteriaBuilder().createQuery(entityClass());
        Root<T> rootEntry = cq.from(entityClass());
        CriteriaQuery<T> all = cq.select(rootEntry);
        TypedQuery<T> allQuery = em.createQuery(all);
        return allQuery.getResultList();
    }

    public void persist(T entity) {
        em.persist(entity);
    }

    public T getById(Long id) {
        return em.find(entityClass(), id);
    }

    public void delete(T entity) {
        em.remove(entity);
    }

    @SuppressWarnings("unchecked")
    protected Class<T> entityClass() {
        try {
            String className = ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0].getTypeName();
            Class<?> clazz = Class.forName(className);
            return (Class<T>) clazz;
        } catch (Exception e) {
            throw new IllegalStateException("Repository class is not parametrized with generic type!!! Please use extends <> ");
        }
    }
}
