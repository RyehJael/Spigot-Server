package com.civai.ryehjael.Events;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;


public class HourPassedEvent extends Event{

    private static final HandlerList handlers = new HandlerList();

    public HourPassedEvent() {

    }

    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}
