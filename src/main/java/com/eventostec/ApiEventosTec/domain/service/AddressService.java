package com.eventostec.ApiEventosTec.domain.service;

import com.eventostec.ApiEventosTec.domain.address.Address;
import com.eventostec.ApiEventosTec.domain.event.Event;
import com.eventostec.ApiEventosTec.domain.event.EventRequestDTO;
import com.eventostec.ApiEventosTec.repositories.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AddressService {

    @Autowired
    private AddressRepository addressRepository;

    public Address createAddress(EventRequestDTO eventRequestDTO, Event event){

        Address address = Address
                .builder()
                .city(eventRequestDTO.city())
                .uf(eventRequestDTO.state())
                .event(event)
                .build();

        return addressRepository.save(address);
    }

}
