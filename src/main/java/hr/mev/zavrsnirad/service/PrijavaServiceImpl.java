package hr.mev.zavrsnirad.service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import hr.mev.zavrsnirad.exceptions.ResourceNotFoundException;
import hr.mev.zavrsnirad.model.Prijava;
import hr.mev.zavrsnirad.repository.PrijavaRepository;

@Service
@Transactional
public class PrijavaServiceImpl implements PrijavaService {

	@Autowired
	private PrijavaRepository prijavaRepository;

	@Override
	public Prijava createPrijava(Prijava prijava) {
		return prijavaRepository.save(prijava);
	}
	
	@Override
	public Prijava updatePrijava(Prijava dataPrijava) throws ResourceNotFoundException {
		Optional<Prijava> productDb = this.prijavaRepository.findById(dataPrijava.getId());
		if (productDb.isPresent()) {
			Prijava prijavaUpdate = productDb.get();
			prijavaUpdate.setNaziv(dataPrijava.getNaziv());
			prijavaUpdate.setEmail(dataPrijava.getEmail());
			prijavaRepository.save(prijavaUpdate);
			return prijavaUpdate;
		} else {
			throw new ResourceNotFoundException("Zapis nije pronađen : " + dataPrijava.getId());
		}
	}

	@Override
	public Iterable<Prijava> getAllPrijava() {
		return this.prijavaRepository.findAll();
	}
	
	@Override
	public Iterable<Prijava> findPrijavaByEmail(String email) {
		return this.prijavaRepository.odrediPrijavaPremaEmail(email);
	}
	
	@Override
	public Iterable<Prijava> findPrijavaByNaziv(String naziv) {
		return this.prijavaRepository.odrediPrijavaPremaNazivCol(naziv);
	}
	
	@Override
	public Prijava getPrijava(long id) {

		if (id == 0)
			return new Prijava();
		Optional<Prijava> productDb = this.prijavaRepository.findById(id);

		if (productDb.isPresent()) {
			return productDb.get();
		} else {
			return new Prijava();
		}
	}
	
	@Override
	public void deletePrijava(long id) {
		Optional<Prijava> productDb = this.prijavaRepository.findById(id);

		if (productDb.isPresent()) {
			this.prijavaRepository.delete(productDb.get());
		} else {
			throw new ResourceNotFoundException("Zapis nije pronađen.");
		}
	}
	
	@Override
	public void deleteAll(List<Prijava> prijavaToDelete) {
		this.prijavaRepository.deleteAll(prijavaToDelete);
	}
}
