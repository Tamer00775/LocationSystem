package kz.kartayev.LocationSystem.services;

import kz.kartayev.LocationSystem.models.Share;
import kz.kartayev.LocationSystem.repositories.ShareRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class ShareService {
  private final ShareRepository shareRepository;
  @Autowired
  public ShareService(ShareRepository shareRepository) {
    this.shareRepository = shareRepository;
  }

  public List<Share> findAll(){
    return shareRepository.findAll();
  }

  public Optional<Share> findOne(int id){
    return shareRepository.findById(id);
  }
  @Transactional
  public void manageAccess(int id, int friend_id, String newStatus, int location_id){
    List<Share> shares = shareRepository.findAll();
    Share newShare = new Share();
    for(Share share : shares){
      if(share.getGetter_id() == friend_id
              && share.getSend_id() == id
              && share.getLocation_id() == location_id){
        newShare = share;
        newShare.setStatus(newStatus);
      }

    }
    shareRepository.save(newShare);
  }
}
