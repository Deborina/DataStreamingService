package com.hellofresh.dao;

import java.util.List;

import com.hellofresh.model.Event;
import com.hellofresh.model.EventStatsResponse;

public interface DataStreamingDao {

	 int createEvents(List<Event> events);
	 EventStatsResponse getEventStats();
}
