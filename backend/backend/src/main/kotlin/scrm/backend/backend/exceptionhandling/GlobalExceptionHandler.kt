package scrm.backend.backend.exceptionhandling

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import scrm.backend.backend.exceptionhandling.exceptions.NoElementUnderThisIdException

@ControllerAdvice
class GlobalExceptionHandler  {

    @ExceptionHandler(HttpMessageNotReadableException::class)
    fun handleHttpMessageNotReadableException(ex: HttpMessageNotReadableException): ResponseEntity<Any?> {
        val body: MutableMap<String, Any> = HashMap()
        body["errors"] = ex.message.toString()
        return ResponseEntity(body, HttpStatus.UNPROCESSABLE_ENTITY)
    }

    @ExceptionHandler(NoElementUnderThisIdException::class)
    fun handleNoElementUnderThisIdException(ex: NoElementUnderThisIdException): ResponseEntity<Any?> {
        val body: MutableMap<String, Any> = HashMap()
        body["errors"] = ex.message.toString()
        return ResponseEntity(body, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleMethodArgumentNotValid(
        ex: MethodArgumentNotValidException
    ): ResponseEntity<Any?> {
        val body: MutableMap<String, Any> = HashMap()
        body["errors"] = ex.bindingResult.fieldErrors.stream().map { it.defaultMessage }.toList()
        return ResponseEntity(body, HttpStatus.UNPROCESSABLE_ENTITY)
    }
}
