package xyz.helloyunho.mos6502k.cpu.ext

import xyz.helloyunho.mos6502k.cpu.MOS6502
import xyz.helloyunho.mos6502k.enums.Flags
import xyz.helloyunho.mos6502k.memory.BasicMemory
import kotlin.test.Test
import kotlin.test.assertEquals

@OptIn(ExperimentalUnsignedTypes::class)
class BranchTest {
    @Test
    fun testBCC() {
        val mem = BasicMemory(ubyteArrayOf(0x90u, 0x02u, 0x00u))
        val cpu = MOS6502(mem)
        cpu[Flags.C] = false
        cpu.step()
        assertEquals(cpu.PC, 0x03u, "BCC test")
    }

    @Test
    fun testBCS() {
        val mem = BasicMemory(ubyteArrayOf(0xB0u, 0x02u, 0x00u))
        val cpu = MOS6502(mem)
        cpu[Flags.C] = true
        cpu.step()
        assertEquals(cpu.PC, 0x03u, "BCS test")
    }

    @Test
    fun testBNE() {
        val mem = BasicMemory(ubyteArrayOf(0xD0u, 0x02u, 0x00u))
        val cpu = MOS6502(mem)
        cpu[Flags.Z] = false
        cpu.step()
        assertEquals(cpu.PC, 0x03u, "BNE test")
    }

    @Test
    fun testBEQ() {
        val mem = BasicMemory(ubyteArrayOf(0xF0u, 0x02u, 0x00u))
        val cpu = MOS6502(mem)
        cpu[Flags.Z] = true
        cpu.step()
        assertEquals(cpu.PC, 0x03u, "BEQ test")
    }

    @Test
    fun testBPL() {
        val mem = BasicMemory(ubyteArrayOf(0x10u, 0x02u, 0x00u))
        val cpu = MOS6502(mem)
        cpu[Flags.N] = false
        cpu.step()
        assertEquals(cpu.PC, 0x03u, "BPL test")
    }

    @Test
    fun testBMI() {
        val mem = BasicMemory(ubyteArrayOf(0x30u, 0x02u, 0x00u))
        val cpu = MOS6502(mem)
        cpu[Flags.N] = true
        cpu.step()
        assertEquals(cpu.PC, 0x03u, "BMI test")
    }

    @Test
    fun testBVC() {
        val mem = BasicMemory(ubyteArrayOf(0x50u, 0x02u, 0x00u))
        val cpu = MOS6502(mem)
        cpu[Flags.V] = false
        cpu.step()
        assertEquals(cpu.PC, 0x03u, "BVC test")
    }

    @Test
    fun testBVS() {
        val mem = BasicMemory(ubyteArrayOf(0x70u, 0x02u, 0x00u))
        val cpu = MOS6502(mem)
        cpu[Flags.V] = true
        cpu.step()
        assertEquals(cpu.PC, 0x03u, "BVS test")
    }

}