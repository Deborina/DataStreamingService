package com.hellofresh.service;


import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.hellofresh.model.Event;

public interface EventService {

	  List<Event> saveEvents(HttpServletRequest request);
	 String getEventStats();
}
