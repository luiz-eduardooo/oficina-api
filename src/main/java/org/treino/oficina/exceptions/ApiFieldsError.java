package org.treino.oficina.exceptions;

import java.time.Instant;
import java.util.List;

public record ApiFieldsError(Instant timestamp, String message, int status, String details, List<CamposInvalidos> camposInvalidos) {
}
