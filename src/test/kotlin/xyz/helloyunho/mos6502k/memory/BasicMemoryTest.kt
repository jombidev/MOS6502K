@file:OptIn(ExperimentalUnsignedTypes::class)

package xyz.helloyunho.mos6502k.memory

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class BasicMemoryTest {
    @Test
    fun testBasicMemoryInitialization() {
        // maximum size
        val allMem = BasicMemory()
        assertEquals(0xFFFF, allMem.size, "Maximum sized memory count test")

        // limited size
        val limitMem = BasicMemory(0xFFFu)
        assertEquals(0xFFF, limitMem.size, "Limited size memory count test")

        // custom data
        val data = UByteArray(4)
        data[0] = 0xCAu
        data[1] = 0xFEu
        data[2] = 0xBAu
        data[3] = 0xBEu // 0xCAFEBABE lol
        val customMem = BasicMemory(data)

        assertEquals(data.size, customMem.size, "Memory with custom data count test")
        assertEquals(data[0], customMem[0u], "Memory with custom data equality test")
        assertEquals(data[1], customMem.read(1u), "Memory with custom data equality test")
        assertEquals(data[2], customMem[2u], "Memory with custom data equality test")
        assertEquals(data[3], customMem[3u], "Memory with custom data equality test")
    }

    @Test
    fun testBasicMemoryModification() {
        val mem = BasicMemory(4u)
        mem[0u] = 0xAAu
        assertEquals(0xAAu.toUByte(), mem[0u], "Memory value test")

        mem.write(0u, 0xEAu)
        assertEquals(0xEAu.toUByte(), mem[0u], "Memory value test (after mod)")
    }

    @Test
    fun testBasicMemory16BitIO() {
        val data = ubyteArrayOf(0xEAu, 0xAAu, 0x10u, 0x01u)
        val mem = BasicMemory(data)

        assertEquals(0xAAEAu.toUShort(), mem.read2Bytes(0x0u), "Memory 16 bit read test")
        assertEquals(0x0110u.toUShort(), mem.read2Bytes(0x2u), "Memory 16 bit read test")

        mem.write(0x1u, 0xAEDAu)
        assertEquals(0xDAu.toUByte(), mem.read(0x1u), "Memory 16 bit value conversion test")
        assertEquals(0xAEu.toUByte(), mem.read(0x2u), "Memory 16 bit value conversion test")
    }
}