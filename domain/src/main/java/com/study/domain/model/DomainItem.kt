package com.study.domain.model

abstract class DomainItem {
    abstract fun getIdentifier() :String
    abstract override fun equals(other: Any?): Boolean
    override fun hashCode(): Int {
        return javaClass.hashCode()
    }
}