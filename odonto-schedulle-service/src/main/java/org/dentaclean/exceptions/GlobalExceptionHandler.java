package org.dentaclean.exceptions;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import org.hibernate.PersistentObjectException;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.InvalidDataAccessResourceUsageException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RestControllerAdvice
@AllArgsConstructor
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

   private MessageSource messageSource;

   @Override
   protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
                                                                 HttpHeaders headers, HttpStatus status, WebRequest request) {
      var titulo = "O body da requisição inválido";
      var mensagem = "O corpo da requisição está inválido. Verifique erro de sintaxe.";
      Problema problema = new Problema(status.value(), LocalDateTime.now(), titulo, mensagem, null);
      return handleExceptionInternal(ex, problema, headers, status, request);
   }

   /**
    * Tratamento para parâmentro obrigatório
    */
   @Override
   protected ResponseEntity<Object> handleMissingServletRequestParameter(
           MissingServletRequestParameterException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
      var mensagem = "O parâmetro '" + ex.getParameterName().toUpperCase() + "' é obrigatório.";
      Problema problema = new Problema(status.value(), LocalDateTime.now(), mensagem, mensagem, null);
      return handleExceptionInternal(ex, problema, headers, status, request);
   }

   @Override
   protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers,
                                                                 HttpStatus status, WebRequest request) {
      List<Problema.Campo> collect = ex.getBindingResult().getFieldErrors().stream().map(
              bjectError -> new Problema.Campo(bjectError.getField(), bjectError.getDefaultMessage())).collect(Collectors.toList());
      var titulo ="Dados inválidos";
      var mensagem = "Um ou mais campos estão inválidos. Faça o preenchimento correto e tente novamente.";
      Problema problema = Problema.builder().code(status.value()).titulo(titulo).mensagem(mensagem).dataHora(LocalDateTime.now()).campos(collect).build();
      problema.setCode(status.value());
      problema.setCampos(collect);
      return handleExceptionInternal(ex, problema, headers, status, request);
   }

   @Override
   protected ResponseEntity<Object> handleTypeMismatch(TypeMismatchException ex, HttpHeaders headers,
                                                       HttpStatus status, WebRequest request) {
      if (ex instanceof MethodArgumentTypeMismatchException) {
         return handleMethodArgumentTypeMismatch(
                 (MethodArgumentTypeMismatchException) ex, headers, status, request);
      }
      return super.handleTypeMismatch(ex, headers, status, request);
   }

   /**
    * Tratamento para parâmentro de URL incompatíveis
    * 1. MethodArgumentTypeMismatchException é um subtipo de TypeMismatchException
    * 2. ResponseEntityExceptionHandler já trata TypeMismatchException de forma mais abrangente
    * 3. Então, especializamos o método handleTypeMismatch e verificamos se a exception
    * é uma instância de MethodArgumentTypeMismatchException
    */
   private ResponseEntity<Object> handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException ex,
                                                                   HttpHeaders headers, HttpStatus status, WebRequest request) {
      var titulo = "Parâmentro de URL incompatíveis";
      var mensagem = String.format(
              "O parâmetro de URL '%s' recebeu o valor '%s', "
                      + "que é de um tipo inválido. Corrija e informe um valor compatível com o tipo %s.",
              ex.getName(), ex.getValue(), Objects.requireNonNull(ex.getRequiredType()).getSimpleName());
      Problema problema = new Problema(status.value(), LocalDateTime.now(), titulo, mensagem, null);
      return handleExceptionInternal(ex, problema, headers, status, request);
   }

   @Override
   protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex,
                                                                        HttpHeaders headers, HttpStatus status, WebRequest request) {
      var titulo = "Método não suportado";
      var mensagem = "Método de solicitação '" + ex.getMethod() + "' não suportado";
      Problema problema = new Problema(status.value(), LocalDateTime.now(), titulo, mensagem, null);
      return handleExceptionInternal(ex, problema, headers, status, request);
   }

   @ExceptionHandler({InvalidDataAccessResourceUsageException.class})
   public ResponseEntity<Object> invalidDataAccessResourceUsageException(InvalidDataAccessResourceUsageException ex, WebRequest request) {
      HttpStatus status = HttpStatus.BAD_REQUEST;
      Problema problema = new Problema(status.value(), LocalDateTime.now(), "teste", "mensagem", null);
      return handleExceptionInternal(ex, problema, new HttpHeaders(), status, request);
   }

   @ExceptionHandler({DataIntegrityViolationException.class})
   public ResponseEntity<Object> handleDataIntegrityViolationException(DataIntegrityViolationException ex, WebRequest request) {
      HttpStatus status = HttpStatus.BAD_REQUEST;
      var titulo = "Operação não permitida";
      var mensagem = "";
      if (ex.getCause() instanceof ConstraintViolationException) {
         ConstraintViolationException constraintViolationException = (ConstraintViolationException) ex.getCause();
         if (constraintViolationException.getCause().getMessage().contains("Duplicate")) {
            mensagem = "Valor já cadastrado na base de dados";
         }
      }
      Problema problema = new Problema(status.value(), LocalDateTime.now(), titulo, mensagem, null);
      return handleExceptionInternal(ex, problema, new HttpHeaders(), status, request);
   }

   @ExceptionHandler({PersistentObjectException.class})
   public ResponseEntity<Object> persistentObjectException(PersistentObjectException ex, WebRequest request) {
      String mensagem = messageSource.getMessage("recurso.detached", null, LocaleContextHolder.getLocale());
      HttpStatus status = HttpStatus.BAD_REQUEST;
      Problema problema = new Problema(status.value(), LocalDateTime.now(), mensagem, mensagem, null);
      return handleExceptionInternal(ex, problema, new HttpHeaders(), status, request);
   }

   @ExceptionHandler({EmptyResultDataAccessException.class})
   protected ResponseEntity<Object> handleEmptyResultDataAccessException(EmptyResultDataAccessException ex, WebRequest request) {
      var titulo = "Recurso não encontrado";
      var msn = "O recurso que você tentou acessar, é inexistente.";
      HttpStatus status = HttpStatus.BAD_REQUEST;
      Problema problema = new Problema(status.value(), LocalDateTime.now(), titulo, msn, null);
      return handleExceptionInternal(ex, problema, null, status, request);
   }


   @Data
   @Builder
   @JsonInclude(JsonInclude.Include.NON_NULL)
   public static class Problema {
      private Integer code;
      private LocalDateTime dataHora;
      private String titulo;
      private String mensagem;
      private List<Campo> campos;


      @Getter
      @AllArgsConstructor
      public static class Campo {
         private String nome;
         private String mensagem;
      }
   }


}