package hi.HBV501G.kritikin.services.implementation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hi.HBV501G.kritikin.persistence.entites.CompanyUser;
import hi.HBV501G.kritikin.persistence.repositories.CompanyUserRepository;
import hi.HBV501G.kritikin.services.CompanyService;
import hi.HBV501G.kritikin.services.CompanyUserService;

@Service
public class CompanyUserServiceImplementation implements CompanyUserService {

    private final CompanyUserRepository companyUserRepository;
    private final CompanyService companyService;

    @Autowired
    public CompanyUserServiceImplementation(CompanyUserRepository companyUserRepository,
            CompanyService companyService) {
        this.companyUserRepository = companyUserRepository;
        this.companyService = companyService;
    }

    @Override
    public void save(CompanyUser companyUser) {
        companyUserRepository.save(companyUser);

    }

    @Override
    public void delete(CompanyUser companyUser) {
        companyUserRepository.delete(companyUser);
    }

    @Override
    public CompanyUser findById(long id) {
        return companyUserRepository.findById(id);
    }

    @Override
    public CompanyUser findByUsername(String username) {
        return companyUserRepository.findByUsername(username);
    }

    @Override
    public List<CompanyUser> findAll() {
        return companyUserRepository.findAll();
    }

}
