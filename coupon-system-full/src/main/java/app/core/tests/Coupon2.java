package app.core.tests;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import app.core.entities.Company;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = { "company", "customers" })
@Table(name = "coupons")
@Entity
public class Coupon2 {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Enumerated(EnumType.STRING)
	private Category category;

	private String title;
	private String description;
	private LocalDate startDate;
	private LocalDate endDate;
	private int amount;
	private double price;
	private String image;

	// each coupon is mapped to a specific company
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "company_id")
	private Company company;

	public Coupon2(Category category, String title, String description, LocalDate startDate, LocalDate endDate,
			int amount, double price, Company company) {
		super();
		this.category = category;
		this.title = title;
		this.description = description;
		this.startDate = startDate;
		this.endDate = endDate;
		this.amount = amount;
		this.price = price;
		this.company = company;
		this.image = null;
	}

	public enum Category {

		ELECTRICITY, FOOD, RESTAURANT, VACATION

	}

}
