package com.kyler.assignment.controller

import com.kyler.assignment.model.Product
import com.kyler.assignment.service.AdminService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/admin")
class AdminController(val adminService: AdminService) {
    @PostMapping("/product")
    fun addProduct(@RequestBody product: Product): ResponseEntity<Product> {
        return ResponseEntity.ok(adminService.addProduct(product))
    }

    @PutMapping("/product/{id}")
    fun updateProduct(@PathVariable id: Long, @RequestBody product: Product): ResponseEntity<Product> {
        return ResponseEntity.ok(adminService.updateProduct(id, product))
    }

    @DeleteMapping("/product/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteProduct(@PathVariable id: Long){
        adminService.deleteProduct(id)
    }
}
