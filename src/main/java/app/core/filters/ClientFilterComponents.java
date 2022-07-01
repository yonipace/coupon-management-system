package app.core.filters;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import app.core.utilities.JwtUtil;

@Component
public class ClientFilterComponents {

	@Bean
	public FilterRegistrationBean<AdminFilter> adminFilter(JwtUtil jwtUtil) {

		FilterRegistrationBean<AdminFilter> filterRegistrationBean;

		filterRegistrationBean = new FilterRegistrationBean<AdminFilter>(new AdminFilter(jwtUtil));
		filterRegistrationBean.addUrlPatterns("/admin/*");
		return filterRegistrationBean;
	}

	@Bean
	public FilterRegistrationBean<CompanyFilter> companyFilter(JwtUtil jwtUtil) {

		FilterRegistrationBean<CompanyFilter> filterRegistrationBean;

		filterRegistrationBean = new FilterRegistrationBean<>(new CompanyFilter(jwtUtil));
		filterRegistrationBean.addUrlPatterns("/company/*");
		return filterRegistrationBean;
	}

	@Bean
	public FilterRegistrationBean<CustomerFilter> customerFilter(JwtUtil jwtUtil) {

		FilterRegistrationBean<CustomerFilter> filterRegistrationBean;

		filterRegistrationBean = new FilterRegistrationBean<>(new CustomerFilter(jwtUtil));
		filterRegistrationBean.addUrlPatterns("/customer/*");
		return filterRegistrationBean;
	}
}
