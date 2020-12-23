package com.hellofresh.config;

import java.util.TimeZone;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.Scope;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import com.hellofresh.controller.DataStreamController;
import com.hellofresh.dao.DataStreamingDao;
import com.hellofresh.dao.DataStreamingDaoImpl;
import com.hellofresh.service.EventService;
import com.hellofresh.service.EventServiceImpl;
import com.hellofresh.util.EventValidator;

@Configuration
@Profile("test")
public class TestConfig {

	@Bean
	public TimeZone timeZone(){
		TimeZone defaultTimeZone = TimeZone.getTimeZone("UTC");
		TimeZone.setDefault(defaultTimeZone);
		return defaultTimeZone;
	}
	@Bean
	public DataStreamingDao dataStreamingDao() {
		return new DataStreamingDaoImpl();
	}


	@Bean
	public EventService eventService() {
		return new EventServiceImpl();
	}

	@Bean
	public DataStreamController dataStreamController() {
		return new DataStreamController();
	}



	@Bean
	@Scope(scopeName = "singleton") 
	public EventValidator eventValidator() {
		return new EventValidator(); }


	@Bean
	public DataSource h2DataSource() {
		return new EmbeddedDatabaseBuilder()
				.setType(EmbeddedDatabaseType.H2)
				.addScript("schema.sql")
				.build();
	}

	@Bean
	public JdbcTemplate jdbcTemplate() {
		return new JdbcTemplate(h2DataSource());
	}


}
