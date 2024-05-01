package xyz.helloyunho.mos6502k.enums

@Suppress("unused")
enum class AddressingMode {
    /**
     * Accumulator addressing mode.
     *
     * This addressing mode uses the accumulator register.
     */
    ACCUMULATOR,

    /**
     * Implied addressing mode.
     *
     * This addressing mode doesn't use(actually doesn't even need) any operands.
     */
    IMPLIED,

    /**
     * Immediate addressing mode.
     *
     * This addressing mode uses the next byte as the value.
     */
    IMMEDIATE,

    /**
     * Absolute addressing mode.
     *
     * This addressing mode uses the next two bytes as the address.
     */
    ABSOLUTE,

    /**
     * Zero Page addressing mode.
     *
     * This addressing mode uses the next byte as the address.
     */
    ZERO_PAGE,

    /**
     * Relative addressing mode.
     *
     * This addressing mode uses the next byte as the relative address.
     * The way it works is that it adds the next byte to the program counter.
     *
     * > Note: The relative address is signed.
     */
    RELATIVE,

    /**
     * Absolute indirect addressing mode.
     *
     * This addressing mode reads the next two bytes as the address,
     * then reads the value at that address as the actual address.
     *
     * For example, if the next two bytes are `0x00` and `0x01`,
     * and the value at `0x0001` is `0x02` and the value at `0x0002` is `0x03`,
     * the actual address is `0x0302`.
     */
    ABSOLUTE_INDIRECT,

    /**
     * Absolute indexed with X addressing mode.
     *
     * This addressing mode uses the next two bytes as the address,
     * then adds the X register to the address.
     */
    ABSOLUTE_INDEXED_X,

    /**
     * Absolute indexed with Y addressing mode.
     *
     * This addressing mode uses the next two bytes as the address,
     * then adds the Y register to the address.
     */
    ABSOLUTE_INDEXED_Y,

    /**
     * Zero Page indexed with X addressing mode.
     *
     * This addressing mode uses the next byte as the address,
     * then adds the X register to the address.
     */
    ZERO_PAGE_INDEXED_X,

    /**
     * Zero Page indexed with Y addressing mode.
     *
     * This addressing mode uses the next byte as the address,
     * then adds the Y register to the address.
     */
    ZERO_PAGE_INDEXED_Y,

    /**
     * Zero Page indexed with X indirect addressing mode.
     *
     * This addressing mode uses the next byte as the address,
     * then adds the X register to the address,
     * then reads the value at that address as the actual address.
     *
     * For example, if the next byte is `0x00` and the X register is `0x01`,
     * and the value at `0x0001` is `0x02` and the value at `0x0002` is `0x03`,
     * the actual address is `0x0302`.
     */
    ZERO_PAGE_INDEXED_INDIRECT_X,

    /**
     * Zero Page indexed with Y indirect addressing mode.
     *
     * This addressing mode uses the next byte as the address,
     * then reads the value at that address as the actual address,
     * then adds the Y register to the address.
     *
     * For example, if the next byte is `0x00` and the Y register is `0x01`,
     * and the value at `0x0000` is `0x02` and the value at `0x0001` is `0x03`,
     * the actual address is `0x0303`.
     */
    ZERO_PAGE_INDEXED_INDIRECT_Y
}