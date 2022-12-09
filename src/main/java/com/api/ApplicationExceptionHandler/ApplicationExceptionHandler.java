package com.api.ApplicationExceptionHandler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.api.DTO.APIError;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@ControllerAdvice
public class ApplicationExceptionHandler extends ResponseEntityExceptionHandler {

	//Método para adicionar com a anotação os excepcions . 
	//@ExceptionHandler(NullPointerException.class)
	//public ResponseEntity handleException(Exception e) {
	//	log.info("Dentro do Exception especifico para o null");
	//	return new ResponseEntity("Error controlado", HttpStatus.BAD_GATEWAY);
	//}
	
	//Agora tratando todos os exceptions de vez  
		@ExceptionHandler(Exception.class)
		public ResponseEntity handleException(Exception e) {
			log.info("Dentro do Exception");
			APIError error = new APIError(HttpStatus.BAD_GATEWAY.value(), "Error de processamento"); 
			
			return new ResponseEntity(error, HttpStatus.BAD_GATEWAY);
		}
	
	
}
