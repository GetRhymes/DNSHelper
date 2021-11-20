package com.poly.dnshelper.model

import com.poly.dnshelper.Util.parseToCorrectForm

data class DNSFlags(
    var isResponse: Boolean,
    var opCode: Byte,
    var aa: Boolean,
    var truncated: Boolean,
    var recursionDesired: Boolean,
    var recursionAccepted: Boolean,
    var rCode: Byte
) {
    constructor() : this(
        isResponse = false,
        opCode = 0,
        aa = false,
        truncated = false,
        recursionDesired = false,
        recursionAccepted = false,
        rCode = 0
    )

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

    fun mapperFlags(leftAndRightByte: Pair<Byte, Byte>) {
        setLeftByte(leftAndRightByte.first)
        setRightByte(leftAndRightByte.second)
    }

    private fun setLeftByte(byte: Byte) {
        val binaryString = parseToCorrectForm(byte)
        if (binaryString[0] == '1') {
            isResponse = true
        }
        opCode = Integer.parseInt(binaryString.substring(1, 5), 2).toByte()
        if (binaryString[5] == '1') {
            aa = true
        }
        if (binaryString[6] == '1') {
            truncated = true
        }
        if (binaryString[7] == '1') {
            recursionDesired = true
        }
    }

    private fun setRightByte(byte: Byte) {
        val binaryString = parseToCorrectForm(byte)
        if (binaryString[0] == '1') {
            recursionAccepted = true
        }
        rCode = Integer.parseInt(binaryString.substring(4, 8), 2).toByte()
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

//    override fun toString(): String {
//        return """
//            isResponse: Boolean = $isResponse
//            opCode: Byte = $opCode
//            aa: Boolean $aa
//            truncated: Boolean $truncated
//            recursionDesired: Boolean = $recursionDesired
//            recursionAccepted: Boolean = $recursionAccepted
//            rCode: Byte = $rCode
//        """.trimIndent()
//    }
}
