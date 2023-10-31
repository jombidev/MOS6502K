@file:Suppress("MemberVisibilityCanBePrivate", "unused")
@file:OptIn(ExperimentalUnsignedTypes::class)

package xyz.helloyunho.mos6502k.memory

data class BasicMemory(var memory: UByteArray) {
    constructor(size: UShort) : this(UByteArray(size.toInt()))
    constructor() : this(0xFFFFu)

    val size: Int
        get() = memory.size

    fun read(addr: UShort): UByte {
        return memory[addr.toInt()]
    }

    fun read2Bytes(addr: UShort): UShort {
        return read(addr).toUShort() or ((read((addr + 1u).toUShort()).toInt()) shl 8).toUShort()
    }

    fun write(addr: UShort, value: UByte): UByte {
        memory[addr.toInt()] = value
        return value
    }

    fun write(addr: UShort, value: UShort): UShort {
        write(addr, (value and 0xFFu).toUByte())
        write((addr + 1u).toUShort(), (value.toInt() shr 8).toUByte())
        return value
    }

    operator fun get(addr: UShort): UByte {
        return read(addr)
    }

    operator fun set(addr: UShort, newValue: UByte) {
        write(addr, newValue)
    }
}