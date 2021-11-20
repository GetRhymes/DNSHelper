import com.poly.dnshelper.model.DNSFlags
import org.junit.Assert
import org.junit.Test

class DNSFlagsTest {

    @Test
    fun getFinalResultTest() {
        val dnsFlags = DNSFlags(
            isResponse = true,
            opCode = 1,
            aa = true,
            truncated = true,
            recursionDesired = false,
            recursionAccepted = true,
            rCode = 8
        )
        val bytes = dnsFlags.getBytes()
//        println(bytes)
//        println(bytes.toShort())
//        println(Integer.toBinaryString(bytes))
//        println(Integer.toBinaryString(bytes.toShort().toInt()))
//        var byte: Byte = 0
//        byte = bytes.toByte()
//        println(Integer.toBinaryString(byte.toShort().toInt()))
        Assert.assertEquals("1000111010001000", Integer.toBinaryString(bytes.toInt()).substring(16))
    }
}