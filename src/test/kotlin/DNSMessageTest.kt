import com.poly.dnshelper.Util.parseToCorrectForm
import com.poly.dnshelper.model.DNSAnswer
import com.poly.dnshelper.model.DNSFlags
import com.poly.dnshelper.model.DNSMessage
import com.poly.dnshelper.model.DNSQuery
import org.junit.Test

class DNSMessageTest {

    @Test
    fun dnsMessageTest() {
        val dnsFlags = DNSFlags(
            isResponse = true,
            opCode = 1,
            aa = true,
            truncated = true,
            recursionDesired = false,
            recursionAccepted = true,
            rCode = 8
        )
        val dnsMessage = DNSMessage(
            transactionId = 10,
            dnsFlags = dnsFlags,
            numOfQuestions = 4,
            answerRRs = 3,
            authorityRRs = 2,
            additionalRRs = 1,
            listOf(
                DNSQuery(
                    name = "dns.dns.dns",
                    type = 1,
                    queryClass = 1
                )
            ),
            listOf()
        )
        val dnsMessageWithAnswer = DNSMessage(
            transactionId = 10,
            dnsFlags = dnsFlags,
            numOfQuestions = 4,
            answerRRs = 3,
            authorityRRs = 2,
            additionalRRs = 1,
            listOf(
                DNSQuery(
                    name = "dns.dns.dns",
                    type = 1,
                    queryClass = 1
                )
            ),
            listOf(
                DNSAnswer(
                    name = "dns.dns.dns",
                    type = 1,
                    dnsClass = 1,
                    timeToLive = 2,
                    dataLength = 15,
                    resourceData = "192.192.192.192"
                )
            )
        )
        val finalArray = dnsMessage.getMessageBytes()
        val finalArrayWithMessage = dnsMessageWithAnswer.getMessageBytes()
//        finalArray.forEach {
//            forPrint(it)
//        }
        finalArrayWithMessage.forEach {
            parseToCorrectForm(it)
        }
        val newDns = DNSMessage()
        newDns.mapperMessage(finalArray, 0)
        println(newDns)
        val newDnsWithAnswer = DNSMessage()
        newDnsWithAnswer.mapperMessage(finalArrayWithMessage, 0, dnsMessage)
        println(newDnsWithAnswer)

//        printItLikeWireShark(finalArray, false)

    }

    private fun printItLikeWireShark(bytes: ByteArray, isAnswer: Boolean) {
        if (isAnswer) {
            throw UnsupportedOperationException()
        }
        for (i in bytes.indices) {
            if (i < 2) {
                println("Trans ID: " + getStringBytes(bytes[i]))
            }
            if (i in 2..3) {
                println("Flags: " + getStringBytes(bytes[i]))
            }
            if (i in 4..5) {
                println("Questions number: " + getStringBytes(bytes[i]))
            }
            if (i in 6..7) {
                println("AnswerRRs: " + getStringBytes(bytes[i]))
            }
            if (i in 8..9) {
                println("AuthorityRRs: " + getStringBytes(bytes[i]))
            }
            if (i in 10..11) {
                println("AdditionalRRs: " + getStringBytes(bytes[i]))
            }
            if (i >= 12) {
                println("Questions: " + getStringBytes(bytes[i]))
            }
        }
    }

    private fun getStringBytes(byte: Byte): String {
        return parseToCorrectForm(byte)
    }
}