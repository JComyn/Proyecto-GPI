package es.upm.backend.application.dto;

import java.time.LocalDateTime;

public record ErrorMessage(
    String message,
    String details,
    LocalDateTime timestamp
) {}