package com.moringaschool.consult.ui;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

public class Event
{
    public static ArrayList<Event> eventsList = new ArrayList<>();



    public static ArrayList<Event> eventsForDate(LocalDate date)
    {
        ArrayList<Event> events = new ArrayList<>();

        for(Event event : eventsList)
        {
            if(event.getEventDate().equals(date))
                events.add(event);
        }

        return events;
    }


    private String eventName;
    private LocalDate eventDate;
    private LocalTime eventTime;

    public Event(String eventName, LocalDate eventDate, LocalTime eventTime)
    {
        this.eventName = eventName ;
        this.eventDate = eventDate;
        this.eventTime = eventTime;
    }

    public static ArrayList<Event> getEventsList() {
        return eventsList;
    }

    public static void setEventsList(ArrayList<Event> eventsList) {
        Event.eventsList = eventsList;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public LocalDate getEventDate() {
        return eventDate;
    }

    public void setEventDate(LocalDate eventDate) {
        this.eventDate = eventDate;
    }

    public LocalTime getEventTime() {
        return eventTime;
    }

    public void setEventTime(LocalTime eventTime) {
        this.eventTime = eventTime;
    }
}

