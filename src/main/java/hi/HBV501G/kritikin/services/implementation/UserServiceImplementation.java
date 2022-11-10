package hi.HBV501G.kritikin.services.implementation;

import java.util.ArrayList;
import java.util.Collection;

/**
 * This class is the implementation of the UserService interface. It handles
 * all business logic to and from the repositories for everything regarding
 * users.
 * 
 * @author Sara Þórhallsdóttir
 */

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import hi.HBV501G.kritikin.persistence.entites.Authority;
import hi.HBV501G.kritikin.persistence.entites.User;
import hi.HBV501G.kritikin.persistence.repositories.AuthorityRepository;
import hi.HBV501G.kritikin.persistence.repositories.UserRepository;
import hi.HBV501G.kritikin.services.UserService;

@Service
public class UserServiceImplementation implements UserService, UserDetailsService {

    Logger logger = LoggerFactory.getLogger(UserServiceImplementation.class);

    private final UserRepository userRepository;
    private final AuthorityRepository authorityRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * Constructor for UserServiceImplementation which uses AutoWired to inject the
     * UserRepository and AuthorityRepository from JPA.
     * 
     * @param userRepository
     * @param authorityRepository
     */
    @Autowired
    public UserServiceImplementation(UserRepository userRepository, AuthorityRepository authorityRepository,
            PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.authorityRepository = authorityRepository;
        this.passwordEncoder = passwordEncoder;

        // this.save(new User("Test", "password"));
        // this.save(new User("Test2", "password"));
        // this.save(new Authority("ROLE_ADMIN"));
        // this.save(new Authority("ROLE_USER"));
        // this.addAuthorityToUser("Test", "ROLE_ADMIN");
        // this.addAuthorityToUser("Test2", "ROLE_USER");
    }

    /**
     * Saves a user to the database.
     * 
     * @param user the user to be saved.
     */
    @Override
    public User save(User user) {
        logger.info("Saving user: {}", user.getUsername());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    /**
     * Saves an authority to the database.
     * 
     * @param authority the authority to be saved.
     */
    @Override
    public Authority save(Authority authority) {
        logger.info("Saving authority: {}", authority.getName());
        return authorityRepository.save(authority);
    }

    /**
     * Adds an authority to a user.
     * 
     * @param username      the username of the user.
     * @param authorityName the name of the authority to be added.
     */
    @Override
    public User addAuthorityToUser(String username, String authorityName) {
        logger.info("getting user: {} from database", username);
        User user = userRepository.findByUsername(username);
        logger.info("getting authority: {} from database", authorityName);
        Authority authority = authorityRepository.findByName(authorityName);
        logger.info("adding authority: {} to user: {}", authorityName, username);
        user.getAuthorities().add(authority);
        return userRepository.save(user);
    }

    /**
     * Deletes a user from the database.
     * 
     * @param user the user to be deleted.
     */
    @Override
    public void delete(User user) {
        logger.info("Deleting user with id: {}", user.getId());
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
        logger.info("Retrieving user with id: {}", id);
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
        logger.info("Retrieving user with username: {}", username);
        return userRepository.findByUsername(username);
    }

    /**
     * Returns a list of all users in the database.
     * 
     * @return a list of all users in the database.
     */
    @Override
    public List<User> findAll() {
        logger.info("returning all users in database");
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
        logger.info("Getting reference to user with id: {}", id);
        return userRepository.getReferenceById(id);
    }

    /**
     * Implements the loadUserByUsername method from the UserDetailsService
     * interface.
     * This method is used by Spring Security to authenticate a user.
     * 
     * @param username the username of the user to be authenticated.
     * @return a UserDetails object with the user's username, password, and
     *         authorities the user has.
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            logger.error("User not found in database");
            throw new UsernameNotFoundException("User not found in database");
        }
        logger.info("User: {}, found in database", username);

        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        user.getAuthorities().forEach(authority -> {
            authorities.add(new SimpleGrantedAuthority(authority.getName()));
        });

        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
                authorities);
    }
}
