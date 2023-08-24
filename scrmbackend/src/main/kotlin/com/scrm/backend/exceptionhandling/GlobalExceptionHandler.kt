package com.scrm.backend.exceptionhandling

import com.scrm.backend.exceptionhandling.exceptions.NoElementUnderThisIdException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import javax.validation.ConstraintViolationException

/* Handles Exceptions globally -> Allows to use ExcHandler for multiple Controllers
*  -> Exception is thrown in method annotated with @RequestMapping -> this Handler is called*/
@ControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(HttpMessageNotReadableException::class)
    fun handleHttpMessageNotReadableException(ex: HttpMessageNotReadableException): ResponseEntity<Any?>? {
        val body: MutableMap<String, Any> = HashMap()
        body["errors"] = ex.message.toString()
        return ResponseEntity(body, HttpStatus.UNPROCESSABLE_ENTITY)
    }

    @ExceptionHandler(NoElementUnderThisIdException::class)
    fun handleNoElementUnderThisIdException(ex: NoElementUnderThisIdException): ResponseEntity<Any?>? {
        val body: MutableMap<String, Any> = HashMap()
        body["errors"] = ex.message.toString()
        return ResponseEntity(body, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleMethodArgumentNotValid(
        ex: MethodArgumentNotValidException
    ): ResponseEntity<Any?>? {
        val body: MutableMap<String, Any> = HashMap()
        body["errors"] = ex.bindingResult.fieldErrors.stream().map { it.defaultMessage }.toList()
        return ResponseEntity(body, HttpStatus.UNPROCESSABLE_ENTITY)
    }

    @ExceptionHandler(ConstraintViolationException::class)
    fun handleConstraintViolationException(
        ex: ConstraintViolationException
    ): ResponseEntity<Any?>? {
        val body: MutableMap<String, Any> = HashMap()
        body["errors"] = ex.message.toString()
        return ResponseEntity(body, HttpStatus.BAD_REQUEST)
    }
}
