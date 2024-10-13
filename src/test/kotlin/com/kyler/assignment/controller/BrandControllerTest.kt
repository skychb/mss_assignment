package com.kyler.assignment.controller

import com.kyler.assignment.exception.ErrorCode
import com.kyler.assignment.exception.KylerException
import com.kyler.assignment.service.BrandService
import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers

@WebMvcTest(BrandController::class)
class BrandControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @MockkBean
    private lateinit var brandService: BrandService

    @Test
    fun `브랜드별 최저가가 정확하게 나온다`() {
        val response = mapOf(
            "brand" to "D",
            "categories" to listOf(
                mapOf("category" to "상의", "price" to 10100),
                mapOf("category" to "아우터", "price" to 5100),
                mapOf("category" to "바지", "price" to 3000),
                mapOf("category" to "스니커즈", "price" to 9500),
                mapOf("category" to "가방", "price" to 2500),
                mapOf("category" to "모자", "price" to 1500),
                mapOf("category" to "양말", "price" to 2400),
                mapOf("category" to "액세서리", "price" to 2000)
            ),
            "totalPrice" to 36100
        )

        every { brandService.getLowestPriceByBrand() } returns response

        mockMvc.perform(MockMvcRequestBuilders.get("/api/brands/lowestPrice"))
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.jsonPath("$.brand").value("D"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.totalPrice").value(36100))
    }

    @Test
    fun `exception 발생시 원하는 메시지가 나와야 한다`() {
        every { brandService.getLowestPriceByBrand() } throws KylerException(ErrorCode.BRAND_NOT_FOUND)

        mockMvc.perform(MockMvcRequestBuilders.get("/api/brands/lowestPrice"))
            .andExpect(MockMvcResultMatchers.status().isInternalServerError)
            .andExpect(MockMvcResultMatchers.jsonPath("$.error").value("존재하지 않는 브랜드입니다."))
    }
}
