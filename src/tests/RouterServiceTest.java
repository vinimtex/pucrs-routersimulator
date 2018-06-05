package tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import services.RouterService;

class RouterServiceTest {

	@Test
	void testValidateMessage() {
		String message = RouterService.sanatizeMessage("*192.168.1.2;1*192.168.1.3;1");
		assertTrue(RouterService.validateMessage(message));
	}
	
	@Test
	void testValidateMessageOneTuple() {
		String message = RouterService.sanatizeMessage("*192.168.1.2;1");
		
		assertTrue(RouterService.validateMessage(message));
		
	}
	
	@Test
	void testValidateMessageNoMetric() {
		String message = RouterService.sanatizeMessage("*192.168.1.21");
		
		assertFalse(RouterService.validateMessage(message));
		
	}
	
	@Test
	void testValidateMessageNoTupleSeparator() {
		String message = RouterService.sanatizeMessage("*192.168.1.2;1192.168.1.2;1");
		
		assertFalse(RouterService.validateMessage(message));
		
	}
	
	@Test
	void testValidateMessageInvalidIP() {
		String message = RouterService.sanatizeMessage("*168.1.2;1*192.168.1.2;1");
		
		assertFalse(RouterService.validateMessage(message));
		
	}
	
	@Test
	void testValidateMessageInvalidIPOutbound() {
		String message = RouterService.sanatizeMessage("*256.168.1.2;1*192.168.1.2;1");
		
		assertFalse(RouterService.validateMessage(message));
		
	}
	
	@Test
	void testValidateMessageInvalidMetric() {
		String message = RouterService.sanatizeMessage("*192.168.1.2;-10*192.168.1.2;1");
		
		assertFalse(RouterService.validateMessage(message));
		
	}

}
