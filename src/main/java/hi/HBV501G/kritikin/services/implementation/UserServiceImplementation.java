package hi.HBV501G.kritikin.services.implementation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hi.HBV501G.kritikin.persistence.entites.User;
import hi.HBV501G.kritikin.persistence.repositories.UserRepository;
import hi.HBV501G.kritikin.services.UserService;

@Service
public class UserServiceImplementation implements UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImplementation(UserRepository userRepository) {
        this.userRepository = userRepository;
        /*this.userRepository.save(new User("testuser1", "testuser1", null, null));
        this.userRepository.save(new User("testuser2", "testuser2", null, null));
        this.userRepository.save(new User("testuser3", "testuser3", null, null));
        this.userRepository.save(new User("testuser4", "testuser4", null, null));*/
    }

    @Override
    public void save(User user) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void delete(User user) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public User findById(long id) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public User findByUsername(String username) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<User> findAll() {
        // TODO Auto-generated method stub
        return null;
    }
    
}
