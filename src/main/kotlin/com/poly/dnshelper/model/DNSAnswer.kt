package kotlin.com.poly.dnshelper.model

import com.poly.dnshelper.Util.getBytesFromInt
import com.poly.dnshelper.Util.getBytesFromShort

data class DNSAnswer(
    private val name: String,
    private val type: Short,
    private val dnsClass: Short,
    private val timeToLive: Int,
    private val dataLength: Short,
    private val resourceData: String
) {
    fun getAnswerBytes(): List<Byte> {
        val resultArrayBytes = mutableListOf<Byte>()
        resultArrayBytes.addAll(name.toByteArray().toList())
        resultArrayBytes.addAll(getBytesFromShort(type))
        resultArrayBytes.addAll(getBytesFromShort(dnsClass))
        resultArrayBytes.addAll(getBytesFromInt(timeToLive))
        resultArrayBytes.addAll(getBytesFromShort(dataLength))
        resultArrayBytes.addAll(resourceData.toByteArray().toList())
        return resultArrayBytes
    }
}
