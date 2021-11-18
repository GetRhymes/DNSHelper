package kotlin.com.poly.dnshelper.model

data class DNSFlags(
    private val isResponse: Boolean,
    private val opCode: Byte,
    private val aa: Boolean,
    private val truncated: Boolean,
    private val recursionDesired: Boolean,
    private val recursionAccepted: Boolean,
    private val rCode: Byte
) {
    private var bytes: Int = 0

    fun getBytes(): Short {
        bitOperation(isResponse, 4)
        bitOperation(opCode.toInt(), 1)
        bitOperation(aa, 1)
        bitOperation(truncated, 1)
        bitOperation(recursionDesired, 1)
        bitOperation(recursionAccepted, 4)
        bitOperation(0, 3)
        bitOperation(rCode.toInt())
        println(Integer.toBinaryString(bytes))
        return bytes.toShort()
    }

    private fun bitOperation(value: Boolean, shift: Int) {
        if (value) bytes = bytes.or(1)
        bytes = bytes.shl(shift)
    }

    private fun bitOperation(value: Int, shift: Int) {
        bytes = bytes.or(value)
        bytes = bytes.shl(shift)
    }

    private fun bitOperation(value: Int) {
        bytes = bytes.or(value)
    }
}
