package xyz.helloyunho.mos6502k.cpu.ext

import xyz.helloyunho.mos6502k.cpu.MOS6502
import xyz.helloyunho.mos6502k.enums.AddressingMode
import xyz.helloyunho.mos6502k.enums.Flags
import xyz.helloyunho.mos6502k.enums.Registers

/// Adds the value at the specified address to the accumulator.
fun MOS6502.ADC(mode: AddressingMode) {
    val addr = getAddressAndMovePC(mode)
    val value = memory[addr]
    val uint16sum = (this[Registers.A].toUShort() + value.toUShort() + (if (this[Flags.C]) 1u else 0u).toUShort()).toUShort()
    val uint8sum = (uint16sum and 0xFFu.toUShort()).toUByte()

    this[Flags.N] = (uint16sum and 0b1000_0000u) != 0u.toUShort()
    this[Flags.V] = ((this[Registers.A] xor uint8sum) and (value xor uint8sum) and 0b1000_0000u) != 0u.toUByte()
    this[Flags.Z] = uint8sum == 0u.toUByte()
    this[Flags.C] = uint16sum > 0xFFu

    this[Registers.A] = uint8sum
}

/// Subtracts the value at the specified address from the accumulator.
fun MOS6502.SBC(mode: AddressingMode) {
    val addr = getAddressAndMovePC(mode)
    val value = memory[addr]
    val uint16sum: UShort = (this[Registers.A].toUShort() - value.toUShort() - (if (this[Flags.C]) 0u else 1u).toUShort()).toUShort()
    val uint8sum = (uint16sum and 0xFFu.toUShort()).toUByte()

    this[Flags.N] = uint16sum and 0b1000_0000u != 0u.toUShort()
    this[Flags.V] = (this[Registers.A] xor uint8sum) and (value.inv() xor uint8sum) and 0b1000_0000u != 0u.toUByte()
    this[Flags.Z] = uint8sum == 0u.toUByte()
    this[Flags.C] = uint16sum > 0xFFu

    this[Registers.A] = uint8sum
}