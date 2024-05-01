package xyz.helloyunho.mos6502k.cpu.ext

import xyz.helloyunho.mos6502k.cpu.MOS6502
import xyz.helloyunho.mos6502k.enums.Flags
import xyz.helloyunho.mos6502k.enums.Registers

/// Transfers the value in the accumulator to the X register.
fun MOS6502.TAX() {
    val value = this[Registers.A]

    this[Registers.X] = value

    this[Flags.Z] = this[Registers.X] == 0u.toUByte()
    this[Flags.N] = this[Registers.X] and 0b1000_0000u != 0u.toUByte()
}

/// Transfers the value in the X register to the accumulator.
fun MOS6502.TXA() {
    val value = this[Registers.X]

    this[Registers.A] = value

    this[Flags.Z] = this[Registers.A] == 0u.toUByte()
    this[Flags.N] = this[Registers.A] and 0b1000_0000u != 0u.toUByte()
}

/// Transfers the value in the accumulator to the Y register.
fun MOS6502.TAY() {
    val value = this[Registers.A]

    this[Registers.Y] = value

    this[Flags.Z] = this[Registers.Y] == 0u.toUByte()
    this[Flags.N] = this[Registers.Y] and 0b1000_0000u != 0u.toUByte()
}

/// Transfers the value in the Y register to the accumulator.
fun MOS6502.TYA() {
    val value = this[Registers.Y]

    this[Registers.A] = value

    this[Flags.Z] = this[Registers.A] == 0u.toUByte()
    this[Flags.N] = this[Registers.A] and 0b1000_0000u != 0u.toUByte()
}

/// Transfers the value in the stack pointer to the X register.
fun MOS6502.TSX() {
    val value = this[Registers.S]

    this[Registers.X] = value

    this[Flags.Z] = this[Registers.X] == 0u.toUByte()
    this[Flags.N] = this[Registers.X] and 0b1000_0000u != 0u.toUByte()
}

/// Transfers the value in the X register to the stack pointer.
fun MOS6502.TXS() {
    val value = this[Registers.X]

    this[Registers.S] = value

    this[Flags.Z] = this[Registers.S] == 0u.toUByte()
    this[Flags.N] = this[Registers.S] and 0b1000_0000u != 0u.toUByte()
}
