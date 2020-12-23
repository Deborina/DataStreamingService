package com.hellofresh.dao;

import static com.hellofreash.constant.ApplicationConstants.BLANK_STRING;
import static com.hellofreash.constant.QueryConstant.QUERY_EVENT_STATS;
import static com.hellofreash.constant.QueryConstant.QUERY_SAVE;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.stereotype.Repository;

import com.hellofresh.exceptions.ApplicationException;
import com.hellofresh.model.Event;
import com.hellofresh.model.EventStatsResponse;
import com.hellofresh.util.EventValidator;

@Repository
public class DataStreamingDaoImpl implements DataStreamingDao {
	private static final Logger LOGGER = LoggerFactory.getLogger(DataStreamingDaoImpl.class);


	@Autowired
	JdbcTemplate jdbcTemplate;
	@Autowired
	EventValidator eventValidaor;

	@Override
	public int createEvents(List<Event> events) {
		int noOfEventCreated = 0;
		try {
			if(!events.isEmpty())
			{
				Event event = events.get(0);
				System.out.println(
						events.get(0).getTimestampInMillis() +" "
				+ events.get(0).getFirstColumn()+" "
				+ events.get(0).getSecondColumn());
				LOGGER.info("Saving a event of size {} records "+ events.size());
				noOfEventCreated = jdbcTemplate.update(QUERY_SAVE,  new PreparedStatementSetter () {
					@Override
					public void setValues(PreparedStatement ps) throws SQLException {
						DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						format.setTimeZone(TimeZone.getTimeZone("UTC"));
						String formatted = format.format(new Date(Long.valueOf(event.getTimestampInMillis())));
						ps.setTimestamp(1, Timestamp.valueOf(formatted));
						ps.setBigDecimal(2, BigDecimal.valueOf(Double.valueOf(event.getFirstColumn())));
						ps.setLong(3, Long.valueOf(event.getSecondColumn()));	
					}
				});
			}
		} catch (Exception e) {
			
			LOGGER.error("Failed to save events " +e.getMessage());
			throw new ApplicationException("Failed to save events "+e.getMessage());
		}
		return noOfEventCreated;
	}

	@Override
	public EventStatsResponse getEventStats() {
		LOGGER.info("Retrieving event stats ");

		EventStatsResponse eventStatsResponse = new EventStatsResponse();
		try {
			List<Map<String, Object>> stats = jdbcTemplate.queryForList(QUERY_EVENT_STATS);
			String count = stats.get(0).get("count") == null
					? BLANK_STRING : stats.get(0).get("count").toString();
			String firstColumnSum = stats.get(0).get("firstColumnSum") == null
					? BLANK_STRING : stats.get(0).get("firstColumnSum").toString();
			String firstColumnAvg = stats.get(0).get("firstColumnAvg") == null
					? BLANK_STRING : stats.get(0).get("firstColumnAvg").toString();
			String secondColumnSum = stats.get(0).get("secondColumnSum") == null
					? BLANK_STRING : stats.get(0).get("secondColumnSum").toString();
			String secondColumnAvg =  stats.get(0).get("secondColumnAvg") == null
					? BLANK_STRING : stats.get(0).get("secondColumnAvg").toString();
			if(!count.equals(BLANK_STRING) && !firstColumnAvg.equals(BLANK_STRING)
					&& !firstColumnSum.equals(BLANK_STRING) && !secondColumnAvg.equals(BLANK_STRING)
					&& !secondColumnSum.equals(BLANK_STRING)) {
				eventStatsResponse
				.setResponse(String.join(",", count, firstColumnSum, firstColumnAvg,
						secondColumnSum, secondColumnAvg));
			}
			else {
				eventStatsResponse.setResponse(BLANK_STRING);
			}
		} catch (Exception e) {
			eventStatsResponse.setResponse(BLANK_STRING);
			LOGGER.error("Failed to retrive events "+ e.getMessage());
			throw new ApplicationException("Failed to retrive events "+e.getMessage());
		}
		return eventStatsResponse;

	}


}
