package com.eventostec.ApiEventosTec.domain.coupon;

import java.util.Date;

public record CouponRequestDTO( String code, Integer discount, Long valid) {
}
