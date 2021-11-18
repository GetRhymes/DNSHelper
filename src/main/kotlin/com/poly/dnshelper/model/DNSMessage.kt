package kotlin.com.poly.dnshelper.model

import com.poly.dnshelper.Util.getBytesFromShort

data class DNSMessage(
    private val transactionId: Short, // 16 bits
    private val dnsFlags: DNSFlags, // 16 bits
    private val numOfQuestions: Short, // 16 bits
    private val answerRRs: Short, // 16 bits
    private val authorityRRs: Short, // 16 bits
    private val additionalRRs: Short, // 16 bits
    private val questions: List<DNSQuery>,
    private val answers: List<DNSAnswer>,
) {
    fun getMessageBytes(): List<Byte> {
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
        return resultArrayBytes
    }
}
