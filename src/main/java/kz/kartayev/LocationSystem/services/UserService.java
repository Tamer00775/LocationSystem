package kz.kartayev.LocationSystem.services;

import kz.kartayev.LocationSystem.models.Location;
import kz.kartayev.LocationSystem.models.Share;
import kz.kartayev.LocationSystem.models.User;
import kz.kartayev.LocationSystem.repositories.LocationRepository;
import kz.kartayev.LocationSystem.repositories.ShareRepository;
import kz.kartayev.LocationSystem.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class UserService {
    private final UserRepository userRepository;
    private final ShareRepository shareRepository;
    private final LocationRepository locationRepository;
    @Autowired
    public UserService(UserRepository userRepository, ShareRepository shareRepository, LocationRepository locationRepository) {
        this.userRepository = userRepository;
        this.shareRepository = shareRepository;
        this.locationRepository = locationRepository;
    }

    public User findOne(int id){
        return userRepository.findUserById(id);
    }
    public User findByEmail(String s){
        return userRepository.findUserByEmail(s);
    }
    public List<User> findAll(){
        return userRepository.findAll();
    }

    public List<User> findFriend(int id){
        List<Share> list = shareRepository.findAll().stream().filter(a -> a.getSend_id() == id).toList();
        List<User> friends = new ArrayList<>();
        for(int i = 0; i < list.size(); i++){
            friends.add(userRepository.findUserById(list.get(i).getGetter_id()));
        }
        return friends;
    }
    @Transactional
    public void save(User user){
        userRepository.save(user);
    }
    @Transactional
    public void share(Share share){
        Optional<Location> location = locationRepository.findById(share.getLocation_id());
        // TODO : Сохрани все локации которые были отправлены другому человеку
        if(location.isPresent()){
            User user = findOne(share.getGetter_id());
            user.getLocationList().add(location.get());
            userRepository.save(user);
            shareRepository.save(share);
        }
    }

    public List<User> getAllFriendOnLocation(int id, List<Location> locations){
        List<Share> shares = shareRepository.findAll().stream().filter(a -> a.getSend_id() == id).toList();
        System.out.println(shares.size());
        // 1 3 4
        Set<User> users= new HashSet<>();
        for (int i = 0; i < shares.size(); i++) {
            Optional<Location> temp = locationRepository.findById(shares.get(i).getLocation_id());
            if(temp.isPresent() && locations.contains(temp.get()))
                users.add(findOne(shares.get(i).getGetter_id()));
        }
        return new ArrayList<>(users);
    }
}
