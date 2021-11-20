package com.poly.dnshelper.model

import com.poly.dnshelper.Util.getBytesFromShort

data class DNSQuery(
    val name: String,
    val type: Short,
    val queryClass: Short
) {
    fun getQueryBytes(): List<Byte> {
        val resultArrayBytes = mutableListOf<Byte>()
        resultArrayBytes.addAll(name.toByteArray().toList())
        resultArrayBytes.addAll(getBytesFromShort(type))
        resultArrayBytes.addAll(getBytesFromShort(queryClass))
        return resultArrayBytes
    }
}
