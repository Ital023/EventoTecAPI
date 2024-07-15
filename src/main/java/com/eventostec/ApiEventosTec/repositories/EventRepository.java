package com.eventostec.ApiEventosTec.repositories;

import com.eventostec.ApiEventosTec.domain.event.Event;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface EventRepository extends JpaRepository<Event, UUID> {
}
