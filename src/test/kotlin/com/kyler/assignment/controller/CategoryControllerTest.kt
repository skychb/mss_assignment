package com.kyler.assignment.controller

import com.kyler.assignment.exception.ErrorCode
import com.kyler.assignment.exception.KylerException
import com.kyler.assignment.model.Category
import com.kyler.assignment.service.CategoryService
import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers

@WebMvcTest(CategoryController::class)
class CategoryControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @MockkBean
    private lateinit var categoryService: CategoryService

    @Test
    fun `카테고리별 최저가 최고가가 나와야 한다`() {
        val response = mapOf(
            "category" to "상의",
            "minPrice" to mapOf("brand" to "C", "price" to 10000),
            "maxPrice" to mapOf("brand" to "I", "price" to 11400)
        )

        every { categoryService.getMinMaxPriceByCategory(Category.상의) } returns response

        mockMvc.perform(MockMvcRequestBuilders.get("/api/categories/price")
            .param("category", "상의"))
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.jsonPath("$.category").value("상의"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.minPrice.brand").value("C"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.minPrice.price").value(10000))
            .andExpect(MockMvcResultMatchers.jsonPath("$.maxPrice.brand").value("I"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.maxPrice.price").value(11400))
    }

    @Test
    fun `exception 발생시 원하는 메시지가 나와야 한다`() {
        every { categoryService.getMinMaxPriceByCategory(Category.상의) } throws KylerException(ErrorCode.NO_PRODUCT_FOR_CATEGORY)

        mockMvc.perform(MockMvcRequestBuilders.get("/api/categories/price")
            .param("category", "상의"))
            .andExpect(MockMvcResultMatchers.status().isInternalServerError)
            .andExpect(MockMvcResultMatchers.jsonPath("$.error").value("카테고리에 해당하는 제품이 없습니다"))
    }
}
