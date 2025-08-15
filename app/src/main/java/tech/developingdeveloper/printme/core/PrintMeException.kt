package tech.developingdeveloper.printme.core

class PrintMeException : Exception {
    override val message: String
        get() = super.message ?: "Something went wrong."

    constructor(message: String) : super(message)

    constructor(cause: Throwable) : super(cause)
}
