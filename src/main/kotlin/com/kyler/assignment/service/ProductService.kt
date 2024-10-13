package com.kyler.assignment.service

import com.kyler.assignment.exception.ErrorCode
import com.kyler.assignment.exception.KylerException
import com.kyler.assignment.model.Category
import com.kyler.assignment.repository.ProductRepository
import org.springframework.stereotype.Service

@Service
class ProductService(val productRepository: ProductRepository) {
    fun getLowestPriceByCategory(): Map<String, Any> {
        val categories = Category.values()
        val result = categories.map { category ->
            val products = productRepository.findByCategory(category)
            val lowestPriceProduct = products.minByOrNull { it.price }
                ?: throw KylerException(ErrorCode.NO_PRODUCT_FOR_CATEGORY)
            mapOf("카테고리" to category, "브랜드" to lowestPriceProduct.brand, "가격" to lowestPriceProduct.price)
        }
        val totalPrice = result.sumOf { it["가격"] as Int }
        return mapOf("products" to result, "총액" to totalPrice)
    }
}
