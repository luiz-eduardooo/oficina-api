package org.treino.oficina.exceptions;



import java.time.Instant;

public record ApiError(Instant timestamp, String message, int status, String details) {
}
