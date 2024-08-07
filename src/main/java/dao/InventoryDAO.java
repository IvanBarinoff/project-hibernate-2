package dao;

import entities.Inventory;
import entities.Rental;
import entities.Store;
import org.hibernate.SessionFactory;
import org.hibernate.query.NativeQuery;
import org.hibernate.query.Query;

import java.util.List;
import java.util.stream.Collectors;

public class InventoryDAO extends AbstractDAO<Inventory>{
    public InventoryDAO(SessionFactory sessionFactory) {
        super(Inventory.class, sessionFactory);
    }

    public Inventory getFreeInventoryOfStore(Store store) {
        Query<Inventory> inventoryQuery = getCurrentSession()
                .createQuery("FROM Inventory i WHERE i.store.id = :id AND i.id NOT IN (SELECT DISTINCT r.inventory.id FROM Rental r WHERE r.returnDate IS NULL)", Inventory.class);

        inventoryQuery.setParameter("id", store.getId());

        inventoryQuery.setMaxResults(1);

        return inventoryQuery.getSingleResult();
    }
}
