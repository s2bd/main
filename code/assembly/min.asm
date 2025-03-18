section .text
global _start

_start:
    ; syscall: exit
    mov eax, 60       ; syscall number for exit in x86_64
    xor ebx, ebx      ; return 0
    syscall            ; invoke the kernel
