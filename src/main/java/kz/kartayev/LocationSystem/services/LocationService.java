package kz.kartayev.LocationSystem.services;

import kz.kartayev.LocationSystem.models.Location;
import kz.kartayev.LocationSystem.models.User;
import kz.kartayev.LocationSystem.repositories.LocationRepository;
import kz.kartayev.LocationSystem.util.UserError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

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

    public Location findOne(int id){
        Optional<Location> location = locationRepository.findById(id);
        if(location.isPresent())
            return location.get();
        else
            throw new UserError("Not found");
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
