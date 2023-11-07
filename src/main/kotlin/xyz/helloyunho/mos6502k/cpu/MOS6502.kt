@file:Suppress("MemberVisibilityCanBePrivate", "unused")

package xyz.helloyunho.mos6502k.cpu

import xyz.helloyunho.mos6502k.memory.MemoryLayout

class MOS6502(var memory: MemoryLayout) {
    /**
     * Map based set of registers.
     */
    var registers: MutableMap<Registers, UByte> =
        mutableMapOf(
            Registers.A to 0u,
            Registers.X to 0u,
            Registers.Y to 0u,
            Registers.S to 0u,
            Registers.P to 0b0010_0000u
        )

    /**
     * Represents the status (or register P) value.
     *
     * Do not use this variable directly unless you know what you're doing.
     * I strongly recommend you to use [getFlag] instead.
     */
    var status: UByte
        get() = registers[Registers.P]!!
        set(newValue) {
            registers[Registers.P] = newValue
        }

    /**
     * Represents program counter (or register PC).
     */
    @Suppress("PropertyName")
    var PC: UShort = 0u

    /**
     * Gets whether the specific flag is enabled or not.
     *
     * @param flag Flag position you want to get.
     * @return The result of flag in boolean.
     */
    fun getFlag(flag: Flags): Boolean {
        return status and ((1 shl flag.pos.toInt()).toUByte()) != (0u).toUByte()
    }

    /**
     * Sets(or toggles) specific flag.
     *
     * @param flag Flag position you want to set.
     * @param to The value in boolean. Leave it blank(or set it to `null`) to toggle the value.
     * @return The changed status.
     */
    fun setFlag(flag: Flags, to: Boolean? = null): UByte {
        val current = getFlag(flag)
        if (current != (to ?: current)) {
            status = status xor (1 shl flag.pos.toInt()).toUByte()
        }
        return status
    }

    fun getAddress(mode: AddressingMode): UShort {
        return when (mode) {
            AddressingMode.immediate -> PC
            AddressingMode.absolute -> memory.read2Bytes(PC)
            AddressingMode.zeroPage -> memory[PC].toUShort()
            AddressingMode.relative -> {
                val offset = memory[PC].toShort()
                (PC.toInt() + offset.toInt()).toUShort()
            }
            AddressingMode.absoluteIndirect -> memory.read2Bytes(PC)
            AddressingMode.absoluteIndexedX -> (memory.read2Bytes(PC) + this[Registers.X].toUShort()).toUShort()
            AddressingMode.absoluteIndexedY -> (memory.read2Bytes(PC) + this[Registers.Y].toUShort()).toUShort()
            AddressingMode.zeroPageIndexedX -> (memory[PC] + this[Registers.X]).toUShort()
            AddressingMode.zeroPageIndexedY -> (memory[PC] + this[Registers.Y]).toUShort()
            AddressingMode.zeroPageIndexedIndirectX -> {
                val base = memory[PC]
                val pointer = base + this[Registers.X]
                memory.read2Bytes(pointer.toUShort())
            }
            AddressingMode.zeroPageIndexedIndirectY -> {
                val base = memory[PC]
                val pointer = memory.read2Bytes(base.toUShort())
                (pointer + this[Registers.Y]).toUShort()
            }
            else -> error("Register addressing mode detected.")
        }
    }

    fun getAddressAndMovePC(mode: AddressingMode): UShort {
        val data = getAddress(mode)
        when (mode) {
            AddressingMode.immediate, AddressingMode.zeroPage, AddressingMode.relative, AddressingMode.zeroPageIndexedX, AddressingMode.zeroPageIndexedY, AddressingMode.zeroPageIndexedIndirectX, AddressingMode.zeroPageIndexedIndirectY -> PC++
            AddressingMode.absolute, AddressingMode.absoluteIndirect, AddressingMode.absoluteIndexedX, AddressingMode.absoluteIndexedY -> PC = (PC + 2u).toUShort()
            else -> error("Register addressing mode detected.")
        }

        return data
    }


    operator fun get(register: Registers): UByte {
        return registers[register]!!
    }
    operator fun set(register: Registers, newValue: UByte) {
        registers[register] = newValue
    }

    operator fun get(flag: Flags): Boolean {
        return getFlag(flag)
    }
    operator fun set(flag: Flags, newValue: Boolean) {
        setFlag(flag, newValue)
    }

    operator fun get(addr: UShort): UByte {
        return memory[addr]
    }
    operator fun set(addr: UShort, newValue: UByte) {
        memory[addr] = newValue
    }
}