@file:Suppress("unused")

package xyz.helloyunho.mos6502k.enums

enum class Registers {
    // P register is status flag
    // PC is 16 bit and everything else is 8 bit
    /**
    Accumulator register.

    Usually used to calculate with data.
     */
    A,

    /**
    X register.

    This can be used for indexing.
     */
    X,

    /**
    Y register.

    This can be used for indexing.
     */
    Y,

    /**
    Program Counter register.

    This represents the current execution address.

    > Note: This register uses 16 bit instead of 8 bit.
     */
    PC,

    /**
    Stack Pointer register.

    As the name suggests, this register is the index of current stack.

    > Note: The default, initialized value for this register is `0xFD`.
     */
    S,

    /**
    Status register.

    This is the register that contains all the CPU flags.
     */
    P,
}

enum class AccessibleRegisters {
    // PC alone has dedicated variable for it
    /**
    Accumulator register.

    Usually used to calculate with data.
     */
    A,

    /**
    X register.

    This can be used for indexing.
     */
    X,

    /**
    Y register.

    This can be used for indexing.
     */
    Y,

    /**
    Stack Pointer register.

    As the name suggests, this register is the index of current stack.

    > Note: The default, initialized value for this register is `0xFD`.
     */
    S,

    /**
    Status register.

    This is the register that contains all the CPU flags.
     */
    P,
}