package hi.HBV501G.kritikin.services.implementation;

/**
 * This class is the implementation of the CompanyService interface. It handles
 * all business logic to and from the repositories for everything regarding
 * companies.
 * 
 * @author Sara Þórhallsdóttir
 */

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hi.HBV501G.kritikin.persistence.entites.Company;
import hi.HBV501G.kritikin.persistence.repositories.CompanyRepository;
import hi.HBV501G.kritikin.services.CompanyService;

@Service
public class CompanyServiceImplementation implements CompanyService {

    private final CompanyRepository companyRepository;

    /**
     * Constructor for CompanyServiceImplementation which uses Autowired to inject
     * companyRepository from JPA.
     * 
     * @param companyRepository
     */
    @Autowired
    public CompanyServiceImplementation(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;

        // this.save(new Company("Test", 5.0, "www.test.com", 1234567, "Test
        // description", "Test address", "Test opening hours"));
        // this.save(new Company("Test2", 4.0, "www.test2.com", 1234567, "Test2
        // description", "Test2 address", "Test2 opening hours"));

    }

    /**
     * Saves a company to the database.
     * 
     * @param company the company to be saved.
     */
    @Override
    public void save(Company company) {
        companyRepository.save(company);
    }

    /**
     * Deletes a company from the database.
     * 
     * @param company the company to be deleted.
     */
    @Override
    public void delete(Company company) {
        companyRepository.delete(company);
    }

    /**
     * Returns a company from the database with a given id.
     * 
     * @param id the id of the company to be returned.
     * @return the company with the given id.
     */
    @Override
    public Company findById(long id) {
        return companyRepository.findById(id);
    }

    /**
     * Returns a company from the database with a given name.
     * 
     * @param name the name of the company to be returned.
     * @return the company with the given name.
     */
    @Override
    public Company findByName(String name) {
        return companyRepository.findByName(name);
    }

    /**
     * Returns a list of all companies in the database.
     * 
     * @return a list of all companies in the database.
     */
    @Override
    public List<Company> findAll() {
        return companyRepository.findAll();
    }

    /**
     * Returns a reference to a particular company as an object in the database with
     * the given id.
     * 
     * @param id the id of the company to be returned.
     * @return a reference to the company with the given id.
     */
    @Override
    public Company getReferenceById(long id) {
        return companyRepository.getReferenceById(id);
    }
}
