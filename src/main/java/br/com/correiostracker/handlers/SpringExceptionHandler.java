package br.com.correiostracker.handlers;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.core.MethodParameter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.method.annotation.RequestBodyAdvice;

import lombok.RequiredArgsConstructor;


@ControllerAdvice
@RequiredArgsConstructor
public class SpringExceptionHandler implements RequestBodyAdvice {
	
	@ExceptionHandler(Exception.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public ResponseEntity<?> exceptionHandler(final Exception exception,
			final HttpServletRequest request) throws Exception {

		Map<String, String> errors = new HashMap<>();
		errors.put("status", HttpStatus.INTERNAL_SERVER_ERROR.toString());
		errors.put("timestamp", LocalDateTime.now().toString());
		errors.put("error", "Ocorreu um erro interno");
            
        exception.printStackTrace();
        
		return ResponseEntity.internalServerError().body(errors);
	}

	@Override
	public boolean supports(MethodParameter methodParameter, Type targetType,
			Class<? extends HttpMessageConverter<?>> converterType) {
		return true;
	}

	@Override
	public HttpInputMessage beforeBodyRead(HttpInputMessage inputMessage, MethodParameter parameter, Type targetType,
			Class<? extends HttpMessageConverter<?>> converterType) throws IOException {
		byte[] body = inputMessage.getBody().readAllBytes();

		DummyHttpInputMessage dummy = new DummyHttpInputMessage(body, inputMessage.getHeaders());

		return dummy;
	}

	@Override
	public Object afterBodyRead(Object body, HttpInputMessage inputMessage, MethodParameter parameter, Type targetType,
			Class<? extends HttpMessageConverter<?>> converterType) {
		return body;
	}

	@Override
	public Object handleEmptyBody(Object body, HttpInputMessage inputMessage, MethodParameter parameter,
			Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {
		return body;
	}

	private class DummyHttpInputMessage implements HttpInputMessage {
		private InputStream body;
		private HttpHeaders headers;

		DummyHttpInputMessage(byte[] bytes, HttpHeaders headers) {
			this.headers = headers;
			this.body = new ByteArrayInputStream(bytes);
		}

		@Override
		public HttpHeaders getHeaders() {
			return headers;
		}

		@Override
		public InputStream getBody() throws IOException {
			return body;
		}
	}
}

