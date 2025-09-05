package tech.developingdeveloper.printme.core

class PrintMeException : Exception {
    constructor(message: String) : super(message)

    constructor(cause: Throwable) : super(cause.message)

    override val message: String
        get() = super.message ?: "Something went wrong."
}
