package com.example.planner;

public class Event {

    // variables for our coursename,
    // description, tracks and duration, id.
    private String eventPlan;
    private String eventLocation;
    private String eventDate;
    private String eventTime;
    private int id;


    // constructor
    public Event(Integer id, String eventPlan, String eventLocation, String eventDate, String eventTime) {
        this.id = id;
        this.eventPlan = eventPlan;
        this.eventLocation = eventLocation;
        this.eventDate = eventDate;
        this.eventTime = eventTime;
    }

    // creating getter and setter methods
    public String getEventPlan() {
        return eventPlan;
    }

    public void setEventPlan(String eventPlan) {
        this.eventPlan = eventPlan;
    }

    public String getEventLocation() {
        return eventLocation;
    }

    public void setEventLocation(String eventLocation) {
        this.eventLocation = eventLocation;
    }

    public String getEventDate() {
        return eventDate;
    }

    public void setEventDate(String eventDate) {
        this.eventDate = eventDate;
    }

    public String getEventTime() {
        return eventTime;
    }

    public void setEventTime(String eventTime) {
        this.eventTime = eventTime;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

}
