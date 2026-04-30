package com.tyreshop.Roja.Performance.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SupportTicketResponse {
    private String ticketId;
    private String status;
    private Long createdAt;
    private String summary;
}
