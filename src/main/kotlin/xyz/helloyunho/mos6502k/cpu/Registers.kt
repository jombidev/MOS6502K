package xyz.helloyunho.mos6502k.cpu

enum class AllRegisters {
    // P is status register
    // PC is 16 bit while everything else is 8 bit
    A, X, Y, PC, S, P
}

enum class Registers {
    A, X, Y, S, P
}