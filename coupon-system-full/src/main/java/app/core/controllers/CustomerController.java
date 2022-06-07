package app.core.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import app.core.entities.Coupon;
import app.core.entities.Coupon.Category;
import app.core.entities.Customer;
import app.core.exceptions.CouponSystemException;
import app.core.services.CustomerService;
import app.core.utilities.JwtUtil;

@RestController
@RequestMapping("/customer")
@CrossOrigin
public class CustomerController {

	@Autowired
	private CustomerService customerService;
	@Autowired
	private JwtUtil jwtUtil;

	@PostMapping("/purchase")
	public Coupon purchaseCoupon(@RequestBody Coupon coupon, @RequestHeader String token) {

		try {
			int id = jwtUtil.extractId(token);

			return customerService.purchaseCoupon(coupon, id);
		} catch (CouponSystemException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}

	@GetMapping("/coupons")
	public List<Coupon> getAllCustomerCoupons(@RequestHeader String token) {

		try {
			int id = jwtUtil.extractId(token);

			return customerService.getCustomerCoupons(id);
		} catch (CouponSystemException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}

	@GetMapping("/coupons/category")
	public List<Coupon> getAllCustomerCoupons(@RequestParam Category category, @RequestHeader String token) {

		try {
			int id = jwtUtil.extractId(token);

			return customerService.getCustomerCoupons(category, id);
		} catch (CouponSystemException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}

	@GetMapping("/coupons/price")
	public List<Coupon> getAllCustomerCoupons(@RequestParam double price, @RequestHeader String token) {

		try {
			int id = jwtUtil.extractId(token);

			return customerService.getCustomerCoupons(price, id);
		} catch (CouponSystemException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}

	@GetMapping("/details")
	public Customer getDetails(@RequestHeader String token) {

		try {
			int id = jwtUtil.extractId(token);

			return customerService.getCustomerDetails(id);
		} catch (CouponSystemException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}

	@GetMapping("/all-coupons")
	public List<Coupon> getAllCoupons() throws CouponSystemException {
		return this.customerService.getAllCoupons();

	}
}
