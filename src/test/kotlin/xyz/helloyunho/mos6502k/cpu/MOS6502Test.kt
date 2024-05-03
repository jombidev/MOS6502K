package xyz.helloyunho.mos6502k.cpu

import org.junit.jupiter.api.Test
import xyz.helloyunho.mos6502k.enums.AddressingMode
import xyz.helloyunho.mos6502k.enums.Flags
import xyz.helloyunho.mos6502k.enums.Registers
import xyz.helloyunho.mos6502k.memory.BasicMemory
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class MOS6502Test {
    @Test
    fun `CPU Initialization Test`() {
        val mem = BasicMemory()
        val cpu = MOS6502(mem)
        assertEquals(cpu.status, 0b0010_0000u, "Initial CPU status test")

        // Registers test
        assertEquals(cpu.registers[Registers.A], 0u, "Initial CPU register test")
        assertEquals(cpu[Registers.X], 0u, "Initial CPU register test")
        assertEquals(cpu.registers[Registers.Y], 0u, "Initial CPU register test")
        assertEquals(cpu[Registers.S], 0xFDu, "Initial CPU register test")
        assertEquals(cpu.registers[Registers.P], cpu.status, "Initial CPU register test")
    }

    @Test
    fun `CPU Flag Input and Output Test`() {
        val mem = BasicMemory()
        val cpu = MOS6502(mem)
        assertEquals(cpu.status, 0b0010_0000u, "Initial CPU status test")

        assertTrue(cpu.getFlag(Flags.DONT_USE_THIS), "Initial CPU flag test")
        assertFalse(cpu.getFlag(Flags.Z), "Initial CPU flag test")

        assertFalse(cpu.getFlag(Flags.V), "Initial CPU flag test")

        cpu.setFlag(Flags.Z, to = true)
        cpu.setFlag(Flags.V)

        assertTrue(cpu.getFlag(Flags.Z), "Updated CPU flag test")
        assertTrue(cpu.getFlag(Flags.V), "Updated CPU flag test")

        cpu.setFlag(Flags.Z, to = false)
        assertEquals(cpu.status, 0b0110_0000u)
    }

    @Test
    fun `CPU Register Input and Output Test`() {
        val mem = BasicMemory()
        val cpu = MOS6502(mem)

        assertEquals(cpu.registers[Registers.A], 0u, "Initial CPU register test")
        cpu[Registers.A] = 0xEAu
        assertEquals(cpu[Registers.A], 0xEAu, "Initial CPU register test")
    }

    @OptIn(ExperimentalUnsignedTypes::class)
    @Test
    fun `Get Address Test`() {
        val mem = BasicMemory(ubyteArrayOf(0x2u, 0x0u, 0x4u, 0x0u, 0xEAu, 0xAEu, 0xCAu, 0xFEu))
        val cpu = MOS6502(mem)

        assertEquals(cpu.getAddress(AddressingMode.IMMEDIATE), 0x0u, "Immediate addressing mode test")
        assertEquals(cpu.getAddress(AddressingMode.ABSOLUTE), 0x2u, "Absolute addressing mode test")
        assertEquals(cpu.getAddress(AddressingMode.ZERO_PAGE), 0x2u, "Zero page addressing mode test")

        cpu.PC = 0x2u
        assertEquals(cpu.getAddress(AddressingMode.RELATIVE), 0x6u, "Relative addressing mode test")
        assertEquals(cpu.getAddress(AddressingMode.ABSOLUTE_INDIRECT), 0x4u, "Absolute indirect addressing mode test")

        cpu.PC = 0x4u
        cpu[Registers.X] = 0x4u
        cpu[Registers.Y] = 0x2u
        assertEquals(cpu.getAddress(AddressingMode.ABSOLUTE_INDEXED_X), 0xAEEEu, "Absolute indexed with X addressing mode test")
        assertEquals(cpu.getAddress(AddressingMode.ABSOLUTE_INDEXED_Y), 0xAEECu, "Absolute indexed with Y addressing mode test")
        assertEquals(cpu.getAddress(AddressingMode.ZERO_PAGE_INDEXED_X), 0xEEu, "Zero page indexed with X addressing mode test")
        assertEquals(cpu.getAddress(AddressingMode.ZERO_PAGE_INDEXED_Y), 0xECu, "Zero page indexed with Y addressing mode test")

        cpu.PC = 0x0u
        assertEquals(cpu.getAddress(AddressingMode.ZERO_PAGE_INDEXED_INDIRECT_X), 0xFECAu, "Zero page indirect indexed with X addressing mode test")
        assertEquals(cpu.getAddress(AddressingMode.ZERO_PAGE_INDEXED_INDIRECT_Y), 0x6u, "Zero page indirect indexed with Y addressing mode test")
    }
}