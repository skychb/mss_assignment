package com.kyler.assignment.exception

import org.springframework.http.HttpStatus

class KylerException(code: ErrorCode, message: String? = null, status: HttpStatus? = null) :
    RuntimeException() {
    val errorCode: String = code.code
    override val message: String = message ?: code.message
    val status: HttpStatus = status ?: HttpStatus.BAD_REQUEST
}

enum class ErrorCode(
    val code: String,
    val message: String
) {
    UNDEFINED_ERROR("1000", "Bad request"),
    PRODUCT_NOT_FOUND("1001", "제품 정보가 존재하지 않습니다."),
    BRAND_NOT_FOUND("1002", "존재하지 않는 브랜드입니다."),
    CATEGORY_NOT_FOUND("1003", "존재하지 않는 카테고리입니다."),
    NO_PRODUCT_FOR_BRAND("1004", "브랜드에 해당하는 제품이 없습니다"),
    NO_PRODUCT_FOR_CATEGORY("1005", "카테고리에 해당하는 제품이 없습니다"),
}