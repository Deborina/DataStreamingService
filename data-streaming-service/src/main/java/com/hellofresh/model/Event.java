package com.hellofresh.model;


public class Event {
	
	private String timestampInMillis;
	
	private String firstColumn;
	
	private String secondColumn;
	
	public String getTimestampInMillis() {
		return timestampInMillis;
	}
	public void setTimestampInMillis(String timestampInMillis) {
		if(null == timestampInMillis || timestampInMillis.isEmpty())
		{
			throw new IllegalArgumentException("Invalid data");
		}
		this.timestampInMillis = timestampInMillis;
	}
	public String getFirstColumn() {
		return firstColumn;
	}
	public void setFirstColumn(String firstColumn) {
		if(null == timestampInMillis || timestampInMillis.isEmpty())
		{
			throw new IllegalArgumentException("Invalid data");
		}
		this.firstColumn = firstColumn;
	}
	public String getSecondColumn() {
		return secondColumn;
	}
	public void setSecondColumn(String secondColumn) {
		if(null == timestampInMillis || timestampInMillis.isEmpty())
		{
			throw new IllegalArgumentException("Invalid data");
		}
		this.secondColumn = secondColumn;
	}

}
