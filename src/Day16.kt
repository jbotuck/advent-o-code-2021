fun main() {
    //returns the packets to number of bits read
    fun getPackets(bitString: String, packetCount: Int? = null): Pair<List<Packet>, Int> {
        if (packetCount == 0 || bitString.isEmpty()) return emptyList<Packet>() to 0

        val firstVersion = bitString.take(3).toInt(2)
        if (bitString.slice(3..5) == "100") {
            var nextBit = 6
            var hasMoreBits = true
            var bitsAccumulator = ""
            while (hasMoreBits) {
                hasMoreBits = bitString[nextBit++] == '1'
                bitsAccumulator += bitString.substring(nextBit, nextBit + 4)
                nextBit += 4
            }
            val firstPacket = LiteralPacket(firstVersion, bitsAccumulator.toLong(2))
            val (remainingPackets, remainingPacketBits) = getPackets(
                bitString.substring(nextBit),
                packetCount?.minus(1)
            )
            return listOf(firstPacket) + remainingPackets to nextBit + remainingPacketBits

        } else if (bitString[6] == '0') {
            val length = bitString.slice(7..21).toInt(2)
            val packets = getPackets(bitString.substring(22).take(length)).first
            val (remainingPackets, remainingPacketBits) = getPackets(
                bitString.substring(22 + length),
                packetCount?.minus(1)
            )
            return listOf(
                operationToBuilder[bitString.slice(3..5).toInt(2)]!!(
                    firstVersion,
                    packets
                )
            ) + remainingPackets to 22 + length + remainingPacketBits
        } else {
            val subPacketCount = bitString.slice(7..17).toInt(2)
            val (packets, packetBits) = getPackets(bitString.substring(18), subPacketCount)
            val (remainingPackets, remainingPacketBits) = getPackets(
                bitString.substring(18 + packetBits),
                packetCount?.minus(1)
            )
            return listOf(
                operationToBuilder[bitString.slice(3..5).toInt(2)]!!(
                    firstVersion,
                    packets
                )
            ) + remainingPackets to 18 + packetBits + remainingPacketBits
        }
    }

    fun part1(input: List<String>): Long {
        val bitString = input.first().map {
            it.toString().toInt(16).toString(2).padStart(4, '0')
        }.joinToString("")
        val packet = getPackets(bitString, 1).first.first()
        return packet.sumVersions()
    }


    fun part2(input: List<String>): Long {
        val bitString = input.first().map {
            it.toString().toInt(16).toString(2).padStart(4, '0')
        }.joinToString("")
        val packet = getPackets(bitString, 1).first.first()
        return packet.evaluate()
    }
    // test if implementation meets criteria from the description, like:
//    val testInput = readInput("Day15_test")
//    check(part2(testInput) == 315L)

    val input = readInput("Day16")
    println(part1(input))
    println(part2(input))
}

sealed class Packet(val version: Int) {
    abstract fun sumVersions(): Long
    abstract fun evaluate(): Long
}

abstract class OperatorPacket(version: Int, val packets: List<Packet>) : Packet(version) {
    override fun sumVersions() = version + packets.sumOf { it.sumVersions() }
}

class LiteralPacket(version: Int, var value: Long) : Packet(version) {
    override fun sumVersions(): Long = version.toLong()
    override fun evaluate() = value
}

class SumPacket(version: Int, packets: List<Packet>) : OperatorPacket(version, packets) {
    override fun evaluate() = packets.sumOf { it.evaluate() }
}

class ProductPacket(version: Int, packets: List<Packet>) : OperatorPacket(version, packets) {
    override fun evaluate() = packets.map { it.evaluate() }.reduce(Long::times)
}

class MinPacket(version: Int, packets: List<Packet>) : OperatorPacket(version, packets) {
    override fun evaluate() = packets.minOf { it.evaluate() }
}

class MaxPacket(version: Int, packets: List<Packet>) : OperatorPacket(version, packets) {
    override fun evaluate() = packets.maxOf { it.evaluate() }
}

class GreaterThanPacket(version: Int, packets: List<Packet>) : OperatorPacket(version, packets) {
    override fun evaluate(): Long = if (packets.first().evaluate() > packets.last().evaluate()) 1 else 0
}

class LessThanPacket(version: Int, packets: List<Packet>) : OperatorPacket(version, packets) {
    override fun evaluate(): Long = if (packets.first().evaluate() < packets.last().evaluate()) 1 else 0
}

class EqualPacket(version: Int, packets: List<Packet>) : OperatorPacket(version, packets) {
    override fun evaluate(): Long = if (packets.first().evaluate() == packets.last().evaluate()) 1 else 0
}

val operationToBuilder = mapOf(
    0 to ::SumPacket,
    1 to ::ProductPacket,
    2 to ::MinPacket,
    3 to ::MaxPacket,
    5 to ::GreaterThanPacket,
    6 to ::LessThanPacket,
    7 to ::EqualPacket
)

