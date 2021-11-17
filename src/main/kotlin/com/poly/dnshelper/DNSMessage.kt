package com.poly.dnshelper

import com.poly.dnshelper.Util.getLeftAndRightBytes

data class DNSMessage(
    private val transactionId: Short, // 16 bits
    private val dnsFlags: DNSFlags, // 16 bits
    private val numOfQuestions: Short, // 16 bits
    private val numOfAnswers: Short, // 16 bits
    private val answers: List<DNSAnswer>,
    private val questions: List<DNSQuery>,
    private val numOfAuthority: Short = 0,
    private val numOfAdditional: Short = 0,
) {
    fun getMessageBytes(): ByteArray {
        val resultArrayBytes = mutableListOf<Byte>()
        resultArrayBytes.addAll(getLeftAndRightBytes(transactionId))
        resultArrayBytes.addAll(getLeftAndRightBytes(dnsFlags.getBytes()))
        resultArrayBytes.addAll(getLeftAndRightBytes(numOfQuestions))
        resultArrayBytes.addAll(getLeftAndRightBytes(numOfAnswers))
        resultArrayBytes.addAll(getLeftAndRightBytes(numOfAuthority))
        resultArrayBytes.addAll(getLeftAndRightBytes(numOfAdditional))
        return resultArrayBytes.toByteArray()
    }
}
