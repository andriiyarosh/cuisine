package com.work.cuisine.mapper

interface Mapper<M, E> {
    fun mapToModel(entity: E): M
    fun mapToModel(entity: List<E>): List<M>
    fun mapToEntity(model: M): E
    fun mapToEntity(model: List<M>): List<E>
}