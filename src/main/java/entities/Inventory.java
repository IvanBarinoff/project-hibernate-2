package entities;

import jakarta.persistence.*;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Types;
import java.time.LocalDateTime;

@Entity
@Table(schema = "movie", name = "inventory")
public class Inventory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "inventory_id", columnDefinition = "mediumint")
    private int id;

    @ManyToOne
    @JoinColumn(name = "film_id", columnDefinition = "smallint")
    private Film film;

    @ManyToOne
    @JoinColumn(name = "store_id", columnDefinition = "tinyint")
    private Store store;

    @UpdateTimestamp
    @Column(name = "last_update", columnDefinition = "timestamp")
    private LocalDateTime lastUpdate;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Film getFilm() {
        return film;
    }

    public void setFilm(Film film) {
        this.film = film;
    }

    public Store getStore() {
        return store;
    }

    public void setStore(Store store) {
        this.store = store;
    }

    public LocalDateTime getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(LocalDateTime lastUpdate) {
        this.lastUpdate = lastUpdate;
    }
}
