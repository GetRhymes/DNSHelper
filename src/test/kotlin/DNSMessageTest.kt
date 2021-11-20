import com.poly.dnshelper.model.DNSFlags
import com.poly.dnshelper.model.DNSMessage
import com.poly.dnshelper.model.DNSQuery
import org.junit.Test

class DNSMessageTest {

    @Test
    fun getFinalArrayTest() {
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
        val finalArray = dnsMessage.getMessageBytes()

//        printItLikeWireShark(finalArray, false)

        finalArray.forEach {
            forPrint(it)
        }
    }

    private fun forPrint(byte: Byte): String {
        val binaryString = Integer.toBinaryString(byte.toInt())
        return if (binaryString.length > 16) {
            println("Correct form: ${binaryString.substring(24)} Origin form: $binaryString Value: $byte")
            return binaryString.substring(24)
        } else if (binaryString.length < 8) {
            val newString = StringBuilder()
            for (i in 0 until 8 - binaryString.length) {
                newString.append(0)
            }
            newString.append(binaryString).toString()
            println("Correct form: $newString Origin form: $binaryString Value: $byte")
            return newString.toString()
        } else {
            println("Correct form: $binaryString Origin form: $binaryString Value: $byte")
            binaryString
        }
    }

    private fun printItLikeWireShark(bytes: ByteArray, isAnswer: Boolean) {
        if (isAnswer) {
            throw UnsupportedOperationException()
        }
        for (i in bytes.indices) {
            if (i < 2) {
                println("Trans ID: " + getStringBytes(bytes.get(i)))
            }
            if (i in 2..3) {
                println("Flags: " + getStringBytes(bytes.get(i)))
            }
            if (i in 4..5) {
                println("Questions number: " + getStringBytes(bytes.get(i)))
            }
            if (i in 6..7) {
                println("AnswerRRs: " + getStringBytes(bytes.get(i)))
            }
            if (i in 8..9) {
                println("AuthorityRRs: " + getStringBytes(bytes.get(i)))
            }
            if (i in 10..11) {
                println("AdditionalRRs: " + getStringBytes(bytes.get(i)))
            }
            if (i >= 12) {
                println("Questions: " + getStringBytes(bytes.get(i)))
            }
        }
    }

    private fun getStringBytes(byte: Byte): String {
        return forPrint(byte)
    }
//    private val transactionId: Short, // 16 bits
//    private val dnsFlags: DNSFlags, // 16 bits
//    private val numOfQuestions: Short, // 16 bits
//    private val numOfAnswers: Short, // 16 bits
//    private val answers: List<DNSAnswer>,
//    private val questions: List<DNSQuery>,
//    private val numOfAuthority: Short = 0,
//    private val numOfAdditional: Short = 0,
}