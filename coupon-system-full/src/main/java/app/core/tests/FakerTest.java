package app.core.tests;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import app.core.entities.Company;
import app.core.entities.Coupon;
import app.core.entities.Customer;

//@Component
public class FakerTest {

	public static void main(String[] args) {

		FakerUtil fakerUtil = new FakerUtil();

		List<Company> companies = new ArrayList<>();

		companies.add(fakerUtil.generateRandomCompany());
		companies.add(fakerUtil.generateRandomCompany());
		companies.add(fakerUtil.generateRandomCompany());

		companies.stream().forEach(e -> System.out.println(e));

		System.out.println("=============================");

		List<Customer> customers = new ArrayList<>();

		customers.add(fakerUtil.generateRandomCustomer());
		customers.add(fakerUtil.generateRandomCustomer());
		customers.add(fakerUtil.generateRandomCustomer());

//		customers.stream().forEach(e -> System.out.println(e));

		System.out.println("=============================");

		List<Coupon> coupons = new ArrayList<>();

		coupons.add(fakerUtil.generateRandomCoupon());
		coupons.add(fakerUtil.generateRandomCoupon());
		coupons.add(fakerUtil.generateRandomCoupon());
		coupons.add(fakerUtil.generateRandomCoupon());
		coupons.add(fakerUtil.generateRandomCoupon());
		coupons.add(fakerUtil.generateRandomCoupon());

		ObjectMapper mapper = new ObjectMapper();
		mapper.registerModule(new JavaTimeModule());
		mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

		coupons.stream().forEach(e -> {
			try {
				System.out.println(mapper.writeValueAsString(e));
			} catch (JsonProcessingException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});

	}

}
