package xyz.helloyunho.mos6502k.cpu.ext

import xyz.helloyunho.mos6502k.cpu.MOS6502
import xyz.helloyunho.mos6502k.enums.Flags
import xyz.helloyunho.mos6502k.enums.Registers

/// Pushes the value in the accumulator onto the stack.
fun MOS6502.PHA() {
    pushStack(this[Registers.A])
}

/// Pulls the value from the stack into the accumulator.
fun MOS6502.PLA() {
    this[Registers.A] = popStack()
    this[Flags.Z] = this[Registers.A] == 0u.toUByte()
    this[Flags.N] = this[Registers.A] and 0b1000_0000u != 0u.toUByte()
}

/// Pushes the value in the status register onto the stack.
fun MOS6502.PHP() {
    var status = this.status
    status = status or 0b0001_0000u
    pushStack(status)
}

/// Pulls the value from the stack into the status register.
fun MOS6502.PLP() {
    status = popStack()
    setFlag(Flags.B, to = false)
}