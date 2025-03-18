section .data
    width db 80            ; Width of the output
    height db 25           ; Height of the output
    max_iterations db 100  ; Maximum number of iterations
    escape_limit dq 4.0    ; Escape threshold (4.0)

section .bss
    output resb 2000       ; Buffer for output (80 * 25 = 2000)

section .text
    global _start

_start:
    ; Initialize output buffer
    mov rdi, output        ; Pointer to output buffer
    mov rsi, 2000          ; Size of output
    xor rcx, rcx           ; Clear counter

clear_buffer:
    mov byte [rdi + rcx], ' '  ; Fill with spaces
    inc rcx
    cmp rcx, rsi
    jl clear_buffer

    ; Render Mandelbrot set
    mov rbx, 0            ; Row index (y)
render_row:
    cmp rbx, height
    jge display_output     ; If row index >= height, display output

    ; Calculate imaginary part
    movzx rdx, rbx        ; Zero-extend row index to rdx
    sub rdx, 12           ; Center the view
    shl rdx, 1            ; Scale by 2 (multiply by 2)
    mov r8d, rdx          ; Store imaginary part in r8d

    ; Reset column index (x)
    xor rcx, rcx          ; Reset column index
render_column:
    cmp rcx, width
    jge next_row          ; If column index >= width, go to next row

    ; Calculate real part
    movzx rdx, rcx        ; Zero-extend column index to rdx
    sub rdx, 40           ; Center the view
    shl rdx, 1            ; Scale by 2
    mov r9d, rdx          ; Store real part in r9d

    ; Mandelbrot iteration
    xor rax, rax          ; Z_real = 0
    xor rdx, rdx          ; Z_imag = 0
    mov cl, max_iterations ; Set iteration count

mandelbrot_iteration:
    ; Z = Z^2 + C
    mov r10d, rax         ; Copy Z_real to r10d
    imul r10d, r10d       ; Z_real^2
    mov r11d, rdx         ; Copy Z_imag to r11d
    imul r11d, r11d       ; Z_imag^2
    add r10d, r9d         ; Z_real^2 + C_real
    cmp r10d, 4           ; Compare to escape limit
    ja escape             ; If Z^2 >= escape limit, escape

    ; Z_imag = 2 * Z_real * Z_imag + C_imag
    mov rdx, rax          ; Copy Z_real to rdx
    shl rdx, 1            ; 2 * Z_real
    add rdx, r8d          ; Add C_imag

    ; Update Z
    mov rax, r10d         ; Update Z_real
    mov rdx, rdx          ; Update Z_imag

    dec cl
    jnz mandelbrot_iteration

    ; If max iterations reached, assign a character
    mov byte [output + rbx * width + rcx], '#' ; Use rcx for column index
    jmp end_iteration

escape:
    ; Assign a character based on escape
    mov byte [output + rbx * width + rcx], ' '

end_iteration:
    inc rcx
    jmp render_column

next_row:
    inc rbx
    jmp render_row

display_output:
    ; Display the output
    mov rax, 1            ; Syscall for sys_write
    mov rdi, 1            ; File descriptor 1 (stdout)
    mov rsi, output       ; Output buffer
    mov rdx, 2000         ; Size of output
    syscall               ; Call kernel

    ; Exit program
    mov rax, 60           ; Syscall for sys_exit
    xor rdi, rdi          ; Return code 0
    syscall               ; Call kernel
