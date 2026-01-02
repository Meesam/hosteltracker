package com.meesam.hosteltracker.service;

import com.meesam.hosteltracker.dto.HostelRequest;
import com.meesam.hosteltracker.dto.HostelResponse;
import com.meesam.hosteltracker.model.Hostel;
import com.meesam.hosteltracker.repository.HostelRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class HostelService {

    private final HostelRepository hostelRepository;

    public HostelResponse createHostel(HostelRequest hostelRequest) {
        Hostel hostel = Hostel.builder()
                .name(hostelRequest.name())
                .description(hostelRequest.description())
                .address(hostelRequest.address())
                .state(hostelRequest.state())
                .city(hostelRequest.city())
                .pinCode(hostelRequest.pinCode())
                .country(hostelRequest.country())
                .contactPerson(hostelRequest.contactPerson())
                .contactNumber(hostelRequest.contactNumber())
                .build();
        Hostel savedHostel = hostelRepository.save(hostel);
        return HostelResponse.builder()
                .id(savedHostel.getId())
                .name(savedHostel.getName())
                .description(savedHostel.getDescription())
                .address(savedHostel.getAddress())
                .city(savedHostel.getCity())
                .state(savedHostel.getState())
                .pinCode(savedHostel.getPinCode())
                .contactNumber(savedHostel.getContactNumber())
                .contactPerson(savedHostel.getContactPerson())
                .country(savedHostel.getCountry())
                .createdAt(savedHostel.getCreatedAt())
                .lastModifiedBy(savedHostel.getLastModifiedBy())
                .updatedAt(savedHostel.getUpdatedAt())
                .createdBy(savedHostel.getCreatedBy())
                .build();
    }

    public Iterable<Hostel> getAllHostels(){
        return hostelRepository.findAll().stream().toList();
    }

    public HostelResponse getHostelById(UUID id){
        Optional<Hostel> hostel = hostelRepository.findById(id);
        if(hostel.isPresent()){
            Hostel savedHostel = hostel.get();
            return HostelResponse.builder()
                    .id(savedHostel.getId())
                    .name(savedHostel.getName())
                    .description(savedHostel.getDescription())
                    .address(savedHostel.getAddress())
                    .city(savedHostel.getCity())
                    .state(savedHostel.getState())
                    .pinCode(savedHostel.getPinCode())
                    .contactNumber(savedHostel.getContactNumber())
                    .contactPerson(savedHostel.getContactPerson())
                    .country(savedHostel.getCountry())
                    .createdAt(savedHostel.getCreatedAt())
                    .lastModifiedBy(savedHostel.getLastModifiedBy())
                    .updatedAt(savedHostel.getUpdatedAt())
                    .createdBy(savedHostel.getCreatedBy())
                    .build();
        }
        return null;
    }
}
