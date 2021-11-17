package com.poly.dnshelper

import com.poly.dnshelper.Util.getLeftAndRightBytes

data class DNSAnswer(
    private val name: String,
    private val type: Short,
    private val dnsClass: Short,
    private val timeToLive: Short,
    private val dataLength: Short,
    private val resourceData: String
) {
    fun getAnswerBytes(): ByteArray {
        val resultArrayBytes = mutableListOf<Byte>()
        resultArrayBytes.addAll(name.toByteArray().toList())
        resultArrayBytes.addAll(getLeftAndRightBytes(type))
        resultArrayBytes.addAll(getLeftAndRightBytes(dnsClass))
        resultArrayBytes.addAll(getLeftAndRightBytes(timeToLive))
        resultArrayBytes.addAll(getLeftAndRightBytes(dataLength))
        resultArrayBytes.addAll(resourceData.toByteArray().toList())
        return resultArrayBytes.toByteArray()
    }
}
