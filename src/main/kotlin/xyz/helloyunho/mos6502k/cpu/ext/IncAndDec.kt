package xyz.helloyunho.mos6502k.cpu.ext

import xyz.helloyunho.mos6502k.cpu.MOS6502
import xyz.helloyunho.mos6502k.enums.AddressingMode
import xyz.helloyunho.mos6502k.enums.Flags
import xyz.helloyunho.mos6502k.enums.Registers

/// Increments the value at the specified address by one.
fun MOS6502.INC(mode: AddressingMode) {
    val addr = getAddressAndMovePC(mode)
    val value = (memory[addr] + 1u.toUByte()).toUByte()

    this[Flags.N] = value and 0b1000_0000u != 0u.toUByte()
    this[Flags.Z] = value == 0u.toUByte()

    memory[addr] = value
}

/// Increments the X register by one.
fun MOS6502.INX() {
    val value = (this[Registers.X] + 1u.toUByte()).toUByte()

    this[Flags.N] = value and 0b1000_0000u != 0u.toUByte()
    this[Flags.Z] = value == 0u.toUByte()

    this[Registers.X] = value
}

/// Increments the Y register by one.
fun MOS6502.INY() {
    val value = (this[Registers.Y] + 1u.toUByte()).toUByte()

    this[Flags.N] = value and 0b1000_0000u != 0u.toUByte()
    this[Flags.Z] = value == 0u.toUByte()

    this[Registers.Y] = value
}

/// Decrements the value at the specified address by one.
fun MOS6502.DEC(mode: AddressingMode) {
    val addr = getAddressAndMovePC(mode)
    val value = (memory[addr] - 1u.toUByte()).toUByte()

    this[Flags.N] = value and 0b1000_0000u != 0u.toUByte()
    this[Flags.Z] = value == 0u.toUByte()

    memory[addr] = value
}

/// Decrements the X register by one.
fun MOS6502.DEX() {
    val value = (this[Registers.X] - 1u.toUByte()).toUByte()

    this[Flags.N] = value and 0b1000_0000u != 0u.toUByte()
    this[Flags.Z] = value == 0u.toUByte()

    this[Registers.X] = value
}

/// Decrements the Y register by one.
fun MOS6502.DEY() {
    val value = (this[Registers.Y] - 1u.toUByte()).toUByte()

    this[Flags.N] = value and 0b1000_0000u != 0u.toUByte()
    this[Flags.Z] = value == 0u.toUByte()

    this[Registers.Y] = value
}
