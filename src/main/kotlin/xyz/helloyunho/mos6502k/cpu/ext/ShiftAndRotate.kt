package xyz.helloyunho.mos6502k.cpu.ext

import xyz.helloyunho.mos6502k.cpu.MOS6502
import xyz.helloyunho.mos6502k.enums.AddressingMode
import xyz.helloyunho.mos6502k.enums.Flags
import xyz.helloyunho.mos6502k.enums.Registers
import xyz.helloyunho.mos6502k.shl
import xyz.helloyunho.mos6502k.shr

/// Shifts the bits of the value at the specified address left by one bit.
fun MOS6502.ASL(mode: AddressingMode? = null) {
    val (addr, value) =
        if (mode != null) getAddressAndMovePC(mode).let { it to memory[it] }
        else null to this[Registers.A]

    val result = value shl 1
    val carry = value shr 7

    this[Flags.C] = carry != 0u.toUByte()
    this[Flags.Z] = result == 0u.toUByte()
    this[Flags.N] = result and 0b1000_0000u != 0u.toUByte()

    if (addr != null) {
        memory[addr] = result
    } else {
        this[Registers.A] = result
    }
}

/// Shifts the bits of the value at the specified address right by one bit.
fun MOS6502.LSR(mode: AddressingMode? = null) {
    val (addr, value) =
        if (mode != null) getAddressAndMovePC(mode).let { it to memory[it] }
        else null to this[Registers.A]

    val result = value shr 1
    val carry = value shl 7

    this[Flags.C] = carry != 0u.toUByte()
    this[Flags.Z] = result == 0u.toUByte()
    this[Flags.N] = result and 0b1000_0000u != 0u.toUByte()

    if (addr != null) {
        memory[addr] = result
    } else {
        this[Registers.A] = result
    }
}

/// Rotates the bits of the value at the specified address left by one bit.
fun MOS6502.ROL(mode: AddressingMode? = null) {
    val (addr, value) =
        if (mode != null) getAddressAndMovePC(mode).let { it to memory[it] }
        else null to this[Registers.A]

    val carry = value shr 7
    val result = value shl 1 or carry

    this[Flags.C] = carry != 0u.toUByte()
    this[Flags.Z] = result == 0u.toUByte()
    this[Flags.N] = result and 0b1000_0000u != 0u.toUByte()

    if (addr != null) {
        memory[addr] = result
    } else {
        this[Registers.A] = result
    }
}

/// Rotates the bits of the value at the specified address right by one bit.
fun MOS6502.ROR(mode: AddressingMode? = null) {
    val (addr, value) =
        if (mode != null) getAddressAndMovePC(mode).let { it to memory[it] }
        else null to this[Registers.A]

    val carry = value shl 7
    val result = value shr 1 or carry

    this[Flags.C] = carry != 0u.toUByte()
    this[Flags.Z] = result == 0u.toUByte()
    this[Flags.N] = result and 0b1000_0000u != 0u.toUByte()

    if (addr != null) {
        memory[addr] = result
    } else {
        this[Registers.A] = result
    }
}
