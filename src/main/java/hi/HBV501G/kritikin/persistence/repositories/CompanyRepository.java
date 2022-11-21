package hi.HBV501G.kritikin.persistence.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.util.Streamable;

import hi.HBV501G.kritikin.persistence.entites.Company;

public interface CompanyRepository extends JpaRepository<Company, Long> {
    Company save(Company company);

    void delete(Company company);

    List<Company> findAll();

    Streamable<Company> findByNameIgnoreCaseContains(String name);

    Company findById(long id);

    Company getReferenceById(long id);
}
