package hr.mev.zavrsnirad.repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import hr.mev.zavrsnirad.model.Prijava;
import hr.mev.zavrsnirad.model.User;

public interface PrijavaRepository extends JpaRepository<Prijava, Long> {
	@Query(
			  value = "SELECT *  FROM prijava p WHERE p.email = ?1 ORDER BY p.id", 
			  nativeQuery = true)
	Collection<Prijava> odrediPrijavaPremaEmail(String email);
	
	@Query(
			  value = "SELECT *  FROM prijava p WHERE p.naziv = ?1 ORDER BY p.id", 
			  nativeQuery = true)
	Optional<Prijava> odrediPrijavaPremaNaziv(String naziv);
	
	@Query(
			  value = "SELECT *  FROM prijava p WHERE p.naziv = ?1 ORDER BY p.id", 
			  nativeQuery = true)
	Collection<Prijava> odrediPrijavaPremaNazivCol(String naziv);
}
