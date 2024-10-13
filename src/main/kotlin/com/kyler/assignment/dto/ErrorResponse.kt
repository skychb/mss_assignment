package com.kyler.assignment.dto

data class ErrorResponse(
    var code: String,
    var message: String? = null,
    var data: ErrorDataList? = null
)

data class ErrorDataList(
    val errors: List<ErrorData>?
)

data class ErrorData(
    var key: String,
    var message: List<String?>
)