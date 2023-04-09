package kz.kartayev.LocationSystem.util;

import kz.kartayev.LocationSystem.models.Location;
import kz.kartayev.LocationSystem.services.LocationService;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Optional;

@Component
public class LocationValidator implements Validator {
    private final LocationService service;

    public LocationValidator(LocationService service) {
        this.service = service;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return false;
    }

    @Override
    public void validate(Object target, Errors errors) {
        Location location = (Location) target;
        Optional<Location> check = Optional.ofNullable(service.findByLocation(location.getLocation()));
        if(!check.isEmpty()){
            errors.rejectValue("location", "", "This location is already used");
        }
    }
}
