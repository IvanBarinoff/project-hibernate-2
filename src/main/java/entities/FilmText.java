package entities;

import jakarta.persistence.*;
import org.hibernate.annotations.ColumnDefault;

@Entity
@Table(schema = "movie", name = "film_text")
public class FilmText {

    @Id
    @Column(name = "film_id")
    private Short id;

    private String title;

    @Column(columnDefinition = "text")
    String description;

    @OneToOne
    @MapsId
    @JoinColumn(name = "film_id")
    private Film film;

    public Film getFilm() {
        return film;
    }

    public void setFilm(Film film) {
        this.film = film;
    }

    public Short getId() {
        return id;
    }

    public void setId(Short id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
