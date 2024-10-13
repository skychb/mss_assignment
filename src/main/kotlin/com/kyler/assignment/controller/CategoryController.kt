package com.kyler.assignment.controller

import com.kyler.assignment.model.Category
import com.kyler.assignment.service.CategoryService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/categories")
class CategoryController(val categoryService: CategoryService) {
    @GetMapping("/price")
    fun getMinMaxPriceByCategory(@RequestParam category: Category): ResponseEntity<Map<String, Any>> {
        return try {
            ResponseEntity.ok(categoryService.getMinMaxPriceByCategory(category))
        } catch (e: Exception) {
            val errorResponse = mapOf("error" to (e.message ?: "Unknown error"))
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse)
        }
    }
}
