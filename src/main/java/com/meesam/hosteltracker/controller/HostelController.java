package com.meesam.hosteltracker.controller;

import com.meesam.hosteltracker.dto.HostelRequest;
import com.meesam.hosteltracker.dto.HostelResponse;
import com.meesam.hosteltracker.service.HostelService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/hostel")
@RequiredArgsConstructor
public class HostelController {
    private final HostelService hostelService;

    @PostMapping("/create")
    public ResponseEntity<HostelResponse> createHostel(@Valid @RequestBody HostelRequest hostelRequest){
        return new ResponseEntity<>(hostelService.createHostel(hostelRequest), HttpStatus.CREATED);
    }
}
