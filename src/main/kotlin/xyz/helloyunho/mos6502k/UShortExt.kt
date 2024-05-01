package xyz.helloyunho.mos6502k

infix fun UShort.shl(other: Int): UShort {
    return (toUInt() shl other).toUShort()
}

infix fun UShort.shr(other: Int): UShort {
    return (toUInt() shr other).toUShort()
}