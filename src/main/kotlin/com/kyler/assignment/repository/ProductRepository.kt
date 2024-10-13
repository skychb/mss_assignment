package com.kyler.assignment.repository

import com.kyler.assignment.model.Category
import com.kyler.assignment.model.Product
import org.springframework.data.jpa.repository.JpaRepository

interface ProductRepository : JpaRepository<Product, Long> {
    fun findByCategory(category: Category): List<Product>
    fun findByBrand(brand: String): List<Product>
}
