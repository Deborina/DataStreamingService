package com.hellofresh.service.tests;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.DelegatingServletInputStream;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import com.hellofresh.config.TestConfig;
import com.hellofresh.dao.DataStreamingDao;
import com.hellofresh.exceptions.ApplicationException;
import com.hellofresh.model.Event;
import com.hellofresh.model.EventStatsResponse;
import com.hellofresh.service.EventServiceImpl;


@RunWith(SpringRunner.class)
@ActiveProfiles("test")
@ContextConfiguration(classes = TestConfig.class)
public class EventServiceTest {
	@MockBean
	DataStreamingDao dao;
	@MockBean
	HttpServletRequest request;
	@MockBean
	EventStatsResponse eventStatsResponse;
	
	@Autowired
	EventServiceImpl eventService;
	
	   @Test
	    public void saveEvents_validRequest_createsEventList() throws IOException
	    {
		   String eventData = "1607341341814,0.0442672968,1282509067";
		   when(request.getInputStream()).thenReturn(
				   new DelegatingServletInputStream (
				   new ByteArrayInputStream(
				   eventData.getBytes())));
		   
		  List<Event> events =  eventService.saveEvents(request);
	   
		  verify(dao, times(1)).createEvents(events);
	        assertThat(events).isNotEmpty();
	    }
	   
	   @Test
	    public void saveEvents_invalidRequest_requestWithLessThanThreeValues_returnsEmptyEventList()
	    		throws IOException
	    {
		   String eventData = "1607341341814,0.0442672968";
		   when(request.getInputStream()).thenReturn(
				   new DelegatingServletInputStream (
				   new ByteArrayInputStream(
				   eventData.getBytes())));
		   
		  List<Event> events =  eventService.saveEvents(request);
	        verify(dao, times(0)).createEvents(events);
	        assertThat(events).isEmpty();
	    }
	   
	   @Test
	    public void saveEvents_invalidRequest_requestWithNonNumericValues_returnsEmptyEventList()
	    		throws IOException
	    {
		   String eventData = "invalid value,#@$%^&&&&,aaaa####";
		   when(request.getInputStream()).thenReturn(
				   new DelegatingServletInputStream (
				   new ByteArrayInputStream(
				   eventData.getBytes())));
		   
		  List<Event> events =  eventService.saveEvents(request);
	        verify(dao, times(0)).createEvents(events);
	        assertThat(events).isEmpty();
	    }
	   
	   @Test
	    public void saveEvents_invalidRequest_withInvalidTimestamp_returnsEmptyList()
	    		throws IOException
	    {
		   String eventData = "160734134.1814,0.0442672968,1282509067";
		   when(request.getInputStream()).thenReturn(
				   new DelegatingServletInputStream (
				   new ByteArrayInputStream(
				   eventData.getBytes())));
		   
		  List<Event> events =  eventService.saveEvents(request);
	        verify(dao, times(0)).createEvents(events);
	        assertThat(events).isEmpty();
	    }
	   
	   @Test
	    public void saveEvents_invalidRequest_withInvalidFirstColumn_returnsEmptyList()
	    		throws IOException
	    {
		   String eventData = "1607341341814,10.044267296,1282509067";
		   when(request.getInputStream()).thenReturn(
				   new DelegatingServletInputStream (
				   new ByteArrayInputStream(
				   eventData.getBytes())));
		   
		  List<Event> events =  eventService.saveEvents(request);
	        verify(dao, times(0)).createEvents(events);
	        assertThat(events).isEmpty();
	    }
	   
	   @Test
	    public void saveEvents_invalidRequest_withInvalidSecondColumn_returnsEmptyList()
	    		throws IOException
	    {
		   String eventData = "1607341341814,0.0442672968,1073741822";
		   when(request.getInputStream()).thenReturn(
				   new DelegatingServletInputStream (
				   new ByteArrayInputStream(
				   eventData.getBytes())));
		   
		  List<Event> events =  eventService.saveEvents(request);
	        verify(dao, times(0)).createEvents(events);
	        assertThat(events).isEmpty();
	    }
	   
	   @Test
	    public void saveEvents_invalidRequest_emptyContent_returnsEmptyList()
	    		throws IOException
	    {
		   String eventData = "";
		   when(request.getInputStream()).thenReturn(
				   new DelegatingServletInputStream (
				   new ByteArrayInputStream(
				   eventData.getBytes())));
		   
		   
		   
		  List<Event> events =  eventService.saveEvents(request);
	        verify(dao, times(0)).createEvents(events);
	        assertThat(events).isEmpty();
	    }
	   
	   @Rule
		public ExpectedException exceptionRule = ExpectedException.none();
		
		  @Test public void saveEvents_throwsException_returnsBlankResponse()
		  throws IOException 
		  { 
			  exceptionRule.expect(ApplicationException.class);
			   exceptionRule.expectMessage("Failed to save events ");
			  when(eventStatsResponse.getResponse()).thenReturn("");
			  when(dao.createEvents(ArgumentMatchers.<Event>anyList()))
			  .thenThrow(new ApplicationException("Failed to save events "));
		  
		  List<Event> events = eventService.saveEvents(request);
		  assertThat(events).isEmpty();
		  }
	   
	   @Test
	    public void getEventStats_noRecordsInDb_returnsEmptyResponse()
	    		throws IOException
	    {
		   when(eventStatsResponse.getResponse()).thenReturn("");
		  when(dao.getEventStats()).thenReturn(eventStatsResponse);
		   
		  String actualResponse =  eventService.getEventStats();
	        assertThat(actualResponse).isEmpty();
	    }
	   
	   @Test
	    public void getEventStats_statsRecordsPresentInDb_returnsResponse()
	    		throws IOException
	    { 
		   String exectedResponse = "7,1.1345444135,0.1620777734,11824011150,1689144450.000";
		   when(eventStatsResponse.getResponse())
		   .thenReturn(exectedResponse);
		  when(dao.getEventStats()).thenReturn(eventStatsResponse);
		   
		  String actualResponse =  eventService.getEventStats();
	        assertThat(actualResponse).isNotEmpty();
	        assertThat(actualResponse).isEqualTo(exectedResponse);
	    }
	   
		  @Test public void getEventStats_throwsException_returnsBlankResponse()
		  throws IOException 
		  { 
			  exceptionRule.expect(ApplicationException.class);
			   exceptionRule.expectMessage("Failed to retrive events ");
			  when(eventStatsResponse.getResponse()).thenReturn("");
			  when(dao.getEventStats()).thenThrow(new ApplicationException("Failed to retrive events "));
		  
		  String actualResponse = eventService.getEventStats();
		  assertThat(actualResponse).isEmpty();
		  }
		 
		
	

}
