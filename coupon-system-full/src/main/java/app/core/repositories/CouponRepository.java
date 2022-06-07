package app.core.repositories;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import app.core.entities.Coupon;
import app.core.entities.Coupon.Category;

public interface CouponRepository extends JpaRepository<Coupon, Integer> {

	// ***company coupon methods***

	boolean existsByCompanyIdAndTitle(int companyId, String title);

	boolean existsByIdAndCompanyId(int id, int companyId);

	Optional<Coupon> findByIdAndCompanyId(int id, int companyId);

	List<Coupon> findAllByCompanyId(int companyId);

	List<Coupon> findAllByCompanyIdAndCategory(int companyId, Category category);

	List<Coupon> findAllByCompanyIdAndPriceLessThanEqual(int companyId, double price);

	// ***customer coupon methods***

	boolean existsByIdAndCustomersId(int id, int customerId);

	List<Coupon> findAllByCustomersId(int customerId);

	List<Coupon> findAllByCustomersIdAndCategory(int customerId, Category category);

	List<Coupon> findAllByCustomersIdAndPriceLessThanEqual(int customerId, double price);

	// ***daily job method***

	void deleteByEndDateBefore(LocalDate now);

}
