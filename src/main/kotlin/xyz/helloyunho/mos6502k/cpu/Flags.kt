@file:Suppress("unused")

package xyz.helloyunho.mos6502k.cpu

enum class Flags(val pos: UByte) {
    N(7u),

    /**
     * Overflow flag.
     *
     * When performing arithmetic operations,
     * this will indicate whether the result has overflowed.
     */
    V(6u),

    /**
     * Unused flag.
     *
     * **Please do not use this as it's unused flag and needs to be always set to HIGH.**
     */
    DONT_USE_THIS(5u), // probably the best fix imo

    /**
     * Break flag.
     */
    B(4u),

    /**
     * Decimal Flag.
     */
    D(3u),

    /**
     * Interrupt Disable flag.
     */
    I(2u),

    /**
     * Zero flag.
     */
    Z(1u),

    /**
     * Carry flag.
     */
    C(0u)
}