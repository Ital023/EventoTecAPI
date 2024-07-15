package com.eventostec.ApiEventosTec.controller;

import com.eventostec.ApiEventosTec.domain.coupon.Coupon;
import com.eventostec.ApiEventosTec.domain.coupon.CouponRequestDTO;
import com.eventostec.ApiEventosTec.domain.service.CouponService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/coupon")
public class CouponController {

    @Autowired
    private CouponService couponService;

    @PostMapping("/event/{eventId}")
    public ResponseEntity<Coupon> addCuponsToEvent(@PathVariable("eventId")UUID eventId, @RequestBody CouponRequestDTO couponRequestDTO){
        Coupon coupon = couponService.addCuponsToEvent(eventId,couponRequestDTO);

        return ResponseEntity.ok(coupon);
    }

}
