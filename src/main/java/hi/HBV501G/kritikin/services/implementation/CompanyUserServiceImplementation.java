package hi.HBV501G.kritikin.services.implementation;

/**
 * This class is the implementation of the CompanyUserService interface. It handles
 * all business logic to and from the repositories for everything regarding
 * company users.
 * 
 * @author Sara Þórhallsdóttir
 */

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hi.HBV501G.kritikin.persistence.entites.CompanyUser;
import hi.HBV501G.kritikin.persistence.repositories.CompanyUserRepository;
// import hi.HBV501G.kritikin.services.CompanyService;
import hi.HBV501G.kritikin.services.CompanyUserService;

@Service
public class CompanyUserServiceImplementation implements CompanyUserService {

    private final CompanyUserRepository companyUserRepository;
    // private final CompanyService companyService;

    /**
     * Constructor for CompanyUserServiceImplementation which uses AutoWired to
     * inject the CompanyUserRepository and CompanyService from JPA.
     * 
     * @param companyUserRepository
     * @param companyService
     */
    @Autowired
    public CompanyUserServiceImplementation(CompanyUserRepository companyUserRepository) {
        this.companyUserRepository = companyUserRepository;
        // this.companyService = companyService;
    }

    /**
     * Saves a companyUser to the database.
     * 
     * @param companyUser the companyUser to be saved.
     */
    @Override
    public void save(CompanyUser companyUser) {
        companyUserRepository.save(companyUser);

    }

    /**
     * Deletes a companyUser from the database.
     * 
     * @param companyUser the companyUser to be deleted.
     */
    @Override
    public void delete(CompanyUser companyUser) {
        companyUserRepository.delete(companyUser);
    }

    /**
     * Returns a companyUser from the database with a given id.
     * 
     * @param id the id of the companyUser.
     * @return the companyUser with the given id.
     */
    @Override
    public CompanyUser findById(long id) {
        return companyUserRepository.findById(id);
    }

    /**
     * Returns a companyUser from the database with a given username.
     * 
     * @param username the username of the companyUser.
     * @return the companyUser with the given username.
     */
    @Override
    public CompanyUser findByUsername(String username) {
        return companyUserRepository.findByUsername(username);
    }

    /**
     * Returns a list of all companyUsers in the database.
     * 
     * @return a list of all companyUsers in the database.
     */
    @Override
    public List<CompanyUser> findAll() {
        return companyUserRepository.findAll();
    }

}
