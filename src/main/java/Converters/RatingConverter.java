package Converters;

import enums.Rating;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply=true)
public class RatingConverter implements AttributeConverter<Rating, String> {

    @Override
    public String convertToDatabaseColumn(Rating rating) {
        return rating.toString();
    }

    @Override
    public Rating convertToEntityAttribute(String name) {
        return Rating.ratingByName(name);
    }
}
