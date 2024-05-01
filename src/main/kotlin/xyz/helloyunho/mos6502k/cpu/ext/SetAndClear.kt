package xyz.helloyunho.mos6502k.cpu.ext

import xyz.helloyunho.mos6502k.cpu.MOS6502
import xyz.helloyunho.mos6502k.enums.Flags

/// Clears the Carry flag.
fun MOS6502.CLC() {
    this[Flags.C] = false
}

/// Sets the Carry flag.
fun MOS6502.SEC() {
    this[Flags.C] = true
}

/// Clears the Interrupt Disable flag.
fun MOS6502.CLI() {
    this[Flags.I] = false
}

/// Sets the Interrupt Disable flag.
fun MOS6502.SEI() {
    this[Flags.I] = true
}

/// Clears the Overflow flag.
fun MOS6502.CLV() {
    this[Flags.V] = false
}

/// Clears the Decimal flag.
fun MOS6502.CLD() {
    this[Flags.D] = false
}

/// Sets the Decimal flag.
fun MOS6502.SED() {
    this[Flags.D] = true
}
