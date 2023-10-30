package xyz.helloyunho.mos6502k.cpu // AH finally

enum class Flags(val pos: UByte) { // how oh nvm got it
    /*
        // Flags Bit Order: NV_BDIZC
    // swiftlint:disable identifier_name
    /**
     Negative flag.

     Usually same as bit 7(Negative bit),
     but when compare, it indicates whether
     the input value is smaller than the register value.
     */
    case N
    /**
     Overflow flag.

     When performing arithmetic operations,
     this will indicate whether the result has overflowed.
     */
    case V
    /**
     Unused flag.

     > Warning: Please do not use this as it's unused flag and needs to be always set to HIGH.
     */
    case `_`
    /**
     Break flag.
     */
    case B
    case D
    case I
    case Z
    case C
    // swiftlint:enable identifier_name
     */

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
    C(0u) // now it's time to check how to write a docs
    // ah alright it's just a copy of JSDoc thanks
}
// ykwhat lets write docs later lol