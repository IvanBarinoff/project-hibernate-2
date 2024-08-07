package dao;

import entities.Staff;
import entities.Store;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

public class StaffDAO extends AbstractDAO<Staff>{
    public StaffDAO(SessionFactory sessionFactory) {
        super(Staff.class, sessionFactory);
    }

    public Staff getAnyStaffOfStore(Store store) {
        Query<Staff> staffQuery = getCurrentSession().createQuery("FROM Staff WHERE store.id = :id", Staff.class);
        staffQuery.setParameter("id", store.getId());

        staffQuery.setMaxResults(1);

        return staffQuery.getSingleResult();
    }
}
