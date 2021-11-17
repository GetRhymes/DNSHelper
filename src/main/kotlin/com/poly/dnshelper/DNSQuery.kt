package com.poly.dnshelper

import com.poly.dnshelper.Util.getLeftAndRightBytes

data class DNSQuery(
    val name: String,
    val type: Short,
    val queryClass: Short
) {
    fun getQueryBytes(): ByteArray {
        val resultArrayBytes = mutableListOf<Byte>()
        resultArrayBytes.addAll(name.toByteArray().toList())
        resultArrayBytes.addAll(getLeftAndRightBytes(type))
        resultArrayBytes.addAll(getLeftAndRightBytes(queryClass))
        return resultArrayBytes.toByteArray()
    }
}
