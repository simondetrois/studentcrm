package scrm.backend.backend.exceptionhandling.exceptions

class NoElementUnderThisIdException(message: String? = null, cause: Throwable? = null) : Exception(message, cause) {
    constructor(cause: Throwable) : this(null, cause)
}