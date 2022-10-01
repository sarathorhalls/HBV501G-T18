package hi.HBV501G.kritikin.persistence.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import hi.HBV501G.kritikin.persistence.entites.CompanyUser;

public interface CompanyUserRepository extends JpaRepository<CompanyUser, Long> {
    CompanyUser save(CompanyUser companyUser);

    void delete(CompanyUser companyUser);

    List<CompanyUser> findAll();

    CompanyUser findByUsername(String username);

    CompanyUser findById(long id);
}
