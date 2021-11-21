package com.poly.dnshelper.model

import com.poly.dnshelper.Util.getBytesFromShort
import com.poly.dnshelper.Util.getShortFromTwoBytes

data class DNSMessage(
    var transactionId: Short, // 16 bits
    var dnsFlags: DNSFlags, // 16 bits
    var numOfQuestions: Short, // 16 bits
    var answerRRs: Short, // 16 bits
    var authorityRRs: Short, // 16 bits
    var additionalRRs: Short, // 16 bits
    var questions: List<DNSQuery>,
    var answers: List<DNSAnswer>,
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

    fun mapperMessage(byteArray: ByteArray, sizeMessage: Int, prevMessage: DNSMessage? = null) {
//        val nameSize = sizeMessage - 24
        transactionId = getShortFromTwoBytes(byteArray[0] to byteArray[1])
        dnsFlags.mapperFlags(byteArray[2] to byteArray[3])
        numOfQuestions = getShortFromTwoBytes(byteArray[4] to byteArray[5])
        answerRRs = getShortFromTwoBytes(byteArray[6] to byteArray[7])
        authorityRRs = getShortFromTwoBytes(byteArray[8] to byteArray[9])
        additionalRRs = getShortFromTwoBytes(byteArray[10] to byteArray[11])
        val sizeQuestions = prevMessage?.getMessageBytes()?.size ?: byteArray.size
        val dnsQuery = DNSQuery()
        dnsQuery.mapperQuery(byteArray.toList().subList(12, sizeQuestions).toByteArray())
        questions = listOf(dnsQuery)
        if(prevMessage != null) {
            val dnsAnswer = DNSAnswer()
            dnsAnswer.mapperAnswer(
                byteArray.toList().subList(prevMessage.getMessageBytes().size - 1, byteArray.size).toByteArray(),
                prevMessage.questions[0].name
            )
            answers = listOf(dnsAnswer)
        }
    }
}
