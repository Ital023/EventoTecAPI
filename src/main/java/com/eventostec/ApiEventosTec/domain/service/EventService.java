package com.eventostec.ApiEventosTec.domain.service;

import com.eventostec.ApiEventosTec.domain.event.Event;
import com.eventostec.ApiEventosTec.domain.event.EventRequestDTO;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

@Service
public class EventService {

    public Event createEvent(EventRequestDTO eventRequestDTO){
        String imgUrl = null;

        if(eventRequestDTO.image() != null) {
            imgUrl = uploadImg(eventRequestDTO.image());
        }

        Event event = Event
                .builder()
                .title(eventRequestDTO.title())
                .description(eventRequestDTO.description())
                .eventUrl(eventRequestDTO.eventUrl())
                .date(new Date(eventRequestDTO.date()))
                .imgUrl(imgUrl)
                .build();

        return event;
    }

    private String uploadImg(MultipartFile file){
        return null;
    }
}
