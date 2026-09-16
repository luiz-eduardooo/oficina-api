package org.treino.oficina.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.treino.oficina.exceptions.OS.OrdemDeServicoFinalizadaException;
import org.treino.oficina.exceptions.OS.OrdemServicoJaAbertaException;
import org.treino.oficina.exceptions.OS.OrdemVaziaException;
import org.treino.oficina.exceptions.OS.OsNaoEncontradaException;
import org.treino.oficina.exceptions.cliente.ClienteNaoEncontradoException;
import org.treino.oficina.exceptions.item.PecaNaoEncontradaException;
import org.treino.oficina.exceptions.veiculo.PlacaJaExistenteException;
import org.treino.oficina.exceptions.veiculo.VeiculoNaoEncontradoException;
import org.treino.oficina.exceptions.veiculo.VeiculoNaoPertencenteException;

import java.time.Instant;
import java.util.List;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {


    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ApiError> exception(RuntimeException ex, WebRequest request){
        ApiError apiError = new ApiError(Instant.now(),"Erro interno", HttpStatus.INTERNAL_SERVER_ERROR.value(), request.getDescription(false));
        log.error("Erro não tratado", ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(apiError);
    }


    @ExceptionHandler(OrdemDeServicoFinalizadaException.class)
    public ResponseEntity<ApiError> ordemServicoFinalizada(OrdemDeServicoFinalizadaException ex, WebRequest request){
        ApiError apiError = toApiError(ex, HttpStatus.CONFLICT, request);
        return ResponseEntity.status(HttpStatus.CONFLICT).body(apiError);
    }

    @ExceptionHandler(OrdemServicoJaAbertaException.class)
    public ResponseEntity<ApiError> ordemServicoJaAberta(OrdemServicoJaAbertaException ex, WebRequest request){
        ApiError apiError = toApiError(ex, HttpStatus.CONFLICT, request);
        return ResponseEntity.status(HttpStatus.CONFLICT).body(apiError);
    }

    @ExceptionHandler(ClienteNaoEncontradoException.class)
    public ResponseEntity<ApiError> clienteNaoEncontrado(ClienteNaoEncontradoException ex, WebRequest request){
        ApiError apiError = toApiError(ex, HttpStatus.NOT_FOUND, request);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiError);
    }

    @ExceptionHandler(OsNaoEncontradaException.class)
    public ResponseEntity<ApiError> osNaoEncontrada(OsNaoEncontradaException ex, WebRequest request){
        ApiError apiError = toApiError(ex, HttpStatus.NOT_FOUND, request);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiError);
    }

    @ExceptionHandler(PecaNaoEncontradaException.class)
    public ResponseEntity<ApiError> pecaNaoEncontrada(PecaNaoEncontradaException ex, WebRequest request){
        ApiError apiError = toApiError(ex, HttpStatus.NOT_FOUND, request);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiError);
    }

    @ExceptionHandler(VeiculoNaoEncontradoException.class)
    public ResponseEntity<ApiError> veiculoNaoEncontrado(VeiculoNaoEncontradoException ex, WebRequest request){
        ApiError apiError = toApiError(ex, HttpStatus.NOT_FOUND, request);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiError);
    }

    @ExceptionHandler(OrdemVaziaException.class)
    public ResponseEntity<ApiError> ordemVazia(OrdemVaziaException ex, WebRequest request){
        ApiError apiError = toApiError(ex, HttpStatus.BAD_REQUEST, request);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiError);
    }

    @ExceptionHandler(PlacaJaExistenteException.class)
    public ResponseEntity<ApiError> placaJaExistente(PlacaJaExistenteException ex, WebRequest request){
        ApiError apiError = toApiError(ex, HttpStatus.CONFLICT, request);
        return ResponseEntity.status(HttpStatus.CONFLICT).body(apiError);
    }

    @ExceptionHandler(VeiculoNaoPertencenteException.class)
    public ResponseEntity<ApiError> veiculoNaoPertecente(VeiculoNaoPertencenteException ex, WebRequest request){
        ApiError apiError = toApiError(ex, HttpStatus.CONFLICT, request);
        return ResponseEntity.status(HttpStatus.CONFLICT).body(apiError);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiFieldsError> validationErrror(MethodArgumentNotValidException ex, WebRequest request){
        ApiFieldsError apiFieldsError = new ApiFieldsError(Instant.now(), "Erros de validação", HttpStatus.BAD_REQUEST.value(), request.getDescription(false), camposInvalidosList(ex));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiFieldsError);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ApiError> pathIncorretoError(MethodArgumentTypeMismatchException ex, WebRequest request){
        ApiError apiError = toApiError(ex, HttpStatus.BAD_REQUEST, request);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiError);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiError> requisicaoMalFormatadaException(HttpMessageNotReadableException ex, WebRequest request){
        ApiError apiError = toApiError(ex, HttpStatus.BAD_REQUEST, request);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiError);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ApiError> constraintViolada(DataIntegrityViolationException ex, WebRequest request){
        ApiError apiError = new ApiError(Instant.now(), "A Constraint do banco de dados foi violada", HttpStatus.CONFLICT.value(), request.getDescription(false));
        return ResponseEntity.status(HttpStatus.CONFLICT).body(apiError);
    }


    private List<CamposInvalidos> camposInvalidosList(MethodArgumentNotValidException ex){
        return ex.getBindingResult().getFieldErrors().stream().map(error -> new CamposInvalidos(error.getField(), error.getDefaultMessage())).toList();
    }




    private ApiError toApiError(RuntimeException ex,HttpStatus status ,WebRequest request){
        return new ApiError(Instant.now(),ex.getMessage(), status.value(), request.getDescription(false));
    }
}
