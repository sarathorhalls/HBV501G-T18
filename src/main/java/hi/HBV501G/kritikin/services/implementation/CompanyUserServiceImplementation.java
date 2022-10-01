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
    public CompanyUserServiceImplementation(CompanyUserRepository companyUserRepository, CompanyService companyService) {
        this.companyUserRepository = companyUserRepository;
        this.companyService = companyService;
        //this.companyUserRepository.save(new CompanyUser(companyService.findByName("TestCompany1"), "testcompanyuser1", "testcompanyuser1"));
    }

    @Override
    public void save(CompanyUser companyUser) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void delete(CompanyUser companyUser) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public CompanyUser findById(long id) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public CompanyUser findByUsername(String username) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<CompanyUser> findAll() {
        // TODO Auto-generated method stub
        return null;
    }
    
}
