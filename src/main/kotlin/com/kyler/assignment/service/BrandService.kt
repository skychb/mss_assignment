package com.kyler.assignment.service

import com.kyler.assignment.exception.ErrorCode
import com.kyler.assignment.exception.KylerException
import com.kyler.assignment.model.Category
import com.kyler.assignment.repository.ProductRepository
import org.springframework.stereotype.Service

@Service
class BrandService(val productRepository: ProductRepository) {
    fun getLowestPriceByBrand(): Map<String, Any> {
        val allProducts = productRepository.findAll()
        val productsByBrand = allProducts.groupBy { it.brand }

        val brandPrices = productsByBrand.map { (brand, products) ->
            val lowestPricesByCategory = Category.values().mapNotNull { category ->
                products.filter { it.category == category }.minByOrNull { it.price }
            }
            brand to lowestPricesByCategory.sumOf { it.price }
        }.toMap()

        val lowestBrand = brandPrices.minByOrNull { it.value }
            ?: throw KylerException(ErrorCode.BRAND_NOT_FOUND)

        val products = Category.values().mapNotNull { category ->
            productsByBrand[lowestBrand.key]?.filter { it.category == category }?.minByOrNull { it.price }
        }

        if (products.isEmpty()) {
            throw KylerException(ErrorCode.NO_PRODUCT_FOR_BRAND)
        }
        return mapOf(
            "브랜드" to lowestBrand.key,
            "카테고리" to products.map { mapOf("카테고리" to it.category, "가격" to it.price) },
            "총액" to lowestBrand.value
        )
    }
}
