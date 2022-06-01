package tech.developingdeveloper.printme.printerlist.ui

data class PrinterUiItem(
    val name: String,
    val isAcceptingJobs: PrinterIsAcceptingJobs
) {
    enum class PrinterIsAcceptingJobs(val value: String) {
        NOT_ACCEPTING_JOBS("Not Accepting Jobs"), ACCEPTING_JOBS("Accepting Jobs")
    }
}
