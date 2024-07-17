package com.eventostec.ApiEventosTec.domain.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.eventostec.ApiEventosTec.domain.event.Event;
import com.eventostec.ApiEventosTec.domain.event.EventRequestDTO;
import com.eventostec.ApiEventosTec.domain.event.EventResponseDTO;
import com.eventostec.ApiEventosTec.repositories.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
public class EventService {

    @Autowired
    private AmazonS3 s3Client;

    @Value("${aws.bucket.name}")
    private String bucketName;

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private AddressService addressService;

    public Event createEvent(EventRequestDTO eventRequestDTO){
        String imgUrl = null;

        if(eventRequestDTO.image() != null) {
            imgUrl = this.uploadImg(eventRequestDTO.image());
        }

        Event event = Event
                .builder()
                .title(eventRequestDTO.title())
                .description(eventRequestDTO.description())
                .eventUrl(eventRequestDTO.eventUrl())
                .date(new Date(eventRequestDTO.date()))
                .imgUrl(imgUrl)
                .remote(eventRequestDTO.remote())
                .build();

        eventRepository.save(event);

        if(!eventRequestDTO.remote()) {
            addressService.createAddress(eventRequestDTO, event);
        }

        return event;
    }

    private String uploadImg(MultipartFile multipartFile){
        String filename = UUID.randomUUID() + "-" + multipartFile.getOriginalFilename();

        try{
            File file = this.convertMultipartFile(multipartFile);
            s3Client.putObject(bucketName, filename, file);
            file.delete();
            return s3Client.getUrl(bucketName, filename).toString();
        }catch (Exception e){
            System.out.println("Erro ao subir arquivo");
            return null;
        }
    }

    private File convertMultipartFile(MultipartFile multipartFile) throws IOException {
        File convFile = new File(Objects.requireNonNull(multipartFile.getOriginalFilename()));
        FileOutputStream fos = new FileOutputStream(convFile);
        fos.write(multipartFile.getBytes());
        fos.close();
        return convFile;
    }

    public List<EventResponseDTO> getEvents(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Event> eventsPage = eventRepository.findAll(pageable);
        return eventsPage
                .map(event -> new EventResponseDTO(
                                event.getId(),
                                event.getTitle(),
                                event.getDescription(),
                                event.getDate(),
                                "",
                                "",
                                event.getRemote(),
                                event.getImgUrl(),
                                event.getEventUrl()
                        )

                ).stream().toList();

    }


    public List<EventResponseDTO> getUpcomingEvents(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Event> eventsPage = eventRepository.findUpcomingEvents(new Date(), pageable);
        return eventsPage
                .map(event -> new EventResponseDTO(
                                event.getId(),
                                event.getTitle(),
                                event.getDescription(),
                                event.getDate(),
                                "",
                                "",
                                event.getRemote(),
                                event.getImgUrl(),
                                event.getEventUrl()
                        )

                ).stream().toList();

    }


}
