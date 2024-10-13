package com.kyler.assignment.controller

import com.kyler.assignment.service.BrandService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/brands")
class BrandController(val brandService: BrandService) {
    @GetMapping("/lowestPrice")
    fun getLowestPriceByBrand(): ResponseEntity<Map<String, Any>> {
        return try {
            ResponseEntity.ok(brandService.getLowestPriceByBrand())
        } catch (e: Exception) {
            val errorResponse = mapOf("error" to (e.message ?: "Unknown error"))
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse)
        }
    }
}
