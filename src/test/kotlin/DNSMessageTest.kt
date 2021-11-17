import com.poly.dnshelper.DNSFlags
import com.poly.dnshelper.DNSMessage
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
            transactionId = 23123,
            dnsFlags = dnsFlags,
            numOfQuestions = 5432,
            numOfAnswers = 23432,
            listOf(),
            listOf()
        )
        val finalArray = dnsMessage.getMessageBytes()

        finalArray.forEach {
            println(forPrint(it))
        }
    }

    private fun forPrint(byte: Byte): String {
        val binaryString = Integer.toBinaryString(byte.toInt())
        return if (binaryString.length > 16) {
            binaryString.substring(24)
        } else if (binaryString.length < 8) {
            val newString = StringBuilder()
            for (i in 0 until 8 - binaryString.length) {
                newString.append(0)
            }
            newString.append(binaryString).toString()
        } else binaryString
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