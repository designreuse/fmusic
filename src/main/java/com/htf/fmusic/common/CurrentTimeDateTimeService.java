package com.htf.fmusic.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.ZonedDateTime;

/**
 * @author HTFeeds
 */
public class CurrentTimeDateTimeService implements DateTimeService {

	private static final Logger LOGGER = LoggerFactory.getLogger(CurrentTimeDateTimeService.class);

	@Override
	public ZonedDateTime getCurrentDateAndTime() {
		ZonedDateTime currentDateAndTime = ZonedDateTime.now();

		LOGGER.info("Returning current date and time: {}", currentDateAndTime);

		return currentDateAndTime;
	}
}
