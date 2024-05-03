package xyz.helloyunho.mos6502k.cpu.ext

import xyz.helloyunho.mos6502k.cpu.MOS6502
import xyz.helloyunho.mos6502k.enums.Flags
import xyz.helloyunho.mos6502k.enums.Registers
import xyz.helloyunho.mos6502k.memory.BasicMemory
import kotlin.test.Test
import kotlin.test.assertEquals

@OptIn(ExperimentalUnsignedTypes::class)
class ArithmeticTest {

    @Test
    fun `ADC Test`() {
        val mem = BasicMemory(ubyteArrayOf(0x69u, 0x01u, 0x00u))
        val cpu = MOS6502(mem)

        cpu[Registers.A] = 0x01u
        cpu.step()
        assertEquals(cpu[Registers.A], 0x02u, "ADC immediate test")
    }

    @Test
    fun `SBC Test`() {
        val mem = BasicMemory(ubyteArrayOf(0xE9u, 0x01u, 0x00u))
        val cpu = MOS6502(mem)

        cpu[Registers.A] = 0x01u
        cpu[Flags.C] = true
        cpu.step()
        assertEquals(cpu[Registers.A], 0x00u, "SBC immediate test")
    }
}