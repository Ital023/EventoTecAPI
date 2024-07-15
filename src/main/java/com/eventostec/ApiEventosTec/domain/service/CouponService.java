package com.eventostec.ApiEventosTec.domain.service;

import com.eventostec.ApiEventosTec.domain.coupon.Coupon;
import com.eventostec.ApiEventosTec.domain.coupon.CouponRequestDTO;
import com.eventostec.ApiEventosTec.domain.event.Event;
import com.eventostec.ApiEventosTec.repositories.CouponRepository;
import com.eventostec.ApiEventosTec.repositories.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@Service
public class CouponService {

    @Autowired
    private CouponRepository couponRepository;

    @Autowired
    private EventRepository eventRepository;

    public Coupon addCuponsToEvent(UUID eventId, CouponRequestDTO couponRequestDTO) {

        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new IllegalArgumentException("Event not found"));


        Coupon couponEntity = Coupon
                .builder()
                .code(couponRequestDTO.code())
                .discount(couponRequestDTO.discount())
                .valid(new Date(couponRequestDTO.valid()))
                .event(event)
                .build();

        Coupon coupon = couponRepository.save(couponEntity);

        return coupon;
    }



}
