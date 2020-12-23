package com.hellofresh.dao.tests;

import static org.assertj.core.api.Assertions.assertThat;

import java.sql.SQLException;
import java.util.List;
import java.util.TimeZone;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import com.hellofresh.config.TestConfig;
import com.hellofresh.dao.DataStreamingDao;
import com.hellofresh.exceptions.ApplicationException;
import com.hellofresh.model.Event;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@ActiveProfiles("test")
public class DataStreamingDaoTest {
	@Autowired
	DataStreamingDao dataStreamingDaoImpl;
	@Autowired
	TimeZone timeZone;

	@Before
	public void setUp() throws Exception {

	}
	
	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void createEvent_withNonEmptyEventList_sucess() throws NumberFormatException, SQLException {
		List<Event> sampleEventList = 
				List.of(buildEvent("1607341341814", "0.0442672968", "1282509067"));
	        
		 assertThat(dataStreamingDaoImpl.createEvents(sampleEventList)).isEqualTo(1);
	}

	@Test
	public void createEvent_withEmptyEventList_doesNotCreateEvent() throws NumberFormatException, SQLException {
		List<Event> sampleEventList = List.of();
	        
		 assertThat(dataStreamingDaoImpl.createEvents(sampleEventList)).isEqualTo(0);
	}
	
	@Rule
	public ExpectedException exceptionRule = ExpectedException.none();

	@Test
	public void createEvent_withInvalidEventList_exceptionThrown_doesNotCreateEvent() throws NumberFormatException, SQLException {
		    List<Event> sampleEventList = 
					List.of(buildEvent("1607341341814", "invalid value", "1282509067"));
		    
		    exceptionRule.expect(ApplicationException.class);
		   exceptionRule.expectMessage("Failed to save events ");
	        
		 assertThat(dataStreamingDaoImpl.createEvents(sampleEventList)).isEqualTo(0);
	}
	
	@Test
	public void getEventStats_emptyDatabase_blankResponse() throws NumberFormatException, SQLException {
	        
		 assertThat(dataStreamingDaoImpl.getEventStats().getResponse()).isEqualTo("");
	}
	
	
	
	private static Event buildEvent(String timestampInMillis,
			 String firstColumn, String secondColumn) {
		Event event = new Event();
		event.setTimestampInMillis(timestampInMillis);
		event.setFirstColumn(firstColumn);
		event.setSecondColumn(secondColumn);
		return event;
		
	}
	
}
