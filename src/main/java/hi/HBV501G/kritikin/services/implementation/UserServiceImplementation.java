package hi.HBV501G.kritikin.services.implementation;

/**
 * This class is the implementation of the UserService interface. It handles
 * all business logic to and from the repositories for everything regarding
 * users.
 * 
 * @author Sara Þórhallsdóttir
 */

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hi.HBV501G.kritikin.persistence.entites.User;
import hi.HBV501G.kritikin.persistence.repositories.UserRepository;
import hi.HBV501G.kritikin.services.UserService;

@Service
public class UserServiceImplementation implements UserService {

    private final UserRepository userRepository;

    /**
     * Constructor for UserServiceImplementation which uses AutoWired to inject the
     * UserRepository from JPA.
     * 
     * @param userRepository
     */
    @Autowired
    public UserServiceImplementation(UserRepository userRepository) {
        this.userRepository = userRepository;

        // this.save(new User("Test", "password"));
        // this.save(new User("Test2", "password"));
    }

    /**
     * Saves a user to the database.
     * 
     * @param user the user to be saved.
     */
    @Override
    public void save(User user) {
        userRepository.save(user);
    }

    /**
     * Deletes a user from the database.
     * 
     * @param user the user to be deleted.
     */
    @Override
    public void delete(User user) {
        userRepository.delete(user);
    }

    /**
     * Returns a user from the database with a given id.
     * 
     * @param id the id of the user to be returned.
     * @return the user with the given id.
     */
    @Override
    public User findById(long id) {
        return userRepository.findById(id);
    }

    /**
     * Returns a user from the database with a given username.
     * 
     * @param username the username of the user to be returned.
     * @return the user with the given username.
     */
    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    /**
     * Returns a list of all users in the database.
     * 
     * @return a list of all users in the database.
     */
    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    /**
     * Returns a reference to a particular user as an object in the database with
     * the given id.
     * 
     * @param id the id of the user to be returned.
     * @return a reference to the user with the given id.
     */
    @Override
    public User getReferenceById(long id) {
        return userRepository.getReferenceById(id);
    }
}
