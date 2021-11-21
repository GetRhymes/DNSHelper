package com.poly.dnshelper.model

import com.poly.dnshelper.Util.getBytesFromShort
import com.poly.dnshelper.Util.getShortFromTwoBytes

data class DNSQuery(
    var name: String,
    var type: Short,
    var queryClass: Short
) {
    constructor() : this(
        name = "",
        type = 0,
        queryClass = 0
    )

    fun getQueryBytes(): List<Byte> {
        val resultArrayBytes = mutableListOf<Byte>()
        resultArrayBytes.addAll(name.toByteArray().toList())
        resultArrayBytes.addAll(getBytesFromShort(type))
        resultArrayBytes.addAll(getBytesFromShort(queryClass))
        return resultArrayBytes
    }

    fun mapperQuery(byteArray: ByteArray) {
        type = getShortFromTwoBytes(byteArray[byteArray.size - 4] to byteArray[byteArray.size - 3])
        queryClass = getShortFromTwoBytes(byteArray[byteArray.size - 2] to byteArray[byteArray.size - 1])
        name = String(byteArray.toList().subList(0, byteArray.size - 4).toByteArray())
    }
}
