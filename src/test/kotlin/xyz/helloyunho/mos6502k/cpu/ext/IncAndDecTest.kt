package xyz.helloyunho.mos6502k.cpu.ext

import xyz.helloyunho.mos6502k.cpu.MOS6502
import xyz.helloyunho.mos6502k.enums.Registers
import xyz.helloyunho.mos6502k.memory.BasicMemory
import kotlin.test.Test
import kotlin.test.assertEquals

@OptIn(ExperimentalUnsignedTypes::class)
class IncAndDecTest {
    @Test
    fun testINC() {
        val mem = BasicMemory(ubyteArrayOf(0xEEu, 0x03u, 0x00u, 0x03u))
        val cpu = MOS6502(mem)

        cpu.step()
        assertEquals(cpu.memory[0x3u], 0x04u, "INC absolute test")
    }

    @Test
    fun testINX() {
        val mem = BasicMemory(ubyteArrayOf(0xE8u, 0x00u))
        val cpu = MOS6502(mem)

        cpu[Registers.X] = 0x01u
        cpu.step()
        assertEquals(cpu[Registers.X], 0x02u, "INX test")
    }

    @Test
    fun testINY() {
        val mem = BasicMemory(ubyteArrayOf(0xC8u, 0x00u))
        val cpu = MOS6502(mem)

        cpu[Registers.Y] = 0x01u
        cpu.step()
        assertEquals(cpu[Registers.Y], 0x02u, "INY test")
    }

    @Test
    fun testDEC() {
        val mem = BasicMemory(ubyteArrayOf(0xCEu, 0x03u, 0x00u, 0x01u))
        val cpu = MOS6502(mem)

        cpu.step()
        assertEquals(cpu.memory[0x3u], 0x00u, "DEC absolute test")
    }

    @Test
    fun testDEX() {
        val mem = BasicMemory(ubyteArrayOf(0xCAu, 0x00u))
        val cpu = MOS6502(mem)

        cpu[Registers.X] = 0x01u
        cpu.step()
        assertEquals(cpu[Registers.X], 0x00u, "DEX test")
    }

    @Test
    fun testDEY() {
        val mem = BasicMemory(ubyteArrayOf(0x88u, 0x00u))
        val cpu = MOS6502(mem)

        cpu[Registers.Y] = 0x01u
        cpu.step()
        assertEquals(cpu[Registers.Y], 0x00u, "DEY test")
    }

}