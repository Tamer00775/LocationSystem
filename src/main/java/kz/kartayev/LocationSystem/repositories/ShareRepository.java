package kz.kartayev.LocationSystem.repositories;

import kz.kartayev.LocationSystem.models.Share;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
@Repository
public interface ShareRepository extends JpaRepository<Share, Integer> {
}
