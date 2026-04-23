package com.bookmyseat.movieservice.exception;

import com.bookmyseat.movieservice.dto.ErrorResponseDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GlobalExceptionHandlerTest {

    @InjectMocks
    private GlobalExceptionHandler globalExceptionHandler;

    @Mock
    private WebRequest webRequest;

    @Mock
    private BindingResult bindingResult;

    @BeforeEach
    void setUp() {
        when(webRequest.getDescription(false)).thenReturn("uri=/api/v1/movies");
    }

    @Test
    void testHandleMovieNotFoundException() {
        // Given
        String errorMessage = "Movie with id 1 not found";
        MovieNotFoundException exception = new MovieNotFoundException(errorMessage);

        // When
        ResponseEntity<ErrorResponseDTO> response = globalExceptionHandler
                .handleMovieNotFoundException(exception, webRequest);

        // Then
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());

        ErrorResponseDTO errorResponse = response.getBody();
        assertNotNull(errorResponse);
        assertEquals(404, errorResponse.getStatus());
        assertEquals("Not Found", errorResponse.getError());
        assertEquals(errorMessage, errorResponse.getMessage());
        assertEquals("/api/v1/movies", errorResponse.getPath());
    }

    @Test
    void testHandleMovieNotFoundExceptionWithNullMessage() {
        // Given
        MovieNotFoundException exception = new MovieNotFoundException(null);

        // When
        ResponseEntity<ErrorResponseDTO> response = globalExceptionHandler
                .handleMovieNotFoundException(exception, webRequest);

        // Then
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());

        ErrorResponseDTO errorResponse = response.getBody();
        assertNotNull(errorResponse);
        assertNull(errorResponse.getMessage());
    }

    @Test
    void testHandleValidationExceptions() {
        // Given
        MethodArgumentNotValidException exception = mock(MethodArgumentNotValidException.class);
        FieldError fieldError = new FieldError("movie", "title", "Title cannot be blank");

        when(exception.getBindingResult()).thenReturn(bindingResult);
        when(bindingResult.getAllErrors()).thenReturn(Collections.singletonList(fieldError));

        // When
        ResponseEntity<ErrorResponseDTO> response = globalExceptionHandler
                .handleValidationExceptions(exception, webRequest);

        // Then
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

        ErrorResponseDTO errorResponse = response.getBody();
        assertNotNull(errorResponse);
        assertEquals(400, errorResponse.getStatus());
        assertEquals("Validation Failed", errorResponse.getError());
        assertTrue(errorResponse.getMessage().contains("title"));
        assertTrue(errorResponse.getMessage().contains("Title cannot be blank"));
    }

    @Test
    void testHandleBindException() {
        // Given
        BindException exception = mock(BindException.class);
        FieldError fieldError = new FieldError("movie", "durationMinutes", "Duration must be positive");

        when(exception.getBindingResult()).thenReturn(bindingResult);
        when(bindingResult.getAllErrors()).thenReturn(Collections.singletonList(fieldError));

        // When
        ResponseEntity<ErrorResponseDTO> response = globalExceptionHandler
                .handleBindException(exception, webRequest);

        // Then
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

        ErrorResponseDTO errorResponse = response.getBody();
        assertNotNull(errorResponse);
        assertEquals(400, errorResponse.getStatus());
        assertEquals("Invalid Parameters", errorResponse.getError());
        assertTrue(errorResponse.getMessage().contains("durationMinutes"));
        assertTrue(errorResponse.getMessage().contains("Duration must be positive"));
    }

    @Test
    void testHandleTypeMismatchException() {
        // Given
        MethodParameter methodParameter = mock(MethodParameter.class);
        MethodArgumentTypeMismatchException exception = new MethodArgumentTypeMismatchException(
                "invalid_id", Long.class, "movieId", methodParameter, new NumberFormatException("For input string: \"invalid_id\"")
        );

        // When
        ResponseEntity<ErrorResponseDTO> response = globalExceptionHandler
                .handleTypeMismatchException(exception, webRequest);

        // Then
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

        ErrorResponseDTO errorResponse = response.getBody();
        assertNotNull(errorResponse);
        assertEquals(400, errorResponse.getStatus());
        assertEquals("Invalid Parameter Type", errorResponse.getError());
        assertTrue(errorResponse.getMessage().contains("invalid_id"));
        assertTrue(errorResponse.getMessage().contains("movieId"));
        assertTrue(errorResponse.getMessage().contains("Long"));
    }

    @Test
    void testHandleGlobalException() {
        // Given
        Exception exception = new RuntimeException("Unexpected error occurred");

        // When
        ResponseEntity<ErrorResponseDTO> response = globalExceptionHandler
                .handleGlobalException(exception, webRequest);

        // Then
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());

        ErrorResponseDTO errorResponse = response.getBody();
        assertNotNull(errorResponse);
        assertEquals(500, errorResponse.getStatus());
        assertEquals("Internal Server Error", errorResponse.getError());
        assertEquals("An unexpected error occurred. Please try again later.", errorResponse.getMessage());
        assertEquals("/api/v1/movies", errorResponse.getPath());
    }

    @Test
    void testHandleGlobalExceptionWithNullPointerException() {
        // Given
        NullPointerException exception = new NullPointerException("Null value encountered");

        // When
        ResponseEntity<ErrorResponseDTO> response = globalExceptionHandler
                .handleGlobalException(exception, webRequest);

        // Then
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());

        ErrorResponseDTO errorResponse = response.getBody();
        assertNotNull(errorResponse);
        assertEquals(500, errorResponse.getStatus());
        assertEquals("Internal Server Error", errorResponse.getError());
        assertEquals("An unexpected error occurred. Please try again later.", errorResponse.getMessage());
    }

    @Test
    void testHandleValidationExceptionsWithMultipleErrors() {
        // Given
        MethodArgumentNotValidException exception = mock(MethodArgumentNotValidException.class);
        FieldError fieldError1 = new FieldError("movie", "title", "Title cannot be blank");
        FieldError fieldError2 = new FieldError("movie", "durationMinutes", "Duration must be positive");

        when(exception.getBindingResult()).thenReturn(bindingResult);
        when(bindingResult.getAllErrors()).thenReturn(java.util.Arrays.asList(fieldError1, fieldError2));

        // When
        ResponseEntity<ErrorResponseDTO> response = globalExceptionHandler
                .handleValidationExceptions(exception, webRequest);

        // Then
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

        ErrorResponseDTO errorResponse = response.getBody();
        assertNotNull(errorResponse);
        assertTrue(errorResponse.getMessage().contains("title"));
        assertTrue(errorResponse.getMessage().contains("durationMinutes"));
    }

    @Test
    void testPathProcessingFromWebRequest() {
        // Given
        when(webRequest.getDescription(false)).thenReturn("uri=/api/v1/movies/123");
        MovieNotFoundException exception = new MovieNotFoundException("Test");

        // When
        ResponseEntity<ErrorResponseDTO> response = globalExceptionHandler
                .handleMovieNotFoundException(exception, webRequest);

        // Then
        ErrorResponseDTO errorResponse = response.getBody();
        assertNotNull(errorResponse);
        assertEquals("/api/v1/movies/123", errorResponse.getPath());
    }

    @Test
    void testErrorResponseDTOFieldsNotNull() {
        // Given
        MovieNotFoundException exception = new MovieNotFoundException("Test error");

        // When
        ResponseEntity<ErrorResponseDTO> response = globalExceptionHandler
                .handleMovieNotFoundException(exception, webRequest);

        // Then
        ErrorResponseDTO errorResponse = response.getBody();
        assertNotNull(errorResponse);
        assertNotNull(errorResponse.getStatus());
        assertNotNull(errorResponse.getError());
        assertNotNull(errorResponse.getMessage());
        assertNotNull(errorResponse.getPath());
    }

    @Test
    void testTypeMismatchExceptionWithNullValue() {
        // Given
        MethodParameter methodParameter = mock(MethodParameter.class);
        MethodArgumentTypeMismatchException exception = new MethodArgumentTypeMismatchException(
                null, Long.class, "movieId", methodParameter, new NumberFormatException("Null value")
        );

        // When
        ResponseEntity<ErrorResponseDTO> response = globalExceptionHandler
                .handleTypeMismatchException(exception, webRequest);

        // Then
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        ErrorResponseDTO errorResponse = response.getBody();
        assertNotNull(errorResponse);
        assertTrue(errorResponse.getMessage().contains("null"));
    }
}