package app.core.filters;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import app.core.login.LoginManagerInterface.ClientType;
import app.core.utilities.JwtUtil;

@Component
public class ClientFilterComponents {

	private FilterRegistrationBean<ClientFilter> filterRegistrationBean;

	@Bean
	public FilterRegistrationBean<ClientFilter> adminFilter(JwtUtil jwtUtil) {

		this.filterRegistrationBean = new FilterRegistrationBean<>(new ClientFilter(jwtUtil, ClientType.ADMIN));
		filterRegistrationBean.addUrlPatterns("/admin/*");
		return filterRegistrationBean;
	}

	@Bean
	public FilterRegistrationBean<ClientFilter> companyFilter(JwtUtil jwtUtil) {

		this.filterRegistrationBean = new FilterRegistrationBean<>(new ClientFilter(jwtUtil, ClientType.COMPANY));
		filterRegistrationBean.addUrlPatterns("/company/*");
		return filterRegistrationBean;
	}

	@Bean
	public FilterRegistrationBean<ClientFilter> customerFilter(JwtUtil jwtUtil) {

		this.filterRegistrationBean = new FilterRegistrationBean<>(new ClientFilter(jwtUtil, ClientType.CUSTOMER));
		filterRegistrationBean.addUrlPatterns("/customer/*");
		return filterRegistrationBean;
	}
}
