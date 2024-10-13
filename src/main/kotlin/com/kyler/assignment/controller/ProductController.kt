package com.kyler.assignment.controller

import com.kyler.assignment.service.ProductService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/products")
class ProductController(
    private val productService: ProductService
) {
    @GetMapping("/lowestPrice")
    fun getLowestPriceByCategory(): ResponseEntity<Map<String, Any>> {
        return try {
            ResponseEntity.ok(productService.getLowestPriceByCategory())
        } catch (e: Exception) {
            val errorResponse = mapOf("error" to (e.message ?: "Unknown error"))
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse)
        }
    }
}