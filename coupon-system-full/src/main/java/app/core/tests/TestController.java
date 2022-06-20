package app.core.tests;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import app.core.entities.Coupon;
import app.core.exceptions.CouponSystemException;
import app.core.services.CustomerService;

@RestController
@RequestMapping("/test")
@CrossOrigin
public class TestController {

	@Autowired
	private TestService testService;

	@Autowired
	private CustomerService customerService;

	@GetMapping("/getCoupons")
	public List<Coupon> getALLCoupons() {

		return testService.getAllCoupons();

	}

	@PostMapping("/addCoupon")
	public Coupon addCoupon(@RequestBody Coupon coupon) {

		return testService.addCoupon(coupon);

	}

	@PutMapping("/purchase")
	public Coupon purchaseCoupon(@RequestBody Coupon coupon) {

		try {
			return customerService.purchaseCoupon(coupon, 1);
		} catch (CouponSystemException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}

	}

	@GetMapping("/getCustomerCoupons")
	public List<Coupon> getCustomerCoupons() {

		try {
			return customerService.getCustomerCoupons(1);
		} catch (CouponSystemException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}

	}
}
