package com.kyler.assignment.service

import com.kyler.assignment.exception.ErrorCode
import com.kyler.assignment.exception.KylerException
import com.kyler.assignment.model.Category
import com.kyler.assignment.repository.ProductRepository
import org.springframework.stereotype.Service

@Service
class CategoryService(val productRepository: ProductRepository) {
    fun getMinMaxPriceByCategory(category: Category): Map<String, Any> {
        val products = productRepository.findByCategory(category)

        if(products.isEmpty()) {
            throw KylerException(ErrorCode.NO_PRODUCT_FOR_CATEGORY)
        }
        val minPriceProduct = products.minByOrNull { it.price }
        val maxPriceProduct = products.maxByOrNull { it.price }
        return mapOf(
            "카테고리" to category,
            "최저가" to mapOf("브랜드" to minPriceProduct?.brand, "가격" to minPriceProduct?.price),
            "최고가" to mapOf("브랜드" to maxPriceProduct?.brand, "가격" to maxPriceProduct?.price)
        )
    }
}
