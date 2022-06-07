package app.core.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = "coupons")
@Table(name = "companies")
@Entity
public class Company {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String name;
	private String email;
	private String password;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "company")
	@JsonIgnore
	private List<Coupon> coupons;

	// CTOR
	public Company(String name, String email, String password) {

		this.id = 0;
		this.name = name;
		this.email = email;
		this.password = password;
		this.coupons = null;

	}
	// make sure company attribute is filled right when adding or setting coupons

	public void addCoupons(Coupon... coupons) {

		if (this.coupons == null) {
			this.coupons = new ArrayList<Coupon>();
		}
		for (Coupon coupon : coupons) {
			coupon.setCompany(this);
			this.coupons.add(coupon);
		}
	}

	public void setCoupons(List<Coupon> coupons) {

		for (Coupon coupon : coupons) {
			coupon.setCompany(this);
		}
		this.coupons = coupons;
	}

}
