package com.hellofresh.controller.tests;

import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import javax.servlet.http.HttpServletRequest;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.internal.verification.VerificationModeFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.hellofresh.config.TestConfig;
import com.hellofresh.controller.DataStreamController;
import com.hellofresh.service.EventService;


@RunWith(SpringRunner.class)
@WebMvcTest(DataStreamController.class)
@ContextConfiguration(classes = TestConfig.class)
@ActiveProfiles("test")
public class DataStreamControllerTest {
	
	 @Autowired
	 private MockMvc mvc;
	 @MockBean
	 EventService eventService;
	 
		@Test
		public void createEvent_validRequest_createsEvent() throws Exception {
			 String sampleRequest = "1607341341814,0.0442672968,1282509067";
			mvc.perform(post("/event").content(sampleRequest)
					.contentType(MediaType.TEXT_PLAIN))
			.andExpect(status().isAccepted());
	        verify(eventService, VerificationModeFactory.times(1))
	        .saveEvents(ArgumentMatchers.any(HttpServletRequest.class));
	        reset(eventService);
		}
		
	 
	@Test
	public void getStats_dataIsPresent_returnsStats() throws Exception {
		 String exectedResponse = "7,1.1345444135,0.1620777734,11824011150,1689144450.000";
		when(eventService.getEventStats()).thenReturn(exectedResponse);
		mvc.perform(get("/stats"))
		.andExpect(status().isOk()).andExpect(MockMvcResultMatchers.content().string(exectedResponse));
        verify(eventService, VerificationModeFactory.times(1)).getEventStats();
        reset(eventService);
	}

	@Test
	public void getStats_dataIsNotPresent_returnsBlankResponse() throws Exception {
		 String exectedResponse = "";
		when(eventService.getEventStats()).thenReturn(exectedResponse);
		mvc.perform(get("/stats"))
		.andExpect(status().isOk()).andExpect(MockMvcResultMatchers.content().string(exectedResponse));
        verify(eventService, VerificationModeFactory.times(1)).getEventStats();
        reset(eventService);
	}
}
