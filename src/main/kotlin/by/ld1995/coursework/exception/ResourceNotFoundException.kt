package by.ld1995.coursework.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(HttpStatus.NOT_FOUND)
class ResourceNotFoundException(val resourceName: String, val fieldName: String,val fieldValue: String)
    : RuntimeException("$resourceName not found with $fieldName : $fieldValue")