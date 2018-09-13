package com.qxf.fileserver.event;

import org.springframework.context.ApplicationEvent;

public class UpdateEvent extends ApplicationEvent {

    private static final long serialVersionUID = 1L;

    public UpdateEvent(Object source) {
        super(source);
    }

}
