package guru.sfg.brewery.repositories.security;

import guru.sfg.brewery.security.Authority;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


public interface AuthorityRepository extends JpaRepository<Authority, Long> {
}
