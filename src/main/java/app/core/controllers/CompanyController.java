package app.core.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import app.core.entities.Company;
import app.core.entities.Coupon;
import app.core.entities.Coupon.Category;
import app.core.exceptions.CouponSystemException;
import app.core.services.CompanyService;
import app.core.utilities.JwtUtil;

@RestController
@RequestMapping("/company")
@CrossOrigin
public class CompanyController {

	@Autowired
	private CompanyService companyService;
	@Autowired
	private JwtUtil jwtUtil;

	@PostMapping("/coupons")
	public Coupon addCoupon(@RequestBody Coupon coupon, @RequestHeader String token) {

		try {
			int id = jwtUtil.extractId(token);

			return this.companyService.addCoupon(coupon, id);
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}

	@PutMapping("/coupons")
	public Coupon updateCoupon(@RequestBody Coupon coupon, @RequestHeader String token) {

		try {
			int id = jwtUtil.extractId(token);

			return this.companyService.updateCoupon(coupon, id);
		} catch (CouponSystemException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}

	}

	@DeleteMapping("/coupons/{couponId}")
	public void deleteCoupon(@PathVariable int couponId, @RequestHeader String token) {

		try {
			int id = jwtUtil.extractId(token);

			this.companyService.deleteCoupon(couponId, id);
		} catch (CouponSystemException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}

	}

	@GetMapping("/coupons/{id}")
	public Coupon getOneCoupon(@PathVariable int couponId, @RequestHeader String token) {

		try {
			int id = jwtUtil.extractId(token);

			return this.companyService.getOneCoupon(couponId, id);
		} catch (CouponSystemException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}

	@GetMapping("/coupons")
	public List<Coupon> getAllCompanyCoupons(@RequestHeader String token) {

		try {
			int id = jwtUtil.extractId(token);

			return this.companyService.getCompanyCoupons(id);
		} catch (CouponSystemException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}

	@GetMapping("/coupons/category")
	public List<Coupon> getAllCompanyCouponsByCategory(@RequestParam Category category, @RequestHeader String token) {

		try {
			int id = jwtUtil.extractId(token);

			return this.companyService.getCompanyCoupons(category, id);
		} catch (CouponSystemException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}

	@GetMapping("/coupons/price")
	public List<Coupon> getAllCompanyCouponsUpToPrice(@RequestParam double price, @RequestHeader String token) {

		try {
			int id = jwtUtil.extractId(token);

			return this.companyService.getCompanyCoupons(price, id);
		} catch (CouponSystemException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}

	}

	@GetMapping("/details")
	public Company getDetails(@RequestHeader String token) {

		try {
			int id = jwtUtil.extractId(token);

			return companyService.getCompanyDetails(id);
		} catch (CouponSystemException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}

}
