package xyz.helloyunho.mos6502k.cpu.ext

import xyz.helloyunho.mos6502k.cpu.MOS6502
import xyz.helloyunho.mos6502k.enums.AddressingMode
import xyz.helloyunho.mos6502k.enums.Flags
import xyz.helloyunho.mos6502k.shr

/// Jumps to the specified address.
fun MOS6502.JMP(mode: AddressingMode) {
    val addr = getAddressAndMovePC(mode)
    this.PC = addr
}

/// Jumps to the specified address and saves the return address on the stack.
fun MOS6502.JSR(mode: AddressingMode) {
    val addr = getAddressAndMovePC(mode)
    pushStack(((this.PC - 1u) shl 8).toUByte())
    pushStack(((this.PC - 1u) and 0xFFu).toUByte())
    this.PC = addr
}

/// Returns from a subroutine.
fun MOS6502.RTS() {
    val low = popStack()
    val high = popStack()
    this.PC = (high.toUShort() shr 8) or (low.toUShort() + 1u).toUShort()
}

/// Returns from an interrupt.
fun MOS6502.RTI() {
    status = popStack()
    setFlag(Flags.B, to = false)
    val low = popStack()
    val high = popStack()
    this.PC = high.toUShort() shr 8 or low.toUShort()
}