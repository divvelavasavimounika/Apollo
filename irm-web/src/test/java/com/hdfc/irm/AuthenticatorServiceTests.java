package com.hdfc.irm;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.hdfc.irm.engine.service.RestUtilService;
import com.hdfc.irm.entities.LoginRequestEntity;
import com.hdfc.irm.repository.LoginRepository;
import com.hdfc.irm.web.exceptions.IRMAuthenticateException;
import com.hdfc.irm.web.model.AuthenticateRequest;
import com.hdfc.irm.web.model.AuthenticateResponse;
import com.hdfc.irm.web.service.AuthenticatorService;

@SpringBootTest
@RunWith(SpringRunner.class)
public class AuthenticatorServiceTests {

	@InjectMocks
	@Autowired
	AuthenticatorService authenticatorService;

	@Mock
	RestUtilService restUtilService;
	@Mock
	LoginRepository loginRepo;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void authenticate_Test() throws IRMAuthenticateException {
		AuthenticateRequest request = new AuthenticateRequest();
		request.setUserid("10793");
		request.setPassword("HDFC@123");
		AuthenticateResponse authResp = new AuthenticateResponse();
		authResp.setUsertype("E");

		when(loginRepo.save(any(LoginRequestEntity.class))).thenReturn(new LoginRequestEntity());
		when(restUtilService.callRestService(any(Object.class), any(Class.class), any(String.class)))
				.thenReturn(authResp);
		AuthenticateResponse response = authenticatorService.authenticate(request);
		assertThat(response).isNotNull();

	}
}
