package hi.HBV501G.kritikin.services.implementation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hi.HBV501G.kritikin.persistence.entites.Company;
import hi.HBV501G.kritikin.persistence.repositories.CompanyRepository;
import hi.HBV501G.kritikin.services.CompanyService;

@Service
public class CompanyServiceImplementation implements CompanyService {

    private final CompanyRepository companyRepository;

    @Autowired
    public CompanyServiceImplementation(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;

        // this.save(new Company("Test", 5.0, "www.test.com", 1234567, "Test
        // description", "Test address", "Test opening hours"));
        // this.save(new Company("Test2", 4.0, "www.test2.com", 1234567, "Test2
        // description", "Test2 address", "Test2 opening hours"));

    }

    @Override
    public void save(Company company) {
        companyRepository.save(company);
    }

    @Override
    public void delete(Company company) {
        companyRepository.delete(company);
    }

    @Override
    public Company findById(long id) {
        return companyRepository.findById(id);
    }

    @Override
    public Company findByName(String name) {
        return companyRepository.findByName(name);
    }

    @Override
    public List<Company> findAll() {
        return companyRepository.findAll();
    }

    @Override
    public Company getReferenceById(long id) {
        return companyRepository.getReferenceById(id);
    }
}
