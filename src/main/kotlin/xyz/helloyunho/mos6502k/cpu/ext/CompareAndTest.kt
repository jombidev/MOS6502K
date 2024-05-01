package xyz.helloyunho.mos6502k.cpu.ext

import xyz.helloyunho.mos6502k.cpu.MOS6502
import xyz.helloyunho.mos6502k.enums.AddressingMode
import xyz.helloyunho.mos6502k.enums.Flags
import xyz.helloyunho.mos6502k.enums.Registers

/**
Compares the value at the specified address with the accumulator.

Sets the zero flag if the values are equal,
the negative flag if the value in the accumulator is less than the value at the specified address,
and the carry flag if the value in the accumulator is greater than or equal to the value at the specified address.

TL;DR:
|Condition|N|Z|C|
|---------|-|-|-|
|A < M    |1|0|0|
|A = M    |0|1|1|
|A > M    |0|0|1|
 */
fun MOS6502.CMP(mode: AddressingMode) {
    val addr = getAddressAndMovePC(mode)
    val value = memory[addr]
    val a = this[Registers.A]

    this[Flags.N] = a < value
    this[Flags.Z] = value == a
    this[Flags.C] = a >= value
}

/**
Compares the value at the specified address with the X register.

Sets the zero flag if the values are equal,
the negative flag if the value in the X register is less than the value at the specified address,
and the carry flag if the value in the X register is greater than or equal to the value at the specified address.

TL;DR:
|Condition|N|Z|C|
|---------|-|-|-|
|A < M    |1|0|0|
|A = M    |0|1|1|
|A > M    |0|0|1|
 */
fun MOS6502.CPX(mode: AddressingMode) {
    val addr = getAddressAndMovePC(mode)
    val value = memory[addr]
    val x = this[Registers.X]

    this[Flags.N] = x < value
    this[Flags.Z] = value == x
    this[Flags.C] = x >= value
}

/**
Compares the value at the specified address with the X register.

Sets the zero flag if the values are equal,
the negative flag if the value in the X register is less than the value at the specified address,
and the carry flag if the value in the X register is greater than or equal to the value at the specified address.

TL;DR:
|Condition|N|Z|C|
|---------|-|-|-|
|A < M    |1|0|0|
|A = M    |0|1|1|
|A > M    |0|0|1|
 */
fun MOS6502.CPY(mode: AddressingMode) {
    val addr = getAddressAndMovePC(mode)
    val value = memory[addr]
    val y = this[Registers.Y]

    this[Flags.N] = y < value
    this[Flags.Z] = value == y
    this[Flags.C] = y >= value
}

/**
Tests the bits of the value at the specified address against the accumulator.

Sets the zero flag if the result of the AND operation is zero,
the negative flag if bit 7 of the value at the specified address is set,
and the overflow flag if bit 6 of the value at the specified address is set.

TL;DR:
|Condition|N|Z|V|
|---------|-|-|-|
|A & M = 0|0|1|0|
|A & M != 0|M7|0|M6|
 */
fun MOS6502.BIT(mode: AddressingMode) {
    val addr = getAddressAndMovePC(mode)
    val value = memory[addr]
    val result = this[Registers.A] and value

    this[Flags.Z] = result == 0u.toUByte()
    this[Flags.N] = value and 0b1000_0000u != 0u.toUByte()
    this[Flags.V] = value and 0b0100_0000u != 0u.toUByte()
}