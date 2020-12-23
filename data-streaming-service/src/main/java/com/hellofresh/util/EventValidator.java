package com.hellofresh.util;

import static com.hellofreash.constant.ApplicationConstants.NULL_STRING;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.function.Predicate;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.hellofresh.model.Event;

@Component
@Scope(scopeName = "singleton")
public class EventValidator {

	public boolean validateEvent(Event event) {
		return  ( null == event.getTimestampInMillis() 
				|| event.getTimestampInMillis().equals(NULL_STRING)
				|| event.getTimestampInMillis().isEmpty()
				|| null == event.getFirstColumn()
				|| event.getFirstColumn().equals(NULL_STRING)
				|| event.getFirstColumn().isEmpty()
				|| null == event.getSecondColumn()
				|| event.getSecondColumn().equals(NULL_STRING)
				|| event.getSecondColumn().isEmpty())
				? false : true;
	}
	
	public boolean isValidInput(String data) {
		Predicate<String> validData = input -> input.matches("[-+]?[0-9]*\\.?[0-9]+");
		return data.split(",").length == 3 && Arrays.asList(data.split(",")).stream().allMatch(validData);
	}

	public boolean isValidTimestamp(String timestamp) {
		return timestamp.matches("[-+]?[0-9]+")
				? true : false;
		
	}
	
	public boolean isValidFirstColumn(String firstColumn) {
		return((BigDecimal.valueOf(Double.valueOf(firstColumn)))
						.compareTo(BigDecimal.ZERO)>= 0
						&& (BigDecimal.valueOf(Double.valueOf(firstColumn)))
						.compareTo(BigDecimal.ONE) <= 1 
						&& BigDecimal.valueOf(Double.valueOf(firstColumn))
						.scale() == 10)
				? true : false;
		
	}
	
	public boolean isValidSecondColumn(String secondColumn) {
		return (Long.valueOf(secondColumn) >= 1073741823 &&
				Long.valueOf(secondColumn) <= 2147483647)
				? true : false;
				
	}
	
}
