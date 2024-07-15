package com.eventostec.ApiEventosTec.domain.address;


import com.eventostec.ApiEventosTec.domain.event.Event;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(name = "address")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String city;

    private String uf;

    @ManyToOne
    @JoinColumn(name = "event_id")
    private Event event;

}
