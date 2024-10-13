package com.kyler.assignment.service

import com.kyler.assignment.model.Category
import com.kyler.assignment.model.Product
import com.kyler.assignment.repository.ProductRepository
import io.mockk.every
import io.mockk.justRun
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.util.*

class AdminServiceTest {

    private val productRepository = mockk<ProductRepository>()
    private val adminService = AdminService(productRepository)

    @Test
    fun `상품이 추가된다`() {
        val product = Product(1, "A", Category.상의, 11200)

        every { productRepository.save(product) } returns product

        val result = adminService.addProduct(product)

        assertEquals(product, result)
    }

    @Test
    fun `상품이 수정된다`() {
        val product = Product(1, "A", Category.상의, 11200)
        val updatedProduct = product.copy(price = 12000)

        every { productRepository.findById(1) } returns Optional.of(product)
        every { productRepository.save(updatedProduct) } returns updatedProduct

        val result = adminService.updateProduct(1, updatedProduct)

        assertEquals(updatedProduct, result)
    }

    @Test
    fun `존재하지 않는 상품을 추가하고자 할때 Exception이 발생하고 메시지는 의도한 것이어야 한다`() {
        val product = Product(1, "A", Category.상의, 11200)
        val updatedProduct = product.copy(price = 12000)

        every { productRepository.findById(1) } returns Optional.empty()

        val exception = assertThrows<RuntimeException> {
            adminService.updateProduct(1, updatedProduct)
        }

        assertEquals("제품 정보가 존재하지 않습니다.", exception.message)
    }

    @Test
    fun `상품이 삭제된다`() {
        justRun { productRepository.deleteById(1) }

        adminService.deleteProduct(1)

        verify { productRepository.deleteById(1) }
    }
}
