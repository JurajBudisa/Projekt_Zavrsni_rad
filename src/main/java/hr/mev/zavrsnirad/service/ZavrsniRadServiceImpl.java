package hr.mev.zavrsnirad.service;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import hr.mev.zavrsnirad.exceptions.ResourceNotFoundException;
import hr.mev.zavrsnirad.model.User;
import hr.mev.zavrsnirad.model.ZavrsniRad;
import hr.mev.zavrsnirad.model.Prijava;
import hr.mev.zavrsnirad.repository.ZavrsniRadRepository;
import hr.mev.zavrsnirad.repository.PrijavaRepository;

@Service
@Transactional
public class ZavrsniRadServiceImpl implements ZavrsniRadService {

	@Autowired
	private ZavrsniRadRepository zavrsniradRepository;
	
	@Autowired
	private PrijavaRepository prijavaRepository;

	@Override
	public ZavrsniRad createZavrsniRad(ZavrsniRad zavrsnirad) {
		zavrsnirad.setDatum(LocalDate.now());
		return zavrsniradRepository.save(zavrsnirad);
	}
	
	@Override
	public ZavrsniRad updateZavrsniRad(ZavrsniRad dataZavrsniRad) throws ResourceNotFoundException {
		Optional<ZavrsniRad> productDb = this.zavrsniradRepository.findById(dataZavrsniRad.getId());
		if (productDb.isPresent()) {
			ZavrsniRad zavrsniradUpdate = productDb.get();
			Optional<Prijava> prijava = this.prijavaRepository.odrediPrijavaPremaNaziv(zavrsniradUpdate.getNaziv());
			if (prijava.isPresent()) {
				Prijava prijavaUpdate = prijava.get();
				prijavaUpdate.setNaziv(dataZavrsniRad.getNaziv());
			}
			
			zavrsniradUpdate.setNaziv(dataZavrsniRad.getNaziv());
			zavrsniradUpdate.setOpis(dataZavrsniRad.getOpis());
			zavrsniradUpdate.setGodina(dataZavrsniRad.getGodina());
			zavrsniradUpdate.setStudij(dataZavrsniRad.getStudij());
			zavrsniradUpdate.setStatus(dataZavrsniRad.getStatus());
			zavrsniradRepository.save(zavrsniradUpdate);
			return zavrsniradUpdate;
		} else {
			throw new ResourceNotFoundException("Zapis nije pronađen : " + dataZavrsniRad.getId());
		}
	}

	@Override
	public Iterable<ZavrsniRad> getAllZavrsniRad() {
		return this.zavrsniradRepository.findAll();
	}
	
	@Override
	public Iterable<ZavrsniRad> findZavrsniRadByNaziv(String naziv) {
		return this.zavrsniradRepository.odrediZavrsniRadPremaNazivCol(naziv);
	}
	
	@Override
	public Iterable<ZavrsniRad> findZavrsniRadByStatus(String status, String godina, String studij) {
		return this.zavrsniradRepository.odrediZavrsniRadPremaStatus(status, godina, studij);
	}
	
	@Override
	public Iterable<ZavrsniRad> findZavrsniRadByStudent(String student) {
		return this.zavrsniradRepository.odrediZavrsniRadPremaStudent(student);
	}
	
	@Override
	public Iterable<ZavrsniRad> findDodijeljen() {
		return this.zavrsniradRepository.findDodijeljen();
	}
	
	@Override
	public ZavrsniRad getZavrsniRad(long id) {

		if (id == 0)
			return new ZavrsniRad();
		Optional<ZavrsniRad> productDb = this.zavrsniradRepository.findById(id);

		if (productDb.isPresent()) {
			return productDb.get();
		} else {
			return new ZavrsniRad();
		}
	}
	
	@Override
    public ZavrsniRad findByNaziv(String naziv) {
        return this.zavrsniradRepository.findByNaziv(naziv);
    }
	
	@Override
	public void deleteZavrsniRad(long id) {
		Optional<ZavrsniRad> productDb = this.zavrsniradRepository.findById(id);

		if (productDb.isPresent()) {
			this.zavrsniradRepository.delete(productDb.get());
		} else {
			throw new ResourceNotFoundException("Zapis nije pronađen.");
		}

	}
	
	@Override
	public void deleteZavrsniRadByNaziv(String naziv) {
		Optional<ZavrsniRad> productDb = this.zavrsniradRepository.odrediZavrsniRadPremaNaziv(naziv);

		if (productDb.isPresent()) {
			this.zavrsniradRepository.delete(productDb.get());
		} else {
			throw new ResourceNotFoundException("Zapis nije pronađen.");
		}

	}
	
	@Override
	public void dodjeli(String naziv, String email) throws ResourceNotFoundException {
		Optional<ZavrsniRad> productDb = this.zavrsniradRepository.odrediZavrsniRadPremaNaziv(naziv);
		
		if (productDb.isPresent()) {
			ZavrsniRad zavrsniradUpdate = productDb.get();
			zavrsniradUpdate.setStatus("Dodijeljen");
			zavrsniradUpdate.setStudent(email);
			zavrsniradRepository.save(zavrsniradUpdate);
		} else {
			throw new ResourceNotFoundException("Zapis nije pronađen.");
		}
	}
	
	@Override
	public void oslobodi(long id) throws ResourceNotFoundException {
		Optional<ZavrsniRad> productDb = this.zavrsniradRepository.findById(id);
		
		if (productDb.isPresent()) {
			ZavrsniRad zavrsniradUpdate = productDb.get();
			zavrsniradUpdate.setStatus("Slobodan");
			zavrsniradUpdate.setStudent(null);
			zavrsniradRepository.save(zavrsniradUpdate);
		} else {
			throw new ResourceNotFoundException("Zapis nije pronađen.");
		}
	}
	
}
