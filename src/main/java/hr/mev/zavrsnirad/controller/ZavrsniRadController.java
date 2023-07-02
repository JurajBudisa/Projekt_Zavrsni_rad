package hr.mev.zavrsnirad.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

import hr.mev.zavrsnirad.model.CustomUserDetails;
import hr.mev.zavrsnirad.model.Prijava;
import hr.mev.zavrsnirad.model.User;
import hr.mev.zavrsnirad.model.ZavrsniRad;
import hr.mev.zavrsnirad.service.KorisnikService;
import hr.mev.zavrsnirad.service.PrijavaService;
import hr.mev.zavrsnirad.service.ZavrsniRadService;

@Controller
@RequestMapping("/zavrsnirad")
public class ZavrsniRadController {
	
	@Autowired
    private ZavrsniRadService service;
	
	@Autowired
    private PrijavaService prijavaService;
	
	@Autowired
    private KorisnikService korisnikService;

	@RequestMapping("/")
	public String viewHomePage(Model model, @AuthenticationPrincipal CustomUserDetails userDetails) {
	    String myRole = userDetails.getRole();
        if (myRole.equals("STUDENT")) {
            return "redirect:/prijava/";
        }
		
		ArrayList<ZavrsniRad> popisZavrsniRad = (ArrayList)service.getAllZavrsniRad();
		ArrayList<Prijava> popisPrijava = (ArrayList)prijavaService.getAllPrijava();
		model.addAttribute("popisZavrsniRad", popisZavrsniRad);
		model.addAttribute("popisPrijava", popisPrijava);
		
		ArrayList<ZavrsniRad> popisDodijeljen = (ArrayList)service.findDodijeljen();
		List<String> nazivi = new ArrayList<String>();
		for(ZavrsniRad zavrsniRad : popisDodijeljen ) {
			String naziv = zavrsniRad.getNaziv();
			nazivi.add(naziv);
		}
		
		model.addAttribute("popisNaziva", nazivi);
		
		
		/*
		List<ZavrsniRad> zavrsniRadList = (List)service.findZavrsniRadByProfesor(userDetails.getUsername());
		model.addAttribute("zavrsniRadList", zavrsniRadList);
		*/
		
		return "pocetnaZavrsniRad";
	}
	
	@RequestMapping(value = "/novi", method = RequestMethod.GET)
	public String showNewZavrsniRadPage(Model model, @AuthenticationPrincipal CustomUserDetails userDetails) {
	    String myRole = userDetails.getRole();
        if (myRole.equals("STUDENT")) {
            return "redirect:/prijava/";
        }
		
		ZavrsniRad zavrsnirad = new ZavrsniRad();
		model.addAttribute("zavrsnirad", zavrsnirad);
		return "novi_zavrsnirad";
	}
	
	@RequestMapping(value = "/novi", method = RequestMethod.POST)
	public String showNewZavrsniRadPage2(@ModelAttribute("zavrsnirad") ZavrsniRad zavrsnirad, @AuthenticationPrincipal CustomUserDetails userDetails) {
	    String myRole = userDetails.getRole();
        if (myRole.equals("STUDENT")) {
            return "redirect:/prijava/";
        }
        
        List<ZavrsniRad> zavrsniRadBaza = (List)service.findZavrsniRadByNaziv(zavrsnirad.getNaziv());
        if (zavrsniRadBaza.isEmpty() & !zavrsnirad.getNaziv().isEmpty() & !zavrsnirad.getOpis().isEmpty()) {
    		service.createZavrsniRad(zavrsnirad);
        }
		return "redirect:/zavrsnirad/";
	}
	
	
	@RequestMapping("/uredi/{id}")
	public String showEditZavrsniRadPage(@PathVariable(name = "id") int id, Model model, @AuthenticationPrincipal CustomUserDetails userDetails) {
	    String myRole = userDetails.getRole();
        if (myRole.equals("STUDENT")) {
            return "redirect:/prijava/";
        }
		
		ZavrsniRad zavrsnirad = service.getZavrsniRad(id);
		model.addAttribute("zavrsnirad", zavrsnirad);
		
		return "uredi_zavrsnirad";
	}
	
	@RequestMapping(value = "/uredi", method = RequestMethod.POST)
	public String saveZavrsniRad(@ModelAttribute("zavrsnirad") ZavrsniRad zavrsnirad, @AuthenticationPrincipal CustomUserDetails userDetails) {
	    String myRole = userDetails.getRole();
        if (myRole.equals("STUDENT")) {
            return "redirect:/prijava/";
        }
		
        List<ZavrsniRad> zavrsniRadBaza = (List)service.findZavrsniRadByNaziv(zavrsnirad.getNaziv());
        if (zavrsniRadBaza.isEmpty() & !zavrsnirad.getNaziv().isEmpty() & !zavrsnirad.getOpis().isEmpty()) {
        	service.updateZavrsniRad(zavrsnirad);
        }
		return "redirect:/zavrsnirad/";
	}
	
	@RequestMapping("/brisi/{id}")
	public String brisiZavrsniRad(@PathVariable(name = "id") int id, @AuthenticationPrincipal CustomUserDetails userDetails) {
	    String myRole = userDetails.getRole();
        if (myRole.equals("STUDENT")) {
            return "redirect:/prijava/";
        }
        
        ZavrsniRad zavrsniRad = service.getZavrsniRad(id);
        List<Prijava> prijavaBaza = (List)prijavaService.findPrijavaByNaziv(zavrsniRad.getNaziv());
        if(!prijavaBaza.isEmpty()) prijavaService.deleteAll(prijavaBaza);
        
		service.deleteZavrsniRad(id);
		return "redirect:/zavrsnirad/";		
	}
	
	@RequestMapping("/dodjeli/{naziv}/{email}")
	public String Dodjeli(@PathVariable(name = "naziv") String naziv, @PathVariable(name = "email") String email, @AuthenticationPrincipal CustomUserDetails userDetails) {
	    String myRole = userDetails.getRole();
        if (myRole.equals("STUDENT")) {
            return "redirect:/prijava/";
        }
        
		service.dodjeli(naziv, email);
        //List<Prijava> prijavaBaza = (List)prijavaService.findPrijavaByNaziv(naziv);
		//prijavaService.deleteAll(prijavaBaza);
		return "redirect:/zavrsnirad/";		
	}
	
	@RequestMapping("/oslobodi/{id}")
	public String Oslobodi(@PathVariable(name = "id") int id, @AuthenticationPrincipal CustomUserDetails userDetails) {
	    String myRole = userDetails.getRole();
        if (myRole.equals("STUDENT")) {
            return "redirect:/prijava/";
        }
        
        service.oslobodi(id);
		return "redirect:/zavrsnirad/";		
	}
	
	@RequestMapping("/student/{studentEmail}")
	public String showStudent(@PathVariable(name = "studentEmail") String studentEmail, Model model, @AuthenticationPrincipal CustomUserDetails userDetails) {
	    String myRole = userDetails.getRole();
        if (myRole.equals("STUDENT")) {
            return "redirect:/prijava/";
        }
		
		User student = korisnikService.findUserByEmail(studentEmail);
		model.addAttribute("student", student);
		
		return "student";
	}
	
	@RequestMapping("/dokumentacija")
	public String Dokumentacija(Model model, @AuthenticationPrincipal CustomUserDetails userDetails) {
	    String myRole = userDetails.getRole();
        if (myRole.equals("STUDENT")) {
            return "redirect:/prijava/";
        }
        
		return "uploadForm";		
	}
}
