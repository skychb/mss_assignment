package com.kyler.assignment.service

import com.kyler.assignment.exception.ErrorCode
import com.kyler.assignment.exception.KylerException
import com.kyler.assignment.model.Product
import com.kyler.assignment.repository.ProductRepository
import org.springframework.stereotype.Service

@Service
class AdminService(val productRepository: ProductRepository) {
    fun addProduct(product: Product): Product = productRepository.save(product)

    fun updateProduct(id: Long, product: Product): Product {
        val existingProduct = productRepository.findById(id).orElseThrow { KylerException(ErrorCode.PRODUCT_NOT_FOUND) }
        existingProduct.brand = product.brand
        existingProduct.category = product.category
        existingProduct.price = product.price

        return productRepository.save(existingProduct)
    }

    fun deleteProduct(id: Long) {
        productRepository.deleteById(id)
    }
}
