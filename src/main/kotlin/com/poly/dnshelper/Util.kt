package com.poly.dnshelper

import kotlin.com.poly.dnshelper.model.DNSQuery

internal object Util {
    fun getBytesFromShort(value: Short): List<Byte> {
        val rightByte: Byte = value.toByte()
        val leftByte: Byte = value.toInt().shr(8).toByte()
        println("VALUE: $value; LEFT: $leftByte; RIGHT: $rightByte")
        return listOf(leftByte, rightByte)
    }

    fun getBytesFromInt(value: Int): List<Byte> {
        return listOf(
            value.shr(24).toByte(),
            value.shr(16).toByte(),
            value.shr(8).toByte(),
            value.toByte());
    }

    fun getBytesFromQuery(query: DNSQuery): List<Byte> {
        return query.getQueryBytes()
    }
}