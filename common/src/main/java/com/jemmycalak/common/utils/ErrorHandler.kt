package com.jemmycalak.common.utils

import com.jemmycalak.model.ErrorResponse

data class ErrorHandler(
    val errorType: ErrorType,
    val errorResponse: ErrorResponse?
) {
    enum class ErrorType {
        SNACKBAR,
        LAYOUT
    }
}