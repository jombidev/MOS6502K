@file:Suppress("unused")

package xyz.helloyunho.mos6502k.enums

enum class Flags(val pos: UByte) {
    // Flags Bit Order: NV_BDIZC
    /**
     * Negative flag.
     *
     * Usually same as bit 7(Negative bit),
     * but when compare, it indicates whether
     * the input value is smaller than the register value.
     */
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
     * ** Please do not use this as it's unused flag and needs to be always set to HIGH. **
     */
    DONT_USE_THIS(5u), // probably the best fix imo

    /**
     * Break flag.
     *
     * This flag is turned on when `BRK` instruction was called.
     */
    B(4u),

    /**
     * Decimal Flag.
     *
     * Enabling this makes the emulator to read values in decimal instead of hex.
     * For example, `0x09 + 0x01` is `0x10`.
     */
    D(3u),

    /**
     * Interrupt Disable flag.
     */
    I(2u),

    /**
     * Zero flag.
     *
     * When compare, this flag indicates whether
     * the value is equal.
     *
     * Otherwise it literally means whether
     * the value is zero or not.
     */
    Z(1u),

    /**
     * Carry flag.
     *
     * When performing arithmetic operations, this flag basically means
     * an unsigned overflow.
     *
     * When compare, it indicates whether the register's value is greater than or equal to
     * the input value.
     *
     * When shifting, the eliminated bit is set to this flag.
     */
    C(0u)
}