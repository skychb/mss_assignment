package com.kyler.assignment.model

import jakarta.persistence.*

// Brand, Category는 분명히 다른 attr가 존재하여 테이블을 따로 관리하겠지만, 여기선 다른 clue가 없다고 보고 enum 처리 합니다.

@Entity
data class Product(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,
    var brand: String,
    @Enumerated(EnumType.STRING)
    var category: Category,
    var price: Int
)
