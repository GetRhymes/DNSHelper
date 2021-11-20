package com.poly.dnshelper.model

import com.poly.dnshelper.Util.getBytesFromShort

data class DNSMessage(
    var transactionId: Short = 0, // 16 bits
    var dnsFlags: DNSFlags, // 16 bits
    var numOfQuestions: Short = 0, // 16 bits
    var answerRRs: Short = 0, // 16 bits
    var authorityRRs: Short = 0, // 16 bits
    var additionalRRs: Short = 0, // 16 bits
    var questions: List<DNSQuery> = listOf(),
    var answers: List<DNSAnswer> = listOf(),
) {
    constructor() : this(
        transactionId = 0,
        dnsFlags = DNSFlags(),
        numOfQuestions = 0,
        answerRRs = 0,
        authorityRRs = 0,
        additionalRRs = 0,
        questions = listOf(),
        answers = listOf()
    )

    fun getMessageBytes(): ByteArray {
        val resultArrayBytes = mutableListOf<Byte>()
        resultArrayBytes.addAll(getBytesFromShort(transactionId))
        resultArrayBytes.addAll(getBytesFromShort(dnsFlags.getBytes()))
        resultArrayBytes.addAll(getBytesFromShort(numOfQuestions))
        resultArrayBytes.addAll(getBytesFromShort(answerRRs))
        resultArrayBytes.addAll(getBytesFromShort(authorityRRs))
        resultArrayBytes.addAll(getBytesFromShort(additionalRRs))
        for (question in questions) {
            resultArrayBytes.addAll(question.getQueryBytes())
        }
        for (answer in answers) {
            resultArrayBytes.addAll(answer.getAnswerBytes())
        }
        return resultArrayBytes.toByteArray()
    }

    fun mapperMessage(byteArray: ByteArray) {
        for (i in byteArray.indices) {
            if (i == 1) {
                transactionId = getShortFromTwoBytes(byteArray[i - 1] to byteArray[i])
            }
            if (i == 3) {
                dnsFlags.mapperFlags(byteArray[i - 1] to byteArray[i])
            }
            if (i == 5) {
                numOfQuestions = getShortFromTwoBytes(byteArray[i - 1] to byteArray[i])
            }
            if (i == 7) {
                answerRRs = getShortFromTwoBytes(byteArray[i - 1] to byteArray[i])
            }
            if (i == 9) {
                authorityRRs = getShortFromTwoBytes(byteArray[i - 1] to byteArray[i])
            }
            if (i == 11) {
                additionalRRs = getShortFromTwoBytes(byteArray[i - 1] to byteArray[i])
            }
            if (i >= 12) {
                println("Questions: " + getStringBytes(bytes.get(i)))
            }
        }
    }

    private fun getShortFromTwoBytes(leftAndRightBytes: Pair<Byte, Byte>): Short {
        var result = leftAndRightBytes.first.toShort()
        result = result.toInt().shl(8).toShort()
        return (result + leftAndRightBytes.second).toShort()
    }
}
