package hr.mev.zavrsnirad.repository;

import hr.mev.zavrsnirad.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("userRepository")
public interface KorisnikRepository extends JpaRepository<User, Long> {
	User findByEmail(String email);
}
