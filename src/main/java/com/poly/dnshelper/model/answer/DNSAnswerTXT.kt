package com.poly.dnshelper.model.answer

class DNSAnswerTXT(
    var txtLength: Byte = 0,
    name: String = "",
    type: Short = 0,
    dnsClass: Short = 0,
    timeToLive: Int = 0,
    dataLength: Short = 0,
    resourceData: ByteArray = byteArrayOf()
) : DNSAnswer(name, type, dnsClass, timeToLive, dataLength, resourceData) {

    override fun getAnswerBytes(): List<Byte> {
        val newList = super.getAnswerBytes().toMutableList()
        newList.add(txtLength)
        newList.addAll(resourceData.toList())
        return newList
    }

    override fun mapperAnswer(byteArray: ByteArray) {
        super.mapperAnswer(byteArray)
        val sizeName = 2
        txtLength = byteArray[sizeName + 10]
        resourceData = byteArray
            .toList()
            .subList(sizeName + 11, sizeName + 11 + dataLength)
            .toByteArray()
    }
}