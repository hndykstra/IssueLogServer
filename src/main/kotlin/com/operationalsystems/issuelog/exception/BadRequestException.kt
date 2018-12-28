package com.operationalsystems.issuelog.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

/**
 * Exception annotated with HTTP response code
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
class BadRequestException(msg: String) : Exception(msg) {
}