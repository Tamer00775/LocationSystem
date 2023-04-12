package kz.kartayev.LocationSystem.repositories;

import kz.kartayev.LocationSystem.models.Share;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShareRepository extends JpaRepository<Share, Integer> {
}
