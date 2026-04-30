package com.tyreshop.Roja.Performance.controller;

import com.tyreshop.Roja.Performance.dto.ChatRequest;
import com.tyreshop.Roja.Performance.dto.ChatResponse;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/chatbot")
@CrossOrigin(origins = "*", maxAge = 3600)
public class ChatbotController {
    @PostMapping
    public ResponseEntity<ChatResponse> reply(@Valid @RequestBody ChatRequest request) {
        String message = request.getMessage() == null ? "" : request.getMessage().toLowerCase();
        String reply = buildReply(message);
        ChatResponse response = ChatResponse.builder()
            .reply(reply)
            .suggestions(List.of(
                "How do I add a product?",
                "How to create an order?",
                "Why is the API offline?"
            ))
            .timestamp(System.currentTimeMillis())
            .build();
        return ResponseEntity.ok(response);
    }

    private String buildReply(String message) {
        if (message.contains("product") || message.contains("inventory") || message.contains("stock")) {
            return "Go to Products and use Add New Product. Fill brand, type, price, quantity, and size.";
        }
        if (message.contains("order")) {
            return "Open Orders, click Add New Order, choose customer and product, then save.";
        }
        if (message.contains("customer")) {
            return "Customers are managed in the Customers tab. Add new customers to keep records clean.";
        }
        if (message.contains("user") || message.contains("role")) {
            return "Users are managed in the Users tab. Roles control who can add or edit records.";
        }
        if (message.contains("api") || message.contains("offline")) {
            return "If the system is offline, make sure the backend is running on port 8080 and refresh.";
        }
        if (message.contains("help") || message.contains("support")) {
            return "Open Help for quick fixes or Contact to reach support.";
        }
        return "Ask me about products, orders, customers, users, or setup.";
    }
}
