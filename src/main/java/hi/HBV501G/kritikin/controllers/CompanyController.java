package hi.HBV501G.kritikin.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import hi.HBV501G.kritikin.persistence.entites.Company;
import hi.HBV501G.kritikin.services.CompanyService;

@Controller
public class CompanyController {

    private CompanyService companyService;

    @Autowired
    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }

    @GetMapping(value = HomeController.APIURL + "/company/{id}")
    public @ResponseBody Company company(@RequestParam long id) {
        return companyService.findById(id);
    }

    @DeleteMapping(value = HomeController.APIURL + "/company/{id}")
    public @ResponseBody Company deleteCompany(@PathVariable long id) {
        Company company = companyService.findById(id);
        if (company == null) {
            return null;
        }
        companyService.delete(company);
        return company;
    }

    @GetMapping(value = HomeController.APIURL + "/companies")
    public @ResponseBody List<Company> companies() {
        return companyService.findAll();
    }

    @PostMapping(value = HomeController.APIURL + "/companies")
    public @ResponseBody Company addCompany(@RequestBody Company company) {
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
