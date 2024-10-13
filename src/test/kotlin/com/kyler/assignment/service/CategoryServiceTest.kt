package com.kyler.assignment.service

import com.kyler.assignment.model.Category
import com.kyler.assignment.model.Product
import com.kyler.assignment.repository.ProductRepository
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class CategoryServiceTest {

    private val productRepository = mockk<ProductRepository>()
    private val categoryService = CategoryService(productRepository)

    @Test
    fun `카테고리별 최저가 최고가가 같이 노출되어야 한다`() {
        val products = listOf(
            Product(1, "C", Category.상의, 10000),
            Product(2, "B", Category.상의, 10500),
            Product(3, "I", Category.상의, 11400)
        )
        every { productRepository.findByCategory(Category.상의) } returns products

        val result = categoryService.getMinMaxPriceByCategory(Category.상의)

        val expected = mapOf(
            "카테고리" to Category.상의,
            "최저가" to mapOf("브랜드" to "C", "가격" to 10000),
            "최고가" to mapOf("브랜드" to "I", "가격" to 11400)
        )
        assertEquals(expected, result)
    }

    @Test
    fun `카테고리에 해당하는 상품이 없으면 exception이 발생하고 원하는 메시지가 나와야 한다`() {
        every { productRepository.findByCategory(Category.상의) } returns emptyList()

        val exception = assertThrows<RuntimeException> {
            categoryService.getMinMaxPriceByCategory(Category.상의)
        }

        assertEquals("카테고리에 해당하는 제품이 없습니다", exception.message)
    }
}
