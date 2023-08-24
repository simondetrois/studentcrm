package com.scrm.backend.exceptionhandling.exceptions

import java.lang.Exception

class NoElementUnderThisIdException(message: String? = null, cause: Throwable? = null) : Exception(message, cause) {
    constructor(cause: Throwable) : this(null, cause)
}