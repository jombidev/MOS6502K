@file:Suppress("MemberVisibilityCanBePrivate", "unused")

package xyz.helloyunho.mos6502k.cpu

class MOS6502 {
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
        get() = this.registers[Registers.P]!!
        set(newValue) {
            this.registers[Registers.P] = newValue
        }

    /**
     * Represents program counter (or register PC).
     */
    @Suppress("PropertyName")
    var PC: UShort = 0u

    // var memory: MemoryLayout

    /**
     * Gets whether the specific flag is enabled or not.
     *
     * @param flag Flag position you want to get.
     * @return The result of flag in boolean.
     */
    fun getFlag(flag: Flags): Boolean {
        return this.status and ((1 shl flag.pos.toInt()).toUByte()) != (0u).toUByte()
    }
}