package hr.mev.zavrsnirad.service;

import hr.mev.zavrsnirad.model.ZavrsniRad;

public interface ZavrsniRadService {
	
	ZavrsniRad createZavrsniRad(ZavrsniRad ZavrsniRad);

	ZavrsniRad updateZavrsniRad(ZavrsniRad ZavrsniRad);

	Iterable<ZavrsniRad> getAllZavrsniRad();
	
	Iterable<ZavrsniRad> findZavrsniRadByNaziv(String naziv);
	
	Iterable<ZavrsniRad> findZavrsniRadByStatus(String status, String godina, String studij);
	
	Iterable<ZavrsniRad> findZavrsniRadByStudent(String student);
	
	Iterable<ZavrsniRad> findDodijeljen();
	
	ZavrsniRad getZavrsniRad(long id);
	
	ZavrsniRad findByNaziv(String naziv);
	
	void deleteZavrsniRad(long id);
	
	void deleteZavrsniRadByNaziv(String naziv);
	
	void dodjeli(String naziv, String email);
	
	void oslobodi(long id);
}
