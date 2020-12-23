package com.hellofreash.constant;

public class QueryConstant {

	public static final String QUERY_SAVE = "insert into EVENTS(timestamp_millis, event_col1, event_col2) "
											+ "values(?, ?, ?) ";
	
	public static final String QUERY_EVENT_STATS = "select count(*) as count, sum(EVENT_COL1) as firstColumnSum,"
			+ " avg(EVENT_COL1) as firstColumnAvg, sum(EVENT_COL2) as secondColumnSum,"
			+ " avg(EVENT_COL2) as secondColumnAvg  from EVENTS"
			+ " where TIMESTAMP_MILLIS  >= timestampadd(second, -60, now());"; 
}
