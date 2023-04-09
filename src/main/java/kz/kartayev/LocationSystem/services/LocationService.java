package kz.kartayev.LocationSystem.services;

import kz.kartayev.LocationSystem.models.Location;
import kz.kartayev.LocationSystem.models.User;
import kz.kartayev.LocationSystem.repositories.LocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class LocationService {
    private final LocationRepository locationRepository;

    @Autowired
    public LocationService(LocationRepository locationRepository) {
        this.locationRepository = locationRepository;
    }

    public List<Location> findAll(){
        return locationRepository.findAll();
    }

    @Transactional
    public void save(Location location){
        locationRepository.save(location);
    }

    public Location findByLocation(String s){
        return locationRepository.findLocationByLocation(s);
    }
    public Location findByOwner(User owner){
        return locationRepository.findLocationByOwner(owner);
    }
}
