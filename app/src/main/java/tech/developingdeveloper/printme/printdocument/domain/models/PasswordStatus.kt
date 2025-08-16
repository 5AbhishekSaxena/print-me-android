package tech.developingdeveloper.printme.printdocument.domain.models

sealed class PasswordStatus {
    object None : PasswordStatus()

    sealed class Password(
        open val password: String,
    ) : PasswordStatus() {
        data class Unknown(
            override val password: String,
        ) : Password(password)

        data class Incorrect(
            override val password: String,
        ) : Password(password)

        data class Correct(
            override val password: String,
        ) : Password(password)
    }
}
