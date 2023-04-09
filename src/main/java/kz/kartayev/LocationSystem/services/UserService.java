package kz.kartayev.LocationSystem.services;

import kz.kartayev.LocationSystem.models.User;
import kz.kartayev.LocationSystem.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class UserService {
    private final UserRepository userRepository;
    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
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
    @Transactional
    public void save(User user){
        userRepository.save(user);
    }

}
