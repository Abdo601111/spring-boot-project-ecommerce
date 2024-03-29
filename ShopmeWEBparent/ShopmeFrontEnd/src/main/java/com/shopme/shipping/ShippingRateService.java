package com.shopme.shipping;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shopme.common.entity.Address;
import com.shopme.common.entity.Customer;
import com.shopme.common.entity.ShippingRate;

@Service
public class ShippingRateService {
	@Autowired
	private ShippingRateRepository repo;
	
	public ShippingRate getByCountryAndState(Customer customer) {
		String state = customer.getState();
		if(state == null || state.isEmpty()) {
			state = customer.getSity();
		}
		return repo.findByCountryAndState(customer.getCountry(), state);
	}
	
	public ShippingRate getForAddress(Address address) {
		String state = address.getState();
		if(state == null || state.isEmpty()) {
			state = address.getCity();
		}
		return repo.findByCountryAndState(address.getCountry(), state);
	}
	

}
