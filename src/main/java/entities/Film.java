package entities;

import Converters.RatingConverter;
import enums.Feature;
import enums.Rating;
import jakarta.persistence.*;
import jakarta.persistence.Table;
import org.hibernate.annotations.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.Year;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Table(schema = "movie", name = "film")
public class Film {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "film_id")
    private Short id;

    private String title;

    @Column(columnDefinition = "text")
    private String description;

    @Column(name = "release_year", columnDefinition = "year")
    private Year releaseYear;

    @ManyToOne
    @JoinColumn(name = "language_id", columnDefinition = "tinyint")
    private Language language;

    @ManyToOne
    @JoinColumn(name = "original_language_id",columnDefinition = "tinyint")
    private Language originalLanguages;

    @Column(name = "rental_duration", columnDefinition = "tinyint")
    private Byte rentalDuration;

    @Column(name = "rental_rate")
    private BigDecimal rentalRate;

    @Column(columnDefinition = "smallint")
    private Short length;

    @Column(name = "replacement_cost")
    private BigDecimal replacementCost;

    @Convert(converter = RatingConverter.class)
    @Column(columnDefinition = "enum('G', 'PG', 'PG-13', 'R', 'NC-17')")
    private Rating rating;

    @Column(name = "special_features", columnDefinition = "set('Trailers', 'Commentaries', 'Deleted Scenes', 'Behind the Scenes')")
    private String specialFeatures;

    @UpdateTimestamp
    @Column(name = "last_update", columnDefinition = "timestamp")
    private LocalDateTime lastUpdate;


    @ManyToMany
    @JoinTable(name = "film_actor",
            joinColumns = @JoinColumn(name = "film_id", referencedColumnName = "film_id"),
            inverseJoinColumns = @JoinColumn(name = "actor_id", referencedColumnName = "actor_id"))
    private Set<Actor> actors = new HashSet<>();

    @ManyToMany
    @JoinTable(name = "film_category",
            joinColumns = @JoinColumn(name = "film_id", referencedColumnName = "film_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id", referencedColumnName = "category_id"))
    private Set<Category> categories = new HashSet<>();

    public Set<Actor> getActors() {
        return actors;
    }

    public Set<Category> getCategories() {
        return categories;
    }

    public void setCategories(Set<Category> categories) {
        this.categories = categories;
    }

    public void setActors(Set<Actor> actors) {
        this.actors = actors;
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

    public Year getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(Year releaseYear) {
        this.releaseYear = releaseYear;
    }

    public Language getLanguage() {
        return language;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }

    public Language getOriginalLanguages() {
        return originalLanguages;
    }

    public void setOriginalLanguages(Language originalLanguages) {
        this.originalLanguages = originalLanguages;
    }

    public Byte getRentalDuration() {
        return rentalDuration;
    }

    public void setRentalDuration(Byte rentalDuration) {
        this.rentalDuration = rentalDuration;
    }

    public BigDecimal getRentalRate() {
        return rentalRate;
    }

    public void setRentalRate(BigDecimal rentalRate) {
        this.rentalRate = rentalRate;
    }

    public Short getLength() {
        return length;
    }

    public void setLength(Short length) {
        this.length = length;
    }

    public BigDecimal getReplacementCost() {
        return replacementCost;
    }

    public void setReplacementCost(BigDecimal replacementCost) {
        this.replacementCost = replacementCost;
    }

    public Rating getRating() {
        return rating;
    }

    public void setRating(Rating rating) {
        this.rating = rating;
    }

    public Set<Feature> getSpecialFeatures() {
        if(specialFeatures == null || specialFeatures.isBlank())
            return null;

        return Arrays.stream(specialFeatures.split(","))
                .map(Feature::getFeatureByName)
                .collect(Collectors.toSet());
    }

    public void setSpecialFeatures(Set<Feature> specialFeatures) {
        this.specialFeatures = specialFeatures.stream()
                .map(Feature::toString)
                .collect(Collectors.joining(","));
    }

    public LocalDateTime getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(LocalDateTime lastUpdate) {
        this.lastUpdate = lastUpdate;
    }
}
