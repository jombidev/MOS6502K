@file:Suppress("MemberVisibilityCanBePrivate", "unused")
@file:OptIn(ExperimentalUnsignedTypes::class)

package xyz.helloyunho.mos6502k.memory

data class BasicMemory(
    /**
     * DO NOT ACCESS THIS DIRECTLY.
     **/
    var memory: UByteArray
) : MemoryLayout {
    /**
     * Initializes memory for limited count.
     *
     * @param [size] Memory size to initialize.
     **/
    constructor(size: UShort) : this(UByteArray(size.toInt()))

    /**
     * Initializes all the available memory (0xFFFF or 65535 bytes).
     **/
    constructor() : this(0xFFFFu)

    val size: Int
        get() = memory.size

    override fun read(addr: UShort): UByte {
        return memory[addr.toInt()]
    }

    override fun read2Bytes(addr: UShort): UShort {
        return read(addr).toUShort() or ((read((addr + 1u).toUShort()).toInt()) shl 8).toUShort()
    }

    override fun write(addr: UShort, value: UByte): UByte {
        memory[addr.toInt()] = value
        return value
    }

    override fun write(addr: UShort, value: UShort): UShort {
        write(addr, (value and 0xFFu).toUByte())
        write((addr + 1u).toUShort(), (value.toInt() shr 8).toUByte())
        return value
    }

    override operator fun get(addr: UShort): UByte {
        return read(addr)
    }

    override operator fun set(addr: UShort, newValue: UByte) {
        write(addr, newValue)
    }
}