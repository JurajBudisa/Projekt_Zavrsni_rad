package hr.mev.zavrsnirad.controller;


import java.util.Collection;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import hr.mev.zavrsnirad.model.CustomUserDetails;
import hr.mev.zavrsnirad.model.User;
import hr.mev.zavrsnirad.service.KorisnikService;

@Controller
public class LoginController {

	@Autowired
    private KorisnikService korisnikService;

    @RequestMapping(value={"/", "/login"}, method = RequestMethod.GET)
    public ModelAndView login(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("login");
        return modelAndView;
    }


    @RequestMapping(value="/registracija_profesor", method = RequestMethod.GET)
    public ModelAndView registracija_profesor(){
        ModelAndView modelAndView = new ModelAndView();
        User korisnik = new User();
        modelAndView.addObject("user", korisnik);
        modelAndView.setViewName("registracija_profesor");
        return modelAndView;
    }

    @RequestMapping(value = "/registracija_profesor", method = RequestMethod.POST)
    public ModelAndView createNewProfesor(@Validated User korisnik, BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView();
        User korisnikBaza = korisnikService.findUserByEmail(korisnik.getEmail());
        if (korisnikBaza != null) {
            bindingResult
                    .rejectValue("email", "error.user",
                            "U aplikaciji već imamo korisnika s ovom email adresom");
        }
        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("registracija_profesor");
        } else {
        	korisnik.setActive(1);
        	korisnikService.saveProfesor(korisnik);
            modelAndView.addObject("successMessage", "Korisnik je uspješno registriran");
            modelAndView.addObject("user", new User());
            modelAndView.setViewName("registracija_profesor");

        }
        return modelAndView;
    }
    
    @RequestMapping(value="/registracija_student", method = RequestMethod.GET)
    public ModelAndView registracija_student(){
        ModelAndView modelAndView = new ModelAndView();
        User korisnik = new User();
        modelAndView.addObject("user", korisnik);
        modelAndView.setViewName("registracija_student");
        return modelAndView;
    }

    @RequestMapping(value = "/registracija_student", method = RequestMethod.POST)
    public ModelAndView createNewStudent(@Validated User korisnik, BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView();
        User korisnikBaza = korisnikService.findUserByEmail(korisnik.getEmail());
        if (korisnikBaza != null) {
            bindingResult
                    .rejectValue("email", "error.user",
                            "U aplikaciji već imamo korisnika s ovom email adresom");
        }
        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("registracija_student");
        } else {
        	korisnik.setActive(1);
        	korisnikService.saveStudent(korisnik);
            modelAndView.addObject("successMessage", "Korisnik je uspješno registriran");
            modelAndView.addObject("user", new User());
            modelAndView.setViewName("registracija_student");

        }
        return modelAndView;
    }

    @RequestMapping(value="/default", method = RequestMethod.GET)
    public String defaultAfterLogin(@AuthenticationPrincipal CustomUserDetails userDetails) {
	    String myRole = userDetails.getRole();
        if (myRole.equals("PROFESOR")) {
            return "redirect:/zavrsnirad/";
        }
        return "redirect:/prijava/";
    }
}
