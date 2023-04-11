package kz.kartayev.LocationSystem.repositories;

import kz.kartayev.LocationSystem.models.Location;
import kz.kartayev.LocationSystem.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LocationRepository extends JpaRepository<Location, Integer>  {
        Location findLocationByLocation(String s);
        Location findLocationByOwner(User owner);

        List<Location> searchLocationsByLocationStartingWith(String s);
}
