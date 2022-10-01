package hi.HBV501G.kritikin.persistence.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import hi.HBV501G.kritikin.persistence.entites.CompanyUser;

public interface CompanyUserRepository extends JpaRepository<CompanyUser, Long> {
    CompanyUser save(CompanyUser companyUser);
}
