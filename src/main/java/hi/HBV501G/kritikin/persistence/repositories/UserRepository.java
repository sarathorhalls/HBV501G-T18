package hi.HBV501G.kritikin.persistence.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import hi.HBV501G.kritikin.persistence.entites.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User save(User user);
}
