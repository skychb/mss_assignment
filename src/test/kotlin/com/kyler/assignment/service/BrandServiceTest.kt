package com.kyler.assignment.service

import com.kyler.assignment.model.Category
import com.kyler.assignment.model.Product
import com.kyler.assignment.repository.ProductRepository
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class BrandServiceTest {

    private val productRepository = mockk<ProductRepository>()
    private val brandService = BrandService(productRepository)

    @Test
    fun `브랜드별 존재하는 카테고리의 최저가가 나와야 한다`() {
        val products = listOf(
            Product(id = 1, brand = "A", category = Category.상의, price = 10000),
            Product(id = 2, brand = "A", category = Category.아우터, price = 5000),
            Product(id = 3, brand = "A", category = Category.바지, price = 3000),
            Product(id = 4, brand = "A", category = Category.스니커즈, price = 9000),
            Product(id = 5, brand = "A", category = Category.가방, price = 2000),
            Product(id = 6, brand = "A", category = Category.모자, price = 1500),
            Product(id = 7, brand = "A", category = Category.양말, price = 1700),
            Product(id = 8, brand = "A", category = Category.액세서리, price = 1900),
            Product(id = 9, brand = "B", category = Category.상의, price = 11000),
            Product(id = 10, brand = "B", category = Category.아우터, price = 7000),
            Product(id = 11, brand = "B", category = Category.바지, price = 4000),
            Product(id = 12, brand = "B", category = Category.스니커즈, price = 10000),
            Product(id = 13, brand = "B", category = Category.가방, price = 3000),
            Product(id = 14, brand = "B", category = Category.모자, price = 2000),
            Product(id = 15, brand = "B", category = Category.양말, price = 2200),
            Product(id = 16, brand = "B", category = Category.액세서리, price = 2500),
            Product(id = 17, brand = "B", category = Category.액세서리, price = 2800),
            Product(id = 18, brand = "A", category = Category.양말, price = 2300),

        )

        every { productRepository.findAll() } returns products

        val result = brandService.getLowestPriceByBrand()

        val expected = mapOf(
            "브랜드" to "A",
            "카테고리" to listOf(
                mapOf("카테고리" to Category.상의, "가격" to 10000),
                mapOf("카테고리" to Category.아우터, "가격" to 5000),
                mapOf("카테고리" to Category.바지, "가격" to 3000),
                mapOf("카테고리" to Category.스니커즈, "가격" to 9000),
                mapOf("카테고리" to Category.가방, "가격" to 2000),
                mapOf("카테고리" to Category.모자, "가격" to 1500),
                mapOf("카테고리" to Category.양말, "가격" to 1700),
                mapOf("카테고리" to Category.액세서리, "가격" to 1900)
            ),
            "총액" to 34100
        )

        assertEquals(expected["브랜드"], result["브랜드"])
        assertEquals(expected["카테고리"], result["카테고리"])
        assertEquals(expected["총액"], result["총액"])
    }

    @Test
    fun `브랜드로 검색시 결과값이 없으면 Exception이 발생하고 원하는 메시지가 노출되어야 한다`() {
        every { productRepository.findAll() } returns emptyList()

        val exception = assertThrows<RuntimeException> {
            brandService.getLowestPriceByBrand()
        }

        assertEquals("존재하지 않는 브랜드입니다.", exception.message)
    }
}
