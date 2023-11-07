package xyz.helloyunho.mos6502k.cpu

fun MOS6502.LDA(mode: AddressingMode) {
    val addr = getAddressAndMovePC(mode)
    val data = memory[addr]
    this[Registers.A] = data
    this[Flags.N] = (data and 0b1000_0000u) != 0u.toUByte()
    this[Flags.Z] = data == 0u.toUByte()
}

fun MOS6502.LDX(mode: AddressingMode) {
    val addr = getAddressAndMovePC(mode)
    val data = memory[addr]
    this[Registers.X] = data
    this[Flags.N] = (data and 0b1000_0000u) != 0u.toUByte()
    this[Flags.Z] = data == 0u.toUByte()
}

fun MOS6502.LDY(mode: AddressingMode) {
    val addr = getAddressAndMovePC(mode)
    val data = memory[addr]
    this[Registers.Y] = data
    this[Flags.N] = (data and 0b1000_0000u) != 0u.toUByte()
    this[Flags.Z] = data == 0u.toUByte()
}

fun MOS6502.STA(mode: AddressingMode) {
    val addr = getAddressAndMovePC(mode)
    memory[addr] = this[Registers.A]
}

fun MOS6502.STX(mode: AddressingMode) {
    val addr = getAddressAndMovePC(mode)
    memory[addr] = this[Registers.X]
}

fun MOS6502.STY(mode: AddressingMode) {
    val addr = getAddressAndMovePC(mode)
    memory[addr] = this[Registers.Y]
}