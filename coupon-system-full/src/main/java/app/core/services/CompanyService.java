package app.core.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import app.core.entities.Company;
import app.core.entities.Coupon;
import app.core.entities.Coupon.Category;
import app.core.exceptions.CouponSystemException;

@Service
@Transactional
public class CompanyService extends ClientService {

	@Override
	public int login(String email, String password) {

		Optional<Company> opt = this.companyRepo.findByEmailAndPassword(email, password);

		return opt.isPresent() ? opt.get().getId() : -1;
	}

	public Coupon addCoupon(Coupon coupon, int id) throws CouponSystemException {

		// each coupon of the company must have a unique title
		if (couponRepo.existsByCompanyIdAndTitle(id, coupon.getTitle())) {
			throw new CouponSystemException("failed to add coupon - coupon: " + coupon.getTitle() + " already exists");
		}
		System.out.printf("coupon %d added successfully \n", coupon.getId());
		coupon.setCompany(getCompanyDetails(id));
		return couponRepo.save(coupon);
	}

	public Coupon updateCoupon(Coupon coupon, int id) throws CouponSystemException {

		// make sure coupon exists and belongs to company before updating
		if (couponRepo.existsByIdAndCompanyId(coupon.getId(), id)) {
			// companyId can not be changed when updating
			coupon.setCompany(getCompanyDetails(id));
			// update coupon
			System.out.printf("coupon %d updated successfully \n", coupon.getId());
			return couponRepo.save(coupon);
		} else {
			throw new CouponSystemException("failed to update coupon - coupon " + coupon.getId() + " can not be found");
		}

	}

	public void deleteCoupon(int couponId, int id) throws CouponSystemException {

		// make sure coupon exists and belongs to company
		if (couponRepo.existsByIdAndCompanyId(couponId, id)) {
			// when coupon is deleted the purchases are deleted with it (cascade)
			couponRepo.deleteById(couponId);
			System.out.printf("coupon %d deleted successfully \n", couponId);

		} else {
			throw new CouponSystemException("failed to delete coupon - coupon " + couponId + " can not be found");
		}
	}

	public List<Coupon> getCompanyCoupons(int id) throws CouponSystemException {

		return couponRepo.findAllByCompanyId(id);

	}

	public List<Coupon> getCompanyCoupons(Category category, int id) throws CouponSystemException {

		return couponRepo.findAllByCompanyIdAndCategory(id, category);

	}

	public List<Coupon> getCompanyCoupons(double maxPrice, int id) throws CouponSystemException {

		return couponRepo.findAllByCompanyIdAndPriceLessThanEqual(id, maxPrice);

	}

	public Company getCompanyDetails(int id) throws CouponSystemException {

		return companyRepo.findById(id).orElseThrow(

				() -> new CouponSystemException("failed to get company " + id));

	}

	public Coupon getOneCoupon(int couponId, int id) throws CouponSystemException {

		return couponRepo.findByIdAndCompanyId(couponId, id).orElseThrow(

				() -> new CouponSystemException("failed to get coupon - coupon " + couponId + " does not exist"));
	}

}
