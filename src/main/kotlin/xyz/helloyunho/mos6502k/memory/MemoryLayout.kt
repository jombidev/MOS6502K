@file:Suppress("unused")

package xyz.helloyunho.mos6502k.memory

interface MemoryLayout {
    /**
     * Reads single byte at specific address.
     *
     * @param [addr] Specific address to get the value.
     * @return The requested value.
     */
    fun read(addr: UShort): UByte

    /**
     * Reads two bytes at specific address.
     *
     * @param [addr] Specific address to get the value.
     * @return The requested value.
     *
     * > If you're implementing this function by yourself, please note that 6502 memory is stored in
     * > [little-endian](https://en.wikipedia.org/wiki/Endianness).
     *
     * > For example, if address `0x0` has `0xFF` and `0x1` has `0x00`, the value for ``MemoryLayout#read2Bytes`` is `0x00FF`.
     *
     * > Important: It's also worth noting that the address like `0xFFFF` can cause overflow since
     * > `0xFFFF + 1` is above 16-bit. When this happens, read the value on `0x0` instead.
     * > Surprisingly this is excepted behaviour and some 6502 code uses this.
     */
    fun read2Bytes(addr: UShort): UShort

    /**
     * Writes single byte at specific address.
     *
     * @param [addr] Specific address to set the value.
     * @param [value] A single byte value.
     * @return The same value.
     */
    fun write(addr: UShort, value: UByte): UByte
    /**
     * Writes two bytes at specific address.
     *
     * @param [addr] Specific address to set the value.
     * @param [value] A two bytes value.
     * @return The same value.
     *
     * > Important: If you're implementing this function by yourself, please note that 6502 memory is stored in
     * > [little-endian](https://en.wikipedia.org/wiki/Endianness).
     *
     * > For example, if the value is `0x00FF` and the address is `0x0`,
     * > ``write(_:_:)-6gcym`` writes `0xFF` to `0x0` and `0x00` to `0x1`.
     *
     * > Important: It's also worth noting that the address like `0xFFFF` can cause overflow since
     * > `0xFFFF + 1` is above 16-bit. When this happens, write the value on `0x0` instead.
     * > Surprisingly this is excepted behaviour and some 6502 code uses this.
     */
    fun write(addr: UShort, value: UShort): UShort

    operator fun get(addr: UShort): UByte
    operator fun set(addr: UShort, newValue: UByte)
}