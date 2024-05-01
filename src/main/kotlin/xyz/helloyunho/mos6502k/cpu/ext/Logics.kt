package xyz.helloyunho.mos6502k.cpu.ext

import xyz.helloyunho.mos6502k.cpu.MOS6502
import xyz.helloyunho.mos6502k.enums.AddressingMode
import xyz.helloyunho.mos6502k.enums.Flags
import xyz.helloyunho.mos6502k.enums.Registers

/// ANDs the value at the specified address with the accumulator.
fun MOS6502.AND(mode: AddressingMode) {
    val addr = getAddressAndMovePC(mode)
    val value = memory[addr]

    this[Registers.A] = this[Registers.A] and value
    this[Flags.Z] = this[Registers.A] == 0u.toUByte()
    this[Flags.N] = this[Registers.A] and 0b1000_0000u != 0u.toUByte()
}

/// ORs the value at the specified address with the accumulator.
fun MOS6502.ORA(mode: AddressingMode) {
    val addr = getAddressAndMovePC(mode)
    val value = memory[addr]

    this[Registers.A] = this[Registers.A] or value
    this[Flags.Z] = this[Registers.A] == 0u.toUByte()
    this[Flags.N] = this[Registers.A] and 0b1000_0000u != 0u.toUByte()
}

/// XORs the value at the specified address with the accumulator.
fun MOS6502.EOR(mode: AddressingMode) { // shouldnt it be xor lol
    val addr = getAddressAndMovePC(mode)
    val value = memory[addr]

    this[Registers.A] = this[Registers.A] xor value
    this[Flags.Z] = this[Registers.A] == 0u.toUByte()
    this[Flags.N] = this[Registers.A] and 0b1000_0000u != 0u.toUByte()
}
