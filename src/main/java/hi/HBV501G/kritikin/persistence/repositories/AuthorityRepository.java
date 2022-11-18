package hi.HBV501G.kritikin.persistence.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import hi.HBV501G.kritikin.persistence.entites.Authority;
import hi.HBV501G.kritikin.persistence.entites.EAuthority;

public interface AuthorityRepository extends JpaRepository<Authority, Long> {
    Authority save(Authority authority);

    void delete(Authority authority);

    List<Authority> findAll();

    Authority findByName(EAuthority name);

    Authority findById(long id);
}
