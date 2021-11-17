package com.poly.dnshelper

internal object Util {
    fun getLeftAndRightBytes(value: Short): List<Byte> {
        val rightByte: Byte = value.toByte()
        val leftByte: Byte = value.toInt().shr(8).toByte()
        println("VALUE: $value; LEFT: $leftByte; RIGHT: $rightByte")
        return listOf(leftByte, rightByte)
    }
}