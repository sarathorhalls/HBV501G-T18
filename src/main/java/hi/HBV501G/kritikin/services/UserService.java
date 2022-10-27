package hi.HBV501G.kritikin.services;

import java.util.List;

import hi.HBV501G.kritikin.persistence.entites.Authority;
import hi.HBV501G.kritikin.persistence.entites.User;

public interface UserService {
    public User save(User user);

    public Authority save(Authority authority);

    public User addAuthorityToUser(String username, String authority);

    public void delete(User user);

    public User findById(long id);

    public User findByUsername(String username);

    public List<User> findAll();

    public User getReferenceById(long id);
}
