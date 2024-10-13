package com.kyler.assignment.service

import com.kyler.assignment.model.Category
import com.kyler.assignment.model.Product
import com.kyler.assignment.repository.ProductRepository
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class ProductServiceTest {

    private val productRepository = mockk<ProductRepository>()
    private val productService = ProductService(productRepository)

    @Test
    fun `카테고리별 최저가가 나와야 한다`() {
        val 상의 = listOf(
            Product(1, "C", Category.상의, 10000),
            Product(2, "B", Category.상의, 10500),
            Product(3, "A", Category.상의, 11200)
        )
        val 아우터 = listOf(
            Product(4, "E", Category.아우터, 5000),
            Product(5, "D", Category.아우터, 5100),
            Product(6, "C", Category.아우터, 6200)
        )
        val 바지 = listOf(
            Product(7, "D", Category.바지, 3000),
            Product(8, "C", Category.바지, 3300),
            Product(9, "B", Category.바지, 3800)
        )
        val 스니커즈 = listOf(
            Product(10, "G", Category.스니커즈, 9000),
            Product(11, "A", Category.스니커즈, 9100),
            Product(12, "C", Category.스니커즈, 9200)
        )
        val 가방 = listOf(
            Product(13, "A", Category.가방, 2000),
            Product(14, "B", Category.가방, 2100),
            Product(15, "C", Category.가방, 2200)
        )
        val 모자 = listOf(
            Product(16, "D", Category.모자, 1500),
            Product(17, "F", Category.모자, 1600),
            Product(18, "A", Category.모자, 1700)
        )
        val 양말 = listOf(
            Product(19, "I", Category.양말, 1700),
            Product(20, "H", Category.양말, 2000),
            Product(21, "E", Category.양말, 2100)
        )
        val 액세서리 = listOf(
            Product(22, "F", Category.액세서리, 1900),
            Product(23, "D", Category.액세서리, 2000),
            Product(24, "C", Category.액세서리, 2100)
        )

        every { productRepository.findByCategory(Category.상의) } returns 상의
        every { productRepository.findByCategory(Category.아우터) } returns 아우터
        every { productRepository.findByCategory(Category.바지) } returns 바지
        every { productRepository.findByCategory(Category.스니커즈) } returns 스니커즈
        every { productRepository.findByCategory(Category.가방) } returns 가방
        every { productRepository.findByCategory(Category.모자) } returns 모자
        every { productRepository.findByCategory(Category.양말) } returns 양말
        every { productRepository.findByCategory(Category.액세서리) } returns 액세서리

        val result = productService.getLowestPriceByCategory()

        val expected = mapOf(
            "products" to listOf(
                mapOf("카테고리" to Category.상의, "브랜드" to "C", "가격" to 10000),
                mapOf("카테고리" to Category.아우터, "브랜드" to "E", "가격" to 5000),
                mapOf("카테고리" to Category.바지, "브랜드" to "D", "가격" to 3000),
                mapOf("카테고리" to Category.스니커즈, "브랜드" to "G", "가격" to 9000),
                mapOf("카테고리" to Category.가방, "브랜드" to "A", "가격" to 2000),
                mapOf("카테고리" to Category.모자, "브랜드" to "D", "가격" to 1500),
                mapOf("카테고리" to Category.양말, "브랜드" to "I", "가격" to 1700),
                mapOf("카테고리" to Category.액세서리, "브랜드" to "F", "가격" to 1900)
            ),
            "총액" to 34100
        )
        print(result)
        assertEquals(expected, result)
    }

    @Test
    fun `카테고리에 해당하는 상품이 없으면 exception이 발생하고 원하는 메시지가 나와야 한다`() {
        every { productRepository.findByCategory(Category.상의) } returns emptyList()

        val exception = assertThrows<RuntimeException> {
            productService.getLowestPriceByCategory()
        }

        assertEquals("카테고리에 해당하는 제품이 없습니다", exception.message)
    }
}
