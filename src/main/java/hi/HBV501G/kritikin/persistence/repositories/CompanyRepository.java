package hi.HBV501G.kritikin.persistence.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import hi.HBV501G.kritikin.persistence.entites.Company;

public interface CompanyRepository extends JpaRepository<Company, Long> {
    Company save(Company company);
    Company findByName(String name);
}
