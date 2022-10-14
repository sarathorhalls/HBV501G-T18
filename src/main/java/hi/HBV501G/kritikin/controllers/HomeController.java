package hi.HBV501G.kritikin.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {

    /**
     * The base URL for all API calls
     */
    public static final String APIURL = "/api";

    /**
     * Redirects from / and /company/* to the index page where the React app is
     * loaded and routes are handled
     * 
     * @return the index page
     */
    @RequestMapping(value = { "/", "/company/*" })
    public String index() {
        return "index";
    }
}
