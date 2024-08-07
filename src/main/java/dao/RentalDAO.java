package dao;

import entities.Customer;
import entities.Rental;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.util.List;

public class RentalDAO extends AbstractDAO<Rental>{
    public RentalDAO(SessionFactory sessionFactory) {
        super(Rental.class, sessionFactory);
    }

    public Rental getRandomUnfinishedRentalByCustomer(int customerId) {
        Query<Rental> rentals = getCurrentSession().createQuery("FROM Rental WHERE customer.id = :id AND returnDate IS NULL", Rental.class);
        rentals.setParameter("id", customerId);

        rentals.setMaxResults(1);

        return rentals.getSingleResult();
    }
}
