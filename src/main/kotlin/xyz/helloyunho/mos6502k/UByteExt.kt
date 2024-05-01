package xyz.helloyunho.mos6502k

infix fun UByte.shl(other: Int): UByte {
    return (toUInt() shl other).toUByte()
}

infix fun UByte.shr(other: Int): UByte {
    return (toUInt() shr other).toUByte()
}