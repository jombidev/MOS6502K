@file:Suppress("unused")

package xyz.helloyunho.mos6502k.memory

interface MemoryLayout {
    fun read(addr: UShort): UByte
    fun read2Bytes(addr: UShort): UShort

    fun write(addr: UShort, value: UByte): UByte
    fun write(addr: UShort, value: UShort): UShort

    operator fun get(addr: UShort): UByte
    operator fun set(addr: UShort, newValue: UByte)
}