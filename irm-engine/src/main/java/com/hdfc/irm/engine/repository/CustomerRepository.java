package com.hdfc.irm.engine.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.hdfc.irm.engine.entities.CustomerDetails;
import com.hdfc.irm.engine.utils.ApplicationProperties;

//ExternalDB
@Repository
public class CustomerRepository {

	@Autowired
	@Qualifier("extJdbcTemplate")
	private JdbcTemplate jdbcTemplate;

	@Autowired
	private ApplicationProperties properties;

	public CustomerDetails findByCustomerPolicyId(String policyId) {
		return jdbcTemplate.queryForObject(properties.getCustomerDetatilsQuery(), new Object[] { policyId },
				(rs, rownum) -> new CustomerDetails(rs.getString("CUSTOMER_NAME"), rs.getString("IFSC_CODE"),
						rs.getString("BANK_ACCOUNT_NUMBER"), rs.getString("POLICY_NUMBER")));
	}
}
