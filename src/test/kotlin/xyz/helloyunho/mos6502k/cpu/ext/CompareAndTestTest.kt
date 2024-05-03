package xyz.helloyunho.mos6502k.cpu.ext

import xyz.helloyunho.mos6502k.cpu.MOS6502
import xyz.helloyunho.mos6502k.enums.Registers
import xyz.helloyunho.mos6502k.memory.BasicMemory
import kotlin.test.Test
import kotlin.test.assertEquals

@OptIn(ExperimentalUnsignedTypes::class)
class CompareAndTestTest {
    @Test
    fun testCMP() {
        val mem = BasicMemory(ubyteArrayOf(0xC9u, 0x01u, 0x00u))
        val cpu = MOS6502(mem)

        cpu[Registers.A] = 0x01u
        cpu.step()
        assertEquals(cpu[Registers.A], 0x01u, "CMP immediate test")
    }

    @Test
    fun testCPX() {
        val mem = BasicMemory(ubyteArrayOf(0xE0u, 0x01u, 0x00u))
        val cpu = MOS6502(mem)

        cpu[Registers.X] = 0x01u
        cpu.step()
        assertEquals(cpu[Registers.X], 0x01u, "CPX immediate test")
    }

    @Test
    fun testCPY() {
        val mem = BasicMemory(ubyteArrayOf(0xc0u, 0x01u, 0x00u))
        val cpu = MOS6502(mem)

        cpu[Registers.Y] = 0x01u
        cpu.step()
        assertEquals(cpu[Registers.Y], 0x01u, "CPY immediate test")
    }

    @Test
    fun testBIT() {
        val mem = BasicMemory(ubyteArrayOf(0x24u, 0x01u, 0x00u))
        val cpu = MOS6502(mem)

        cpu[Registers.A] = 0x01u
        cpu.step()
        assertEquals(cpu[Registers.A], 0x01u, "BIT zero page test")
    }

}