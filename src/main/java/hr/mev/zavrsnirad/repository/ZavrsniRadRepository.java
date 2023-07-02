package hr.mev.zavrsnirad.repository;

import java.util.Collection;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import hr.mev.zavrsnirad.model.User;
import hr.mev.zavrsnirad.model.ZavrsniRad;

public interface ZavrsniRadRepository extends JpaRepository<ZavrsniRad, Long> {
	@Query(
			  value = "SELECT *  FROM zavrsnirad z WHERE z.status = ?1 AND z.godina = ?2 AND z.studij = ?3 ORDER BY z.id", 
			  nativeQuery = true)
	Collection<ZavrsniRad> odrediZavrsniRadPremaStatus(String status, String godina, String studij);

	@Query(
			  value = "SELECT *  FROM zavrsnirad z WHERE z.naziv = ?1 ORDER BY z.id", 
			  nativeQuery = true)
	Optional<ZavrsniRad> odrediZavrsniRadPremaNaziv(String naziv);
	@Query(
			  value = "SELECT *  FROM zavrsnirad z WHERE z.naziv = ?1 ORDER BY z.id", 
			  nativeQuery = true)
	Collection<ZavrsniRad> odrediZavrsniRadPremaNazivCol(String naziv);
	
	ZavrsniRad findByNaziv(String naziv);
	
	@Query(
			  value = "SELECT *  FROM zavrsnirad z WHERE z.student = ?1 ORDER BY z.id", 
			  nativeQuery = true)
	Collection<ZavrsniRad> odrediZavrsniRadPremaStudent(String student);
	
	@Query(
			  value = "SELECT *  FROM zavrsnirad z WHERE z.status = 'Dodijeljen' ORDER BY z.id", 
			  nativeQuery = true)
	Collection<ZavrsniRad> findDodijeljen();
}