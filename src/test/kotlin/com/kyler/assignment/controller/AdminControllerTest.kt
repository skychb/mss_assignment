package com.kyler.assignment.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.kyler.assignment.model.Category
import com.kyler.assignment.model.Product
import com.kyler.assignment.service.AdminService
import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import io.mockk.justRun
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers

@WebMvcTest(AdminController::class)
class AdminControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @MockkBean
    private lateinit var adminService: AdminService

    private val objectMapper = ObjectMapper()

    @Test
    fun `상품이 추가된다`() {
        val product = Product(1, "A", Category.상의, 11200)

        every { adminService.addProduct(product) } returns product

        mockMvc.perform(MockMvcRequestBuilders.post("/api/admin/product")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(product)))
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.jsonPath("$.brand").value("A"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.category").value("상의"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.price").value(11200))
    }

    @Test
    fun `상품이 수정된다`() {
        val product = Product(1, "A", Category.상의, 11200)
        val updatedProduct = product.copy(price = 12000)

        every { adminService.updateProduct(1, updatedProduct) } returns updatedProduct

        mockMvc.perform(MockMvcRequestBuilders.put("/api/admin/product/1")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(updatedProduct)))
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.jsonPath("$.price").value(12000))
    }

    @Test
    fun `상품이 삭제된다`() {
        justRun { adminService.deleteProduct(1) }

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/admin/product/1"))
            .andExpect(MockMvcResultMatchers.status().isNoContent)
    }
}
