package dao;

import jakarta.persistence.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;

public abstract class AbstractDAO<T> {
    private final Class<T> clazz;
    private SessionFactory sessionFactory;

    public AbstractDAO(Class<T> clazz, SessionFactory sessionFactory) {
        this.clazz = clazz;
        this.sessionFactory = sessionFactory;
    }

    public T getById(final long id) {
        return getCurrentSession().get(clazz, id);
    }

    public List<T> getItems(int from, int count) {
        Query query = getCurrentSession().createQuery("from " + clazz.getName(), clazz);
        query.setFirstResult(from);
        query.setMaxResults(count);
        return query.getResultList();
    }

    public List<T> findAll() {
        return getCurrentSession().createQuery("from " + clazz.getName(), clazz).list();
    }

    public T create(final T entity) {
        getCurrentSession().persist(entity);
        return entity;
    }

    public T update(final T entity) {
        return getCurrentSession().merge(entity);
    }

    public void delete(final T entity) {
        getCurrentSession().remove(entity);
    }

    public void deleteById(final long entityId) {
        final T entity = getById(entityId);
        delete(entity);
    }

    protected Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}
