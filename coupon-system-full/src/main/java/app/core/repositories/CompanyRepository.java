package app.core.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import app.core.entities.Company;

public interface CompanyRepository extends JpaRepository<Company, Integer> {

	Optional<Company> findByEmailAndPassword(String email, String password);

	boolean existsByEmail(String email);

	boolean existsByName(String name);
}
