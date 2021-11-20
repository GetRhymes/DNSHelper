package com.poly.dnshelper.model

import com.poly.dnshelper.Util.getBytesFromShort
import com.poly.dnshelper.Util.getShortFromTwoBytes

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

    fun mapperMessage(byteArray: ByteArray, sizeMessage: Int) {
        val nameSize = sizeMessage - 24
        transactionId = getShortFromTwoBytes(byteArray[0] to byteArray[1])
        dnsFlags.mapperFlags(byteArray[2] to byteArray[3])
        numOfQuestions = getShortFromTwoBytes(byteArray[4] to byteArray[5])
        answerRRs = getShortFromTwoBytes(byteArray[6] to byteArray[7])
        authorityRRs = getShortFromTwoBytes(byteArray[8] to byteArray[9])
        additionalRRs = getShortFromTwoBytes(byteArray[10] to byteArray[11])
        val dnsQuery = DNSQuery()
        dnsQuery.mapperQuery(byteArray.toList().subList(12, byteArray.size).toByteArray())
        questions = listOf(dnsQuery)
    }

//    override fun toString(): String {
//        return """
//            transactionId: Short = $transactionId
//            dnsFlags: DNSFlags = $dnsFlags
//            numOfQuestions: Short = $numOfQuestions
//            answerRRs: Short = $answerRRs
//            authorityRRs: Short = $authorityRRs
//            additionalRRs: Short = $additionalRRs
//            questions: List<DNSQuery> = $questions
//            answers: List<DNSAnswer> = listOf()
//        """.trimIndent()
//    }
}
