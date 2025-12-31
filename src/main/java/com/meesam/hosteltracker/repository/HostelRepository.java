package com.meesam.hosteltracker.repository;

import com.meesam.hosteltracker.model.Hostel;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.awt.print.Pageable;
import java.util.UUID;

@Repository
public interface HostelRepository extends JpaRepository<Hostel, UUID> {
    Page<Hostel> findByNameContainingIgnoreCase(String searchStr, Pageable pageable);
}
