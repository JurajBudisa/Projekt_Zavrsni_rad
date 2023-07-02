package hr.mev.zavrsnirad.service;

import java.util.List;

import hr.mev.zavrsnirad.model.Prijava;

public interface PrijavaService {
	
	Prijava createPrijava(Prijava Prijava);

	Prijava updatePrijava(Prijava Prijava);

	Iterable<Prijava> getAllPrijava();
	
	Iterable<Prijava> findPrijavaByEmail(String email);
	
	Iterable<Prijava> findPrijavaByNaziv(String naziv);
	
	Prijava getPrijava(long id);

	void deletePrijava(long id);
	
	void deleteAll(List<Prijava> prijavaToDelete);
}
