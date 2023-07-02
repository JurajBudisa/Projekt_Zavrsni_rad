package hr.mev.zavrsnirad.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;
import java.util.List;

import hr.mev.zavrsnirad.model.CustomUserDetails;
import hr.mev.zavrsnirad.model.Prijava;
import hr.mev.zavrsnirad.service.PrijavaService;

import hr.mev.zavrsnirad.model.User;

import hr.mev.zavrsnirad.model.ZavrsniRad;
import hr.mev.zavrsnirad.service.ZavrsniRadService;

@Controller
@RequestMapping("/prijava")
public class PrijavaController {
	
	@Autowired
    private PrijavaService service;
	
	@Autowired
    private ZavrsniRadService zavrsService;
	
	@Autowired
    private ZavrsniRadService korService;

	@RequestMapping("/")
	public String viewHomePage(Model model, @AuthenticationPrincipal CustomUserDetails userDetails) {
	    String myRole = userDetails.getRole();
        if (myRole.equals("PROFESOR")) {
            return "redirect:/zavrsnirad/";
        }
        
		model.addAttribute("popisPrijava", service.getAllPrijava());
		
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		model.addAttribute("prijavljen", service.findPrijavaByEmail(auth.getName()));
		model.addAttribute("raspolozivo", zavrsService.findZavrsniRadByStatus("Slobodan", userDetails.getGodina(), userDetails.getStudij()));
		model.addAttribute("dodijeljeno", zavrsService.findZavrsniRadByStudent(auth.getName()));
		
		
		ArrayList<ZavrsniRad> popisDodijeljen = (ArrayList)zavrsService.findDodijeljen();
		List<String> nazivi = new ArrayList<String>();
		for(ZavrsniRad zavrsniRad : popisDodijeljen ) {
			String naziv = zavrsniRad.getNaziv();
			nazivi.add(naziv);
		}
		
		model.addAttribute("popisNaziva", nazivi);
		
		return "pocetnaPrijava";
	}
	
	@RequestMapping(value = "/novi", method = RequestMethod.GET)
	public String showNewPrijavaPage(Model model, @AuthenticationPrincipal CustomUserDetails userDetails) {
	    String myRole = userDetails.getRole();
        if (myRole.equals("PROFESOR")) {
            return "redirect:/zavrsnirad/";
        }
		
		Prijava prijava = new Prijava();
		model.addAttribute("prijava", prijava);
		
        List<ZavrsniRad> popisZavrsniRad = (List)zavrsService.findZavrsniRadByStatus("Slobodan", userDetails.getGodina(), userDetails.getStudij());
		model.addAttribute("popisZavrsniRad", popisZavrsniRad);
		
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        List<Prijava> prijavaBaza = (List)service.findPrijavaByEmail(auth.getName());
        List<ZavrsniRad> zavrsniRadBaza = (List)zavrsService.findZavrsniRadByStudent(auth.getName());
        if (!prijavaBaza.isEmpty() || popisZavrsniRad.isEmpty() || !zavrsniRadBaza.isEmpty()) {
    		return "redirect:/prijava/";
        }
        
		return "novi_prijava";
	}
	
	@RequestMapping(value = "/novi", method = RequestMethod.POST)
	public String showNewPrijavaPage2(@ModelAttribute("prijava") Prijava prijava, @AuthenticationPrincipal CustomUserDetails userDetails) {
	    String myRole = userDetails.getRole();
        if (myRole.equals("PROFESOR")) {
            return "redirect:/zavrsnirad/";
        }
        ZavrsniRad zavrsniRad = zavrsService.findByNaziv(prijava.getNaziv());
        prijava.setProfesor(zavrsniRad.getProfesor());
		service.createPrijava(prijava);
		return "redirect:/prijava/";
	}
	
	@RequestMapping("/brisi/{id}")
	public String brisiPrijava(@PathVariable(name = "id") int id, Model model, @AuthenticationPrincipal CustomUserDetails userDetails) {
		/*
	    String myRole = userDetails.getRole();
        if (myRole.equals("PROFESOR")) {
            return "redirect:/zavrsnirad/";
        }
        */
		
        Prijava prijava = service.getPrijava(id);
        //ZavrsniRad zavrsniRad = zavrsService.findByNaziv(prijava.getNaziv());
		//if(userDetails.getUsername() == zavrsniRad.getProfesor()) 
		service.deletePrijava(id);
		return "redirect:/prijava/";
	}
}
