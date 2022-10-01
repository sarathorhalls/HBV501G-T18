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
        /*this.companyRepository.save(new Company("TestCompany1", 4.5, "www.testcompany1.com", 1234567, "This is a test company", "Teststreet 1", null, null, null));
        this.companyRepository.save(new Company("TestCompany2", 3.5, "www.testcompany2.com", 1234567, "This is a test company", "Teststreet 2", null, null, null));
        this.companyRepository.save(new Company("TestCompany3", 2.5, "www.testcompany3.com", 1234567, "This is a test company", "Teststreet 3", null, null, null));
        this.companyRepository.save(new Company("TestCompany4", 1.5, "www.testcompany4.com", 1234567, "This is a test company", "Teststreet 4", null, null, null));*/
    }

    @Override
    public void save(Company company) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void delete(Company company) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public Company findById(long id) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Company findByName(String name) {
        this.companyRepository.findByName(name);
        return null;
    }

    @Override
    public List<Company> findAll() {
        // TODO Auto-generated method stub
        return null;
    }
    
}
