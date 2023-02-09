package com.example.assignment1;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;
import com.example.assignment1.entity.UserInfo;
import com.example.assignment1.repository.UserRepository;
import com.example.assignment1.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
@ContextConfiguration(classes = { WebappApplicationTests.class })
class WebappApplicationTests {

	@Test
	public void Test() {
		assertTrue("Hello".equals("Hello"));
	}

	@InjectMocks
	private UserService service;

	@Mock
	private UserRepository repository;

	@Test
	public void saveUserTest() {
		UserInfo user = new UserInfo();
		user.setId(Long.valueOf(100));
		user.setFirstName("karthik");
		user.setLastName("P");
		user.setPassword("1234");
		user.setUsername("a1100@dddfgii.com");
		String username = "a1100@dddfgii.com";
		when(repository.findByUsername(username)).thenReturn(user);
		assertEquals(user.getUsername(), service.loadUserByUsername(username).getUsername());
	}
}
