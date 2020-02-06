package com.hdfc.irm.repository;


import static org.junit.Assert.*;

import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.hdfc.irm.entities.LoginRequestEntity;



@RunWith(SpringRunner.class)
@DataJpaTest
public class LoginRepositoryTests {

	
	@Autowired
	private LoginRepository loginRepository;
	
	
	@Test
    public void testSaveLoginRepository() {
		
		LoginRequestEntity loginReq = new LoginRequestEntity();	
		loginReq.setUserid("testid");
		loginReq.setPassword("dummy");
		loginReq.setSource("msd");
		loginReq.setDevice_id("test141");
		loginReq.setBuild_version_code("5.9");
		loginReq.setChannel_id("XYZ Bank");
		loginReq.setOs("iOS");		
		loginReq.setTime(null);			
		loginRepository.save(loginReq);			
		assertNotNull(loginReq);	
		Optional<LoginRequestEntity> databaseObj=loginRepository.findById(loginReq.getId());		
		assertEquals(databaseObj.get().getId(),loginReq.getId());		
		loginRepository.deleteById(loginReq.getId());			
	}
	
	@Test
    public void testFindAllLoginRepository() {
		
		LoginRequestEntity request = new LoginRequestEntity();	
		request.setUserid("sampleid");
		request.setPassword("temp");
		request.setSource("msd");
		request.setDevice_id("test123");
		request.setBuild_version_code("5.9");
		request.setChannel_id("ABC Bank");
		request.setOs("iOS");		
		request.setTime(null);			
		loginRepository.save(request);		
        assertNotNull(loginRepository.findAll());         
        List<LoginRequestEntity> list=loginRepository.findAll();   
        assertEquals(list.get(0).getUserid(),request.getUserid());
        assertEquals(list.get(0).getSource(),request.getSource());
        assertEquals(list.get(0).getDevice_id(),request.getDevice_id());
        assertEquals(list.get(0).getOs(),request.getOs());       
        loginRepository.deleteById(request.getId());
    }
	
	
	
	
}
