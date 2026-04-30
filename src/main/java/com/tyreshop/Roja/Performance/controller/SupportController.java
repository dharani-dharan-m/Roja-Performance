package com.tyreshop.Roja.Performance.controller;

import com.tyreshop.Roja.Performance.dto.SupportTicketRequest;
import com.tyreshop.Roja.Performance.dto.SupportTicketResponse;
import jakarta.validation.Valid;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/support")
@CrossOrigin(origins = "*", maxAge = 3600)
public class SupportController {
    private final AtomicLong ticketSequence = new AtomicLong(1000);
    private final List<SupportTicketResponse> tickets = new CopyOnWriteArrayList<>();

    @PostMapping("/tickets")
    public ResponseEntity<SupportTicketResponse> createTicket(@Valid @RequestBody SupportTicketRequest request) {
        String date = LocalDate.now().format(DateTimeFormatter.BASIC_ISO_DATE);
        String ticketId = String.format("RP-%s-%04d", date, ticketSequence.incrementAndGet());
        String priority = request.getPriority() == null || request.getPriority().isBlank()
            ? "Normal"
            : request.getPriority();

        SupportTicketResponse response = SupportTicketResponse.builder()
            .ticketId(ticketId)
            .status("OPEN")
            .createdAt(System.currentTimeMillis())
            .summary(String.format("%s | %s", request.getTopic(), priority))
            .build();

        tickets.add(response);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/tickets")
    public ResponseEntity<List<SupportTicketResponse>> getTickets() {
        return ResponseEntity.ok(tickets);
    }
}
