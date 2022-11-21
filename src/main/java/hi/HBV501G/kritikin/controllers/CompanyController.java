package hi.HBV501G.kritikin.controllers;

import java.net.URI;

/**
 * This class is the controller for everythin related to companies. It handles
 * the creation, deletion and updating of companies from REST requests.
 * 
 * @author Sara Þórhallsdóttir
 */

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import hi.HBV501G.kritikin.persistence.entites.Company;
import hi.HBV501G.kritikin.services.CompanyService;

@RestController
@CrossOrigin(origins = "http://localhost:8080")
public class CompanyController {

    public static final String APIURL = "/api";

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
    @GetMapping(value = APIURL + "/companies")
    public ResponseEntity<List<Company>> fetchAllcompanies() {
        return ResponseEntity.ok().body(companyService.findAll());
    }

    /**
     * Returns a particular company as a json object from a get request to
     * /api/companies/{id} which has the id of the company in the path.
     * 
     * @param id the id of the company
     * @return a particular company
     */
    @GetMapping(value = APIURL + "/company/{id}")
    public ResponseEntity<Company> company(@PathVariable long id) {
        return ResponseEntity.ok().body(companyService.findById(id));
    }

    @GetMapping(value = APIURL + "/findCompany/{name}")
    public ResponseEntity<Company> findCompany(@PathVariable String name) {
        return ResponseEntity.ok().body(companyService.findByNameIgnoreCase(name));
    }

    /**
     * Deletes a particular company from a delete request to /api/companies/{id}
     * which has the id of the company in the path.
     * 
     * @param id the id of the company to be deleted
     * @return the deleted company
     */
    @DeleteMapping(value = APIURL + "/company/{id}")
    public ResponseEntity<?> deleteCompany(@PathVariable long id) {
        Company company = companyService.findById(id);
        if (company == null) {
            return null;
        }
        companyService.delete(company);
        return ResponseEntity.noContent().build();
    }

    /**
     * Adds a company to the database from a post request to /api/company with the
     * company to be inserted in the body.
     * 
     * @param company the company to be inserted, fetched from the body of the post
     *                request
     * @return the inserted company or null if the company already exists
     */
    @PostMapping(value = APIURL + "/company")
    public ResponseEntity<Company> addCompany(@RequestBody Company company) {
        if (company == null || company.getName() == null || company.getName().equals("")) {
            return ResponseEntity.badRequest().body(null);
        }
        if (companyService.findByNameIgnoreCase(company.getName()) != null) {
            return new ResponseEntity<>(null, null, HttpStatus.CONFLICT);
        }
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(APIURL + "/company").toUriString());
        return ResponseEntity.created(uri).body(companyService.save(company));

    }
}
