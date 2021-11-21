package com.poly.dnshelper.model

import com.poly.dnshelper.Util.getBytesFromInt
import com.poly.dnshelper.Util.getBytesFromShort
import com.poly.dnshelper.Util.getIntFromBytes
import com.poly.dnshelper.Util.getShortFromTwoBytes

data class DNSAnswer(
    var name: String = "",
    var type: Short = 0,
    var dnsClass: Short = 0,
    var timeToLive: Int = 0,
    var dataLength: Short = 0,
    var resourceData: String = ""
) {
    constructor() : this(
        name = "",
        type = 0,
        dnsClass = 0,
        timeToLive = 0,
        dataLength = 0,
        resourceData = ""
    )

    fun getAnswerBytes(): List<Byte> {
        val resultArrayBytes = mutableListOf<Byte>()
        resultArrayBytes.addAll(name.toByteArray().toList())
        resultArrayBytes.addAll(getBytesFromShort(type))
        resultArrayBytes.addAll(getBytesFromShort(dnsClass))
        resultArrayBytes.addAll(getBytesFromInt(timeToLive))
        resultArrayBytes.addAll(getBytesFromShort(dataLength))
        resultArrayBytes.addAll(resourceData.toByteArray().toList())
        return resultArrayBytes
    }

    fun mapperAnswer(byteArray: ByteArray, domainName: String) {
        name = domainName
        type = getShortFromTwoBytes(byteArray[domainName.length + 1] to byteArray[domainName.length + 2])
        dnsClass = getShortFromTwoBytes(byteArray[domainName.length + 3] to byteArray[domainName.length + 4])
        timeToLive = getIntFromBytes(
            byteArray
                .toList()
                .subList(domainName.length + 5, domainName.length + 9)
                .toByteArray()
        )
        dataLength = getShortFromTwoBytes(byteArray[domainName.length + 9] to byteArray[domainName.length + 10])
        resourceData = String(
            byteArray
                .toList()
                .subList(domainName.length + 11, domainName.length + 11 + dataLength)
                .toByteArray()
        )
    }
}
