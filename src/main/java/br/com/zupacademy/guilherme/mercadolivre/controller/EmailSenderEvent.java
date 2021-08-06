package br.com.zupacademy.guilherme.mercadolivre.controller;

import org.w3c.dom.events.Event;
import org.w3c.dom.events.EventTarget;

public class EmailSenderEvent implements Event {
    @Override
    public String getType() {
        return null;
    }

    @Override
    public EventTarget getTarget() {
        return null;
    }

    @Override
    public EventTarget getCurrentTarget() {
        return null;
    }

    @Override
    public short getEventPhase() {
        return 0;
    }

    @Override
    public boolean getBubbles() {
        return false;
    }

    @Override
    public boolean getCancelable() {
        return false;
    }

    @Override
    public long getTimeStamp() {
        return 0;
    }

    @Override
    public void stopPropagation() {

    }

    @Override
    public void preventDefault() {

    }

    @Override
    public void initEvent(String eventTypeArg, boolean canBubbleArg, boolean cancelableArg) {

    }
}
