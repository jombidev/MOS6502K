@file:Suppress("MemberVisibilityCanBePrivate", "unused")

package xyz.helloyunho.mos6502k.cpu

import xyz.helloyunho.mos6502k.cpu.ext.*
import xyz.helloyunho.mos6502k.enums.AddressingMode
import xyz.helloyunho.mos6502k.enums.Flags
import xyz.helloyunho.mos6502k.enums.Registers
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
            Registers.S to 0xFDu,
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
     * @param [flag] Flag position you want to get.
     * @return The result of flag in boolean.
     */
    fun getFlag(flag: Flags): Boolean {
        return status and ((1 shl flag.pos.toInt()).toUByte()) != (0u).toUByte()
    }

    /**
     * Sets(or toggles) specific flag.
     *
     * @param [flag] Flag position you want to set.
     * @param [to] The value in boolean. Leave it blank(or set it to `null`) to toggle the value.
     * @return The changed status.
     */
    fun setFlag(flag: Flags, to: Boolean? = null): UByte {
        val current = this[flag]
        if (current != (to ?: current)) {
            status = status xor (1 shl flag.pos.toInt()).toUByte()
        }
        return status
    }

    /**
     * Gets the address with specified addressing mode.
     *
     * @param [mode] Desired way to get the address.
     * @return The address with specified addressing mode.
     */
    fun getAddress(mode: AddressingMode): UShort {
        return when (mode) {
            AddressingMode.IMMEDIATE -> PC
            AddressingMode.ABSOLUTE -> memory.read2Bytes(PC)
            AddressingMode.ZERO_PAGE -> memory[PC].toUShort()
            AddressingMode.RELATIVE -> {
                // We first convert offset to a **signed** integer, 8-bit.
                // Then we convert it to 32-bit integer and add it to PC (casted as 32-bit integer).
                // This is to support negative offsets, otherwise signed integers
                // would not be needed.

                val offset = memory[PC].toByte()
                (PC.toInt() + offset.toInt()).toUShort()
            }
            AddressingMode.ABSOLUTE_INDIRECT -> memory.read2Bytes(PC)
            AddressingMode.ABSOLUTE_INDEXED_X -> (memory.read2Bytes(PC) + this[Registers.X].toUShort()).toUShort()
            AddressingMode.ABSOLUTE_INDEXED_Y -> (memory.read2Bytes(PC) + this[Registers.Y].toUShort()).toUShort()
            AddressingMode.ZERO_PAGE_INDEXED_X -> (memory[PC] + this[Registers.X]).toUShort()
            AddressingMode.ZERO_PAGE_INDEXED_Y -> (memory[PC] + this[Registers.Y]).toUShort()
            AddressingMode.ZERO_PAGE_INDEXED_INDIRECT_X -> {
                val base = memory[PC]
                val pointer = base + this[Registers.X]
                memory.read2Bytes(pointer.toUShort())
            }

            AddressingMode.ZERO_PAGE_INDEXED_INDIRECT_Y -> {
                val base = memory[PC]
                val pointer = memory.read2Bytes(base.toUShort())
                (pointer + this[Registers.Y]).toUShort()
            }

            else -> error("Register addressing mode detected.")
        }
    }

    /**
     * Gets the address with specified addressing mode and moves PC register accordingly.
     *
     * @param [mode] Desired way to get the address.
     * @return The address with specified addressing mode.
     */
    fun getAddressAndMovePC(mode: AddressingMode): UShort {
        val data = getAddress(mode)
        when (mode) {
            AddressingMode.IMMEDIATE,
            AddressingMode.ZERO_PAGE,
            AddressingMode.RELATIVE,
            AddressingMode.ZERO_PAGE_INDEXED_X,
            AddressingMode.ZERO_PAGE_INDEXED_Y,
            AddressingMode.ZERO_PAGE_INDEXED_INDIRECT_X,
            AddressingMode.ZERO_PAGE_INDEXED_INDIRECT_Y -> PC++

            AddressingMode.ABSOLUTE,
            AddressingMode.ABSOLUTE_INDIRECT,
            AddressingMode.ABSOLUTE_INDEXED_X,
            AddressingMode.ABSOLUTE_INDEXED_Y -> PC = (PC + 2u).toUShort()

            else -> error("Register addressing mode detected.")
        }

        return data
    }

    /**
    Pushs the value into the stack.

    - Parameters:
    - value: A single byte value.
     */
    fun pushStack(value: UByte) {
        memory[(0x100u + this[Registers.S].toUShort()).toUShort()] = value
        dec(Registers.S)
    }

    /**
    Pops the value from the stack.

    - Returns: The last value that was in the stack.
     */
    fun popStack(): UByte {
        inc(Registers.S)
        return this[(0x100u + this[Registers.S].toUShort()).toUShort()]
    }

    fun step() {
        val op = this[PC]
        PC++

        when (op) {
            0x00.toUByte() -> {
                this[Flags.B] = true
                this[Flags.I] = true
            }
            0x01u.toUByte() -> ORA(AddressingMode.ZERO_PAGE_INDEXED_INDIRECT_X)
            0x05u.toUByte() -> ORA(AddressingMode.ZERO_PAGE)
            0x06u.toUByte() -> ASL(AddressingMode.ZERO_PAGE)
            0x08u.toUByte() -> PHP()
            0x09u.toUByte() -> ORA(AddressingMode.IMMEDIATE)
            0x0Au.toUByte() -> ASL()
            0x0Du.toUByte() -> ORA(AddressingMode.ABSOLUTE)
            0x0Eu.toUByte() -> ASL(AddressingMode.ABSOLUTE) // 0x0 check fin

            0x10u.toUByte() -> BPL(AddressingMode.RELATIVE)
            0x11u.toUByte() -> ORA(AddressingMode.ZERO_PAGE_INDEXED_INDIRECT_Y)
            0x15u.toUByte() -> ORA(AddressingMode.ZERO_PAGE_INDEXED_X)
            0x16u.toUByte() -> ASL(AddressingMode.ZERO_PAGE_INDEXED_X)
            0x18u.toUByte() -> CLC()
            0x19u.toUByte() -> ORA(AddressingMode.ABSOLUTE_INDEXED_Y)
            0x1Du.toUByte() -> ORA(AddressingMode.ABSOLUTE_INDEXED_X)
            0x1Eu.toUByte() -> ASL(AddressingMode.ABSOLUTE_INDEXED_X) // 0x1 check fin

            0x20u.toUByte() -> JSR(AddressingMode.ABSOLUTE)
            0x21u.toUByte() -> AND(AddressingMode.ZERO_PAGE_INDEXED_INDIRECT_X)
            0x24u.toUByte() -> BIT(AddressingMode.ZERO_PAGE)
            0x25u.toUByte() -> AND(AddressingMode.ZERO_PAGE)
            0x26u.toUByte() -> ROL(AddressingMode.ZERO_PAGE)
            0x28u.toUByte() -> PLP()
            0x29u.toUByte() -> AND(AddressingMode.IMMEDIATE)
            0x2Au.toUByte() -> ROL()
            0x2Cu.toUByte() -> BIT(AddressingMode.ABSOLUTE)
            0x2Du.toUByte() -> AND(AddressingMode.ABSOLUTE)
            0x2Eu.toUByte() -> ROL(AddressingMode.ABSOLUTE) // 0x2 check fin

            0x30u.toUByte() -> BMI(AddressingMode.RELATIVE)
            0x31u.toUByte() -> AND(AddressingMode.ZERO_PAGE_INDEXED_INDIRECT_Y)
            0x35u.toUByte() -> AND(AddressingMode.ZERO_PAGE_INDEXED_X)
            0x36u.toUByte() -> ROL(AddressingMode.ZERO_PAGE_INDEXED_X)
            0x38u.toUByte() -> SEC()
            0x39u.toUByte() -> AND(AddressingMode.ABSOLUTE_INDEXED_Y)
            0x3Du.toUByte() -> AND(AddressingMode.ABSOLUTE_INDEXED_X)
            0x3Eu.toUByte() -> ROL(AddressingMode.ABSOLUTE_INDEXED_X) // 0x3 check fin

            0x40u.toUByte() -> RTI()
            0x41u.toUByte() -> EOR(AddressingMode.ZERO_PAGE_INDEXED_INDIRECT_X)
            0x45u.toUByte() -> EOR(AddressingMode.ZERO_PAGE)
            0x46u.toUByte() -> LSR(AddressingMode.ZERO_PAGE)
            0x48u.toUByte() -> PHA()
            0x49u.toUByte() -> EOR(AddressingMode.IMMEDIATE)
            0x4Au.toUByte() -> LSR()
            0x4Cu.toUByte() -> JMP(AddressingMode.ABSOLUTE)
            0x4Du.toUByte() -> EOR(AddressingMode.ABSOLUTE)
            0x4Eu.toUByte() -> LSR(AddressingMode.ABSOLUTE) // 0x4 check fin

            0x50u.toUByte() -> BVC(AddressingMode.RELATIVE)
            0x51u.toUByte() -> EOR(AddressingMode.ZERO_PAGE_INDEXED_INDIRECT_Y)
            0x55u.toUByte() -> EOR(AddressingMode.ZERO_PAGE_INDEXED_X)
            0x56u.toUByte() -> LSR(AddressingMode.ZERO_PAGE_INDEXED_X)
            0x58u.toUByte() -> CLI()
            0x59u.toUByte() -> EOR(AddressingMode.ABSOLUTE_INDEXED_Y)
            0x5Du.toUByte() -> EOR(AddressingMode.ABSOLUTE_INDEXED_X)
            0x5Eu.toUByte() -> LSR(AddressingMode.ABSOLUTE_INDEXED_X) // 0x5 check fin

            0x60u.toUByte() -> RTS()
            0x61u.toUByte() -> ADC(AddressingMode.ZERO_PAGE_INDEXED_INDIRECT_X)
            0x65u.toUByte() -> ADC(AddressingMode.ZERO_PAGE)
            0x66u.toUByte() -> ROR(AddressingMode.ZERO_PAGE)
            0x68u.toUByte() -> PLA()
            0x69u.toUByte() -> ADC(AddressingMode.IMMEDIATE)
            0x6Au.toUByte() -> ROR()
            0x6Cu.toUByte() -> JMP(AddressingMode.ABSOLUTE_INDIRECT)
            0x6Du.toUByte() -> ADC(AddressingMode.ABSOLUTE)
            0x6Eu.toUByte() -> ROR(AddressingMode.ABSOLUTE) // 0x6 check fin

            0x70u.toUByte() -> BVS(AddressingMode.RELATIVE)
            0x71u.toUByte() -> ADC(AddressingMode.ZERO_PAGE_INDEXED_INDIRECT_Y)
            0x75u.toUByte() -> ADC(AddressingMode.ZERO_PAGE_INDEXED_X)
            0x76u.toUByte() -> ROR(AddressingMode.ZERO_PAGE_INDEXED_X)
            0x78u.toUByte() -> SEI()
            0x79u.toUByte() -> ADC(AddressingMode.ABSOLUTE_INDEXED_Y)
            0x7Du.toUByte() -> ADC(AddressingMode.ABSOLUTE_INDEXED_X)
            0x7Eu.toUByte() -> ROR(AddressingMode.ABSOLUTE_INDEXED_X) // 0x7 check fin

            0x81u.toUByte() -> STA(AddressingMode.ZERO_PAGE_INDEXED_INDIRECT_X)
            0x84u.toUByte() -> STY(AddressingMode.ZERO_PAGE)
            0x85u.toUByte() -> STA(AddressingMode.ZERO_PAGE)
            0x86u.toUByte() -> STX(AddressingMode.ZERO_PAGE)
            0x88u.toUByte() -> DEY()
            0x89u.toUByte() -> BIT(AddressingMode.IMMEDIATE) // nop on table, but it exists
            0x8Au.toUByte() -> TXA()
            0x8Cu.toUByte() -> STY(AddressingMode.ABSOLUTE)
            0x8Du.toUByte() -> STA(AddressingMode.ABSOLUTE)
            0x8Eu.toUByte() -> STX(AddressingMode.ABSOLUTE) // 0x8 check fin

            0x90u.toUByte() -> BCC(AddressingMode.RELATIVE)
            0x91u.toUByte() -> STA(AddressingMode.ZERO_PAGE_INDEXED_INDIRECT_Y)
            0x94u.toUByte() -> STY(AddressingMode.ZERO_PAGE_INDEXED_X)
            0x95u.toUByte() -> STA(AddressingMode.ZERO_PAGE_INDEXED_X)
            0x96u.toUByte() -> STX(AddressingMode.ZERO_PAGE_INDEXED_Y)
            0x98u.toUByte() -> TYA()
            0x99u.toUByte() -> STA(AddressingMode.ABSOLUTE_INDEXED_Y)
            0x9Au.toUByte() -> TXS()
            0x9Du.toUByte() -> STA(AddressingMode.ABSOLUTE_INDEXED_X) // 0x9 check fin

            0xA0u.toUByte() -> LDY(AddressingMode.IMMEDIATE)
            0xA1u.toUByte() -> LDA(AddressingMode.ZERO_PAGE_INDEXED_INDIRECT_X)
            0xA2u.toUByte() -> LDX(AddressingMode.IMMEDIATE)
            0xA4u.toUByte() -> LDY(AddressingMode.ZERO_PAGE)
            0xA5u.toUByte() -> LDA(AddressingMode.ZERO_PAGE)
            0xA6u.toUByte() -> LDX(AddressingMode.ZERO_PAGE)
            0xA8u.toUByte() -> TAY()
            0xA9u.toUByte() -> LDA(AddressingMode.IMMEDIATE)
            0xAAu.toUByte() -> TAX()
            0xACu.toUByte() -> LDY(AddressingMode.ABSOLUTE)
            0xADu.toUByte() -> LDA(AddressingMode.ABSOLUTE)
            0xAEu.toUByte() -> LDX(AddressingMode.ABSOLUTE) // 0xA check fin

            0xB0u.toUByte() -> BCS(AddressingMode.RELATIVE)
            0xB1u.toUByte() -> LDA(AddressingMode.ZERO_PAGE_INDEXED_INDIRECT_Y)
            0xB4u.toUByte() -> LDY(AddressingMode.ZERO_PAGE_INDEXED_X)
            0x85u.toUByte() -> LDA(AddressingMode.ZERO_PAGE_INDEXED_X)
            0x86u.toUByte() -> LDX(AddressingMode.ZERO_PAGE_INDEXED_Y)
            0x88u.toUByte() -> CLV()
            0xB9u.toUByte() -> LDA(AddressingMode.ABSOLUTE_INDEXED_Y)
            0xBAu.toUByte() -> TSX()
            0xBCu.toUByte() -> LDY(AddressingMode.ABSOLUTE_INDEXED_X)
            0xBDu.toUByte() -> LDA(AddressingMode.ABSOLUTE_INDEXED_X)
            0xBEu.toUByte() -> LDX(AddressingMode.ABSOLUTE_INDEXED_Y) // 0xB check fin

            0xC0u.toUByte() -> CPY(AddressingMode.IMMEDIATE)
            0xC1u.toUByte() -> CMP(AddressingMode.ZERO_PAGE_INDEXED_INDIRECT_X)
            0xC4u.toUByte() -> CPY(AddressingMode.ZERO_PAGE)
            0xC5u.toUByte() -> CMP(AddressingMode.ZERO_PAGE)
            0xC6u.toUByte() -> DEC(AddressingMode.ZERO_PAGE)
            0xC8u.toUByte() -> INY()
            0xC9u.toUByte() -> CMP(AddressingMode.IMMEDIATE)
            0xCAu.toUByte() -> DEX()
            0xCCu.toUByte() -> CPY(AddressingMode.ABSOLUTE)
            0xCDu.toUByte() -> CMP(AddressingMode.ABSOLUTE)
            0xCEu.toUByte() -> DEC(AddressingMode.ABSOLUTE) // 0xC check fin

            0xD0u.toUByte() -> BNE(AddressingMode.RELATIVE)
            0xD1u.toUByte() -> CMP(AddressingMode.ZERO_PAGE_INDEXED_INDIRECT_Y)
            0xD5u.toUByte() -> CMP(AddressingMode.ZERO_PAGE_INDEXED_X)
            0xD6u.toUByte() -> DEC(AddressingMode.ZERO_PAGE_INDEXED_X)
            0xD8u.toUByte() -> CLD()
            0xD9u.toUByte() -> CMP(AddressingMode.ABSOLUTE_INDEXED_Y)
            0xDDu.toUByte() -> CMP(AddressingMode.ABSOLUTE_INDEXED_X)
            0xDEu.toUByte() -> DEC(AddressingMode.ABSOLUTE_INDEXED_X) // 0xD check fin

            0xE0u.toUByte() -> CPX(AddressingMode.IMMEDIATE)
            0xE1u.toUByte() -> SBC(AddressingMode.ZERO_PAGE_INDEXED_INDIRECT_X)
            0xE4u.toUByte() -> CPX(AddressingMode.ZERO_PAGE)
            0xE5u.toUByte() -> SBC(AddressingMode.ZERO_PAGE)
            0xE6u.toUByte() -> INC(AddressingMode.ZERO_PAGE)
            0xE8u.toUByte() -> INX()
            0xE9u.toUByte() -> SBC(AddressingMode.IMMEDIATE)
            0xEAu.toUByte() -> {/* NOP */}
            0xECu.toUByte() -> CPX(AddressingMode.ABSOLUTE)
            0xEDu.toUByte() -> SBC(AddressingMode.ABSOLUTE)
            0xEEu.toUByte() -> INC(AddressingMode.ABSOLUTE) // 0xE check fin

            0xF0u.toUByte() -> BEQ(AddressingMode.RELATIVE)
            0xF1u.toUByte() -> SBC(AddressingMode.ZERO_PAGE_INDEXED_INDIRECT_Y)
            0xF5u.toUByte() -> SBC(AddressingMode.ZERO_PAGE_INDEXED_X)
            0xF6u.toUByte() -> INC(AddressingMode.ZERO_PAGE_INDEXED_X)
            0xF8u.toUByte() -> SED()
            0xF9u.toUByte() -> SBC(AddressingMode.ABSOLUTE_INDEXED_Y)
            0xFDu.toUByte() -> SBC(AddressingMode.ABSOLUTE_INDEXED_X)
            0xFEu.toUByte() -> INC(AddressingMode.ABSOLUTE_INDEXED_X) // 0xF check fin

            else -> error("Unknown OP code $op detected.")
        }
    }

    fun inc(register: Registers) {
        registers[register] = (this[register] + 1u).toUByte()
    }

    fun dec(register: Registers) {
        registers[register] = (this[register] + 1u).toUByte()
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