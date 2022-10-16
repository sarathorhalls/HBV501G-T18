package hi.HBV501G.kritikin.controllers;

/**
 * This class is the controller for everythin related to companies. It handles
 * the creation, deletion and updating of companies from REST requests.
 * 
 * @author Sara Þórhallsdóttir
 */

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import hi.HBV501G.kritikin.persistence.entites.Company;
import hi.HBV501G.kritikin.services.CompanyService;

@RestController
public class CompanyController {

    private CompanyService companyService;

    /**
     * Constructor for the CompanyController which uses Autowired to inject the
     * CompanyService from JPA
     * 
     * @param companyService
     */
    @Autowired
    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }

    /**
     * Returns a list of all companies as a json object from a get request to
     * /api/companies .
     * 
     * @return a list of all companies
     */
    @GetMapping(value = HomeController.APIURL + "/companies")
    public List<Company> fetchAllcompanies() {
        return companyService.findAll();
    }

    /**
     * Returns a particular company as a json object from a get request to
     * /api/companies/{id} which has the id of the company in the path.
     * 
     * @param id the id of the company
     * @return a particular company
     */
    @GetMapping(value = HomeController.APIURL + "/company/{id}")
    public Company company(@PathVariable long id) {
        return companyService.findById(id);
    }

    /**
     * Deletes a particular company from a delete request to /api/companies/{id}
     * which has the id of the company in the path.
     * 
     * @param id the id of the company to be deleted
     * @return the deleted company
     */
    @DeleteMapping(value = HomeController.APIURL + "/company/{id}")
    public Company deleteCompany(@PathVariable long id) {
        Company company = companyService.findById(id);
        if (company == null) {
            return null;
        }
        companyService.delete(company);
        return company;
    }

    /**
     * Adds a company to the database from a post request to /api/companies with the
     * company to be inserted in the body.
     * 
     * @param company the company to be inserted, fetched from the body of the post
     *                request
     * @return the inserted company or null if the company already exists
     */
    @PostMapping(value = HomeController.APIURL + "/companies")
    public Company addCompany(@RequestBody Company company) {
        if (company == null || company.getName() == null || company.getName().equals("")) {
            return null;
        }
        if (companyService.findByName(company.getName()) != null) {
            return null;
        }
        companyService.save(company);
        return company;
    }
}
