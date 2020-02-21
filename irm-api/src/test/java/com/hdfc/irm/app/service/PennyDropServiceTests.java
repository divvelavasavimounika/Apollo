package com.hdfc.irm.app.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.hdfc.irm.app.exception.PennyDropApiException;
import com.hdfc.irm.engine.entities.PennyDropRequestEntity;
import com.hdfc.irm.engine.repository.AuditPennyDropRepository;
import com.hdfc.irm.engine.service.RestUtilService;
import com.hdfc.irm.engine.utils.IrmUtils;

@SpringBootTest
@RunWith(SpringRunner.class)
public class PennyDropServiceTests {

	@InjectMocks
	@Autowired
	PennyDropService service;

	@Mock
	RestUtilService restService;
	@Mock
	AuditPennyDropRepository repository;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
	}

	@After
	public void tearDown() throws Exception {
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testCallPennyDropApi() throws IOException, PennyDropApiException {
		String response = IrmUtils.readFromFile("src/test/java/testconfig/PennyResponse.json");
		when(repository.save(any(PennyDropRequestEntity.class))).thenReturn(new PennyDropRequestEntity());
		when(restService.callRestService(any(Object.class), any(Class.class), any(String.class))).thenReturn(response);
		String name = service.callPennyDropApi("ifsc", "accountNumber", "requestId");
		assertThat(name).isNotEmpty();

	}

	@Test
	public void testRetriveCustomerName() throws IOException, PennyDropApiException {
		PennyDropRequestEntity requestEntity = new PennyDropRequestEntity();
		String response = IrmUtils.readFromFile("src/test/java/testconfig/PennyResponse.json");
		service.retriveCustomerName(requestEntity, response);
		assertThat(requestEntity.getTxnErrorCode()).isNotEmpty();
		assertThat(requestEntity.getTxnErrorDesc()).isNotEmpty();
		assertThat(requestEntity.getCustomerName()).isNotEmpty();
	}

}
