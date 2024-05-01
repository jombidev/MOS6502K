package xyz.helloyunho.mos6502k.cpu.ext

import xyz.helloyunho.mos6502k.cpu.MOS6502
import xyz.helloyunho.mos6502k.enums.AddressingMode
import xyz.helloyunho.mos6502k.enums.Flags

/// Branches to the specified address if the carry flag is clear.
fun MOS6502.BCC(mode: AddressingMode) {
    if (!this[Flags.C]) {
        val addr = getAddressAndMovePC(mode)
        this.PC = addr
    }
}

/// Branches to the specified address if the carry flag is set.
fun MOS6502.BCS(mode: AddressingMode) {
    if (this[Flags.C]) {
        val addr = getAddressAndMovePC(mode)
        this.PC = addr
    }
}

/// Branches to the specified address if the zero flag is set.
fun MOS6502.BNE(mode: AddressingMode) {
    if (!this[Flags.Z]) {
        val addr = getAddressAndMovePC(mode)
        this.PC = addr
    }
}

/// Branches to the specified address if the zero flag is clear.
fun MOS6502.BEQ(mode: AddressingMode) {
    if (this[Flags.Z]) {
        val addr = getAddressAndMovePC(mode)
        this.PC = addr
    }
}

/// Branches to the specified address if the negative flag is clear.
fun MOS6502.BPL(mode: AddressingMode) {
    if (!this[Flags.N]) {
        val addr = getAddressAndMovePC(mode)
        this.PC = addr
    }
}

/// Branches to the specified address if the negative flag is set.
fun MOS6502.BMI(mode: AddressingMode) {
    if (this[Flags.N]) {
        val addr = getAddressAndMovePC(mode)
        this.PC = addr
    }
}

/// Branches to the specified address if the overflow flag is clear.
fun MOS6502.BVC(mode: AddressingMode) {
    if (!this[Flags.V]) {
        val addr = getAddressAndMovePC(mode)
        this.PC = addr
    }
}

/// Branches to the specified address if the overflow flag is set.
fun MOS6502.BVS(mode: AddressingMode) {
    if (this[Flags.V]) {
        val addr = getAddressAndMovePC(mode)
        this.PC = addr
    }
}