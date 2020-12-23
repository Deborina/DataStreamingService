package com.hellofresh.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hellofresh.dao.DataStreamingDao;
import com.hellofresh.exceptions.ApplicationException;
import com.hellofresh.model.Event;
import com.hellofresh.util.EventValidator;

@Service
public class EventServiceImpl implements EventService {
    private static final Logger LOGGER = LoggerFactory.getLogger(EventServiceImpl.class);


	@Autowired
	DataStreamingDao dataStreamingDao;
	@Autowired
	EventValidator eventValidator;

	@Override
	//@Async
	public List<Event> saveEvents(HttpServletRequest request) {
		List<Event> validEvents = new ArrayList<Event>();
		try {
			 validEvents = parseRequest(request.getInputStream());
			 if(! validEvents.isEmpty()) {
			dataStreamingDao.createEvents(validEvents);
			 }
		} catch (IOException e) {
			LOGGER.error("Failed to save events " +e.getMessage());
			throw new ApplicationException("Failed to save events "+e.getMessage());
		} catch (Exception e) {
			LOGGER.error("Failed to save events " +e.getMessage());
			throw new ApplicationException("Failed to save events "+e.getMessage());
		}
		return validEvents;

	}

	@Override
	public String getEventStats() {
		try {
			return dataStreamingDao.getEventStats().getResponse();
		} catch (Exception e) {
			LOGGER.error("Failed to retrive events "+ e.getMessage());
			throw new ApplicationException("Failed to retrive events "+e.getMessage());
		}
	}

	private List<Event> parseRequest(final InputStream inputStream) throws Exception {
		List<Event> validEvents = new ArrayList<Event>();
		try {
			try (final BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {
				String line;
				while ((line=br.readLine()) != null) {
					if(eventValidator.isValidInput(line)) {
						final String[] data=line.split(",");
						final Event event=new Event();
						event.setTimestampInMillis(data[0]);
						event.setFirstColumn(data[1]);
						event.setSecondColumn(data[2]);
						if(eventValidator.validateEvent(event)
							&& eventValidator.isValidTimestamp(event.getTimestampInMillis())
							&& eventValidator.isValidFirstColumn(event.getFirstColumn())
							&& eventValidator.isValidSecondColumn(event.getSecondColumn())) {
							validEvents.add(event);
						}
					}
				}
				return validEvents;
			}
		} catch(final IOException e) {
			LOGGER.error("Failed to parse request "+ e.getMessage());
			throw new ApplicationException("Failed to parse Request {}");
		}
	}


}
