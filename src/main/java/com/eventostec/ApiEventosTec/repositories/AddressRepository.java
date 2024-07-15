package com.eventostec.ApiEventosTec.repositories;

import com.eventostec.ApiEventosTec.domain.address.Address;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AddressRepository extends JpaRepository<Address, UUID> {
}
