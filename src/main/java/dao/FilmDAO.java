package dao;

import entities.Film;
import org.hibernate.SessionFactory;

public class FilmDAO extends AbstractDAO<Film>{
    public FilmDAO(SessionFactory sessionFactory) {
        super(Film.class, sessionFactory);
    }
}
