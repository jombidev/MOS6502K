@file:Suppress("unused", "FunctionName")

package xyz.helloyunho.mos6502k.cpu.ext

import xyz.helloyunho.mos6502k.cpu.MOS6502
import xyz.helloyunho.mos6502k.enums.AddressingMode
import xyz.helloyunho.mos6502k.enums.Flags
import xyz.helloyunho.mos6502k.enums.Registers

/// Loads the value at the specified address into the accumulator.
fun MOS6502.LDA(mode: AddressingMode) {
    val addr = getAddressAndMovePC(mode)
    val data = memory[addr]
    this[Registers.A] = data
    this[Flags.N] = (data and 0b1000_0000u) != 0u.toUByte()
    this[Flags.Z] = data == 0u.toUByte()
}

/// Loads the value at the specified address into the X register.
fun MOS6502.LDX(mode: AddressingMode) {
    val addr = getAddressAndMovePC(mode)
    val data = memory[addr]
    this[Registers.X] = data
    this[Flags.N] = (data and 0b1000_0000u) != 0u.toUByte()
    this[Flags.Z] = data == 0u.toUByte()
}

/// Loads the value at the specified address into the Y register.
fun MOS6502.LDY(mode: AddressingMode) {
    val addr = getAddressAndMovePC(mode)
    val data = memory[addr]
    this[Registers.Y] = data
    this[Flags.N] = (data and 0b1000_0000u) != 0u.toUByte()
    this[Flags.Z] = data == 0u.toUByte()
}

/// Stores the value in the accumulator at the specified address.
fun MOS6502.STA(mode: AddressingMode) {
    val addr = getAddressAndMovePC(mode)
    memory[addr] = this[Registers.A]
}

/// Stores the value in the X register at the specified address.
fun MOS6502.STX(mode: AddressingMode) {
    val addr = getAddressAndMovePC(mode)
    memory[addr] = this[Registers.X]
}

/// Stores the value in the Y register at the specified address.
fun MOS6502.STY(mode: AddressingMode) {
    val addr = getAddressAndMovePC(mode)
    memory[addr] = this[Registers.Y]
}