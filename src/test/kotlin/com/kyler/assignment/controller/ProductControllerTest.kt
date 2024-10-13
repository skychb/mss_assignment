package com.kyler.assignment.controller

import com.kyler.assignment.exception.ErrorCode
import com.kyler.assignment.exception.KylerException
import com.kyler.assignment.model.Category
import com.kyler.assignment.service.ProductService
import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers

@WebMvcTest(ProductController::class)
class ProductControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @MockkBean
    private lateinit var productService: ProductService

    @Test
    fun `카테고리별 최저가가 나와야 한다`() {
        val response = mapOf(
            "products" to listOf(
                mapOf("category" to Category.상의.name, "brand" to "C", "price" to 10000),
                mapOf("category" to Category.아우터.name, "brand" to "E", "price" to 5000),
                mapOf("category" to Category.바지.name, "brand" to "D", "price" to 3000),
                mapOf("category" to Category.스니커즈.name, "brand" to "G", "price" to 9000),
                mapOf("category" to Category.가방.name, "brand" to "A", "price" to 2000),
                mapOf("category" to Category.모자.name, "brand" to "D", "price" to 1500),
                mapOf("category" to Category.양말.name, "brand" to "I", "price" to 1700),
                mapOf("category" to Category.액세서리.name, "brand" to "F", "price" to 1900)
            ),
            "totalPrice" to 34100
        )

        every { productService.getLowestPriceByCategory() } returns response

        mockMvc.perform(MockMvcRequestBuilders.get("/api/products/lowestPrice"))
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.jsonPath("$.products[0].category").value(Category.상의.name))
            .andExpect(MockMvcResultMatchers.jsonPath("$.totalPrice").value(34100))
    }

    @Test
    fun `exception 발생시 원하는 메시지가 나와야 한다`() {
        every { productService.getLowestPriceByCategory() } throws KylerException(ErrorCode.NO_PRODUCT_FOR_CATEGORY)

        mockMvc.perform(MockMvcRequestBuilders.get("/api/products/lowestPrice"))
            .andExpect(MockMvcResultMatchers.status().isInternalServerError)
            .andExpect(MockMvcResultMatchers.jsonPath("$.error").value("카테고리에 해당하는 제품이 없습니다"))
    }
}
