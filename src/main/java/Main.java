import dao.*;
import entities.*;
import enums.Feature;
import enums.Rating;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.Year;
import java.util.*;
import java.util.stream.Collectors;

public class Main {

    final private static SessionFactory sessionFactory;

    private static ActorDAO actorDAO;
    private static AddressDAO addressDAO;
    private static CategoryDAO categoryDAO;
    private static CityDAO cityDAO;
    private static CountryDAO countryDAO;
    private static CustomerDAO customerDAO;
    private static FilmDAO filmDAO;
    private static FilmTextDAO filmTextDAO;
    private static InventoryDAO inventoryDAO;
    private static LanguageDAO languageDAO;
    private static PaymentDAO paymentDAO;
    private static RentalDAO rentalDAO;
    private static StaffDAO staffDAO;
    private static StoreDAO storeDAO;

    static {
        Properties properties = new Properties();
        properties.put(Environment.DIALECT, "org.hibernate.dialect.MySQL8Dialect");
        properties.put(Environment.DRIVER, "com.p6spy.engine.spy.P6SpyDriver");
        properties.put(Environment.URL, "jdbc:p6spy:mysql://localhost:3306/movie");
        properties.put(Environment.USER, "root");
        properties.put(Environment.PASS, "root");
        properties.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread");
        properties.put(Environment.HBM2DDL_AUTO, "validate");

        sessionFactory = new Configuration()
                .addAnnotatedClass(Film.class)
                .addAnnotatedClass(Language.class)
                .addAnnotatedClass(FilmText.class)
                .addAnnotatedClass(Actor.class)
                .addAnnotatedClass(Category.class)
                .addAnnotatedClass(Address.class)
                .addAnnotatedClass(City.class)
                .addAnnotatedClass(Country.class)
                .addAnnotatedClass(Inventory.class)
                .addAnnotatedClass(Staff.class)
                .addAnnotatedClass(Store.class)
                .addAnnotatedClass(Customer.class)
                .addAnnotatedClass(Rental.class)
                .addAnnotatedClass(Payment.class)
                .setProperties(properties)
                .buildSessionFactory();

        actorDAO= new ActorDAO(sessionFactory);
        addressDAO= new AddressDAO(sessionFactory);
        categoryDAO = new CategoryDAO(sessionFactory);
        cityDAO = new CityDAO(sessionFactory);
        countryDAO = new CountryDAO(sessionFactory);
        customerDAO = new CustomerDAO(sessionFactory);
        filmDAO = new FilmDAO(sessionFactory);
        filmTextDAO = new FilmTextDAO(sessionFactory);
        inventoryDAO = new InventoryDAO(sessionFactory);
        languageDAO = new LanguageDAO(sessionFactory);
        paymentDAO = new PaymentDAO(sessionFactory);
        rentalDAO = new RentalDAO(sessionFactory);
        staffDAO = new StaffDAO(sessionFactory);
        storeDAO = new StoreDAO(sessionFactory);
    }

    public static void main(String[] args) {
        //Customer customer = createCustomer();
        //System.out.println("id customer = " + customer.getId());

        //customerReturnFilm(479);

        //Payment payment = rentalInventory(601);
        //System.out.println("id payment = " + payment.getId());

        //Film film = createFilm();
        //System.out.println("id film = " + film.getId());
    }

    public static Customer createCustomer() {
        try(Session session = sessionFactory.openSession()) {
            sessionFactory.getCurrentSession().beginTransaction();

            Store store = storeDAO.getById(1);

            Address address = new Address();
            address.setAddress("dom 2 drob reshetka");
            address.setDistrict("California");
            address.setCity(cityDAO.getById(3));
            address.setPostalCode("34580");
            address.setPhone("88005553535");

            addressDAO.create(address);

            Customer customer = new Customer();
            customer.setStore(store);
            customer.setFirstName("Ivan");
            customer.setLastName("Bulatnikov");
            customer.setEmail("sorry@gmail.com");
            customer.setAddress(address);
            customer.setActive(true);

            customerDAO.create(customer);

            sessionFactory.getCurrentSession().getTransaction().commit();

            return customer;
        }
    }

    public static void customerReturnFilm(int idCustomer) {
        try(Session session = sessionFactory.openSession()) {
            sessionFactory.getCurrentSession().beginTransaction();

            Rental rental = rentalDAO.getRandomUnfinishedRentalByCustomer(idCustomer);
            rental.setReturnDate(LocalDateTime.now());

            rentalDAO.update(rental);

            sessionFactory.getCurrentSession().getTransaction().commit();
        }
    }

    public static Payment rentalInventory(int idCustomer) {
        try(Session session = sessionFactory.openSession()) {
            sessionFactory.getCurrentSession().beginTransaction();

            Rental rental = new Rental();

            Customer customer = customerDAO.getById(idCustomer);
            Store store = storeDAO.getById(1);
            Inventory inventory = inventoryDAO.getFreeInventoryOfStore(store);
            Staff staff = staffDAO.getAnyStaffOfStore(store);

            rental.setRentalDate(LocalDateTime.now());
            rental.setInventory(inventory);
            rental.setCustomer(customer);
            rental.setStaff(staff);

            rentalDAO.create(rental);

            Payment payment = new Payment();

            payment.setCustomer(customer);
            payment.setStaff(staff);
            payment.setRental(rental);
            payment.setAmount(new BigDecimal(10.99));
            payment.setPaymentDate(LocalDateTime.now());

            paymentDAO.create(payment);

            sessionFactory.getCurrentSession().getTransaction().commit();

            return payment;
        }
    }

    public static Film createFilm() {
        try(Session session = sessionFactory.openSession()) {
            sessionFactory.getCurrentSession().beginTransaction();

            Language language1 = languageDAO.getById(1);
            Language language2 = languageDAO.getById(3);

            List<Actor> actors = actorDAO.getItems(0, 10);
            List<Category> categories = categoryDAO.getItems(0, 7);

            Film film = new Film();

            film.setTitle("rep");
            film.setDescription("realLife");
            film.setReleaseYear(Year.now());
            film.setLanguage(language1);
            film.setOriginalLanguages(language2);
            film.setRentalDuration((byte) 4);
            film.setRentalRate(new BigDecimal(5.00));
            film.setReplacementCost(new BigDecimal(27.00));
            film.setLength((short) 50);
            film.setSpecialFeatures(Set.of(Feature.COMMENTARIES, Feature.TRAILER));
            film.setRating(Rating.PG13);

            film.setActors(actors.stream().collect(Collectors.toSet()));
            film.setCategories(categories.stream().collect(Collectors.toSet()));

            filmDAO.create(film);

            FilmText filmText = new FilmText();

            filmText.setFilm(film);
            filmText.setTitle("ONO");
            filmText.setDescription("Scary");

            filmTextDAO.create(filmText);

            Inventory inventory = new Inventory();
            inventory.setFilm(film);
            inventory.setStore(storeDAO.getById(2));

            inventoryDAO.create(inventory);

            sessionFactory.getCurrentSession().getTransaction().commit();

            return film;
        }
    }
}
