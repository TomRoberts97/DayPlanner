package com.tomrob.dayplanner;

import java.util.Date;

public class TimeSlot {

    String startTime;
    String endTime;
    String timeSlotType;
    String descriptionHeader;
    String descriptionBody;
    String date;

    public TimeSlot(String startTime, String endTime, String timeSlotType, String descriptionHeader, String descriptionBody, String date) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.timeSlotType = timeSlotType;
        this.descriptionHeader = descriptionHeader;
        this.descriptionBody = descriptionBody;
        this.date = date;
    }

    public String getStartTime() {
        return startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public String getTimeSlotType() {
        return timeSlotType;
    }

    public String getDescriptionHeader() {
        return descriptionHeader;
    }

    public String getDescriptionBody() {
        return descriptionBody;
    }

    public String getDate() {
        return date;
    }
}
