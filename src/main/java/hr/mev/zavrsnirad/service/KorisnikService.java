package hr.mev.zavrsnirad.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import hr.mev.zavrsnirad.model.User;
import hr.mev.zavrsnirad.repository.KorisnikRepository;

import java.util.Arrays;
import java.util.HashSet;

@Service("korisnikService")
public class KorisnikService {

	private KorisnikRepository korisnikRepository;
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Autowired
    public KorisnikService(KorisnikRepository korisnikRepository,
                       BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.korisnikRepository = korisnikRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public User findUserByEmail(String email) {
        return korisnikRepository.findByEmail(email);
    }

    public User saveProfesor(User korisnik) {
    	korisnik.setPassword(bCryptPasswordEncoder.encode(korisnik.getPassword()));
        korisnik.setRole("PROFESOR");
        return korisnikRepository.save(korisnik);
    }
    
    public User saveStudent(User korisnik) {
    	korisnik.setPassword(bCryptPasswordEncoder.encode(korisnik.getPassword()));
        korisnik.setRole("STUDENT");
        return korisnikRepository.save(korisnik);
    }
}
