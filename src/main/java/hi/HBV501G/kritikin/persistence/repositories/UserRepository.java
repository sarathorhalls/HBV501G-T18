package hi.HBV501G.kritikin.persistence.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import hi.HBV501G.kritikin.persistence.entites.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User save(User user);

    void delete(User user);

    List<User> findAll();

    User findByUsername(String username);

    User findById(long id);
}
