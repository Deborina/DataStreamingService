package com.hellofresh.controller;


import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.hellofresh.service.EventService;


@RestController
public class DataStreamController {
	@Autowired
	EventService eventService;
	
	@PostMapping("/event")
	@ResponseStatus(HttpStatus.ACCEPTED)
	public void createEvent(HttpServletRequest request)  {
			eventService.saveEvents(request);
	}
	
	@GetMapping("/stats")
	@ResponseStatus(HttpStatus.OK)
	public String getStats()  {
		return eventService.getEventStats();
	}

}
