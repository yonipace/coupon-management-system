package app.core.tests;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import app.core.entities.Coupon;
import app.core.services.ClientService;

@Service
@Transactional
public class TestService extends ClientService {

	@Override
	public int login(String email, String password) {
		return 0;
	}

	public List<Coupon> getAllCoupons() {

		return couponRepo.findAll();
	}

	public Coupon addCoupon(Coupon coupon) {

		return couponRepo.save(coupon);

	}

}
