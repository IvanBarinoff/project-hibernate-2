package dao;

import entities.Country;
import org.hibernate.SessionFactory;

public class CountryDAO extends AbstractDAO<Country>{
    public CountryDAO(SessionFactory sessionFactory) {
        super(Country.class, sessionFactory);
    }
}
