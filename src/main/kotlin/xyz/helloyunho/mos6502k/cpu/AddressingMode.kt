package xyz.helloyunho.mos6502k.cpu

enum class AddressingMode {
    accumulator, implied, immediate, absolute, zeroPage, relative, absoluteIndirect, absoluteIndexedX, absoluteIndexedY, zeroPageIndexedX, zeroPageIndexedY, zeroPageIndexedIndirectX, zeroPageIndexedIndirectY
}