section .data
    ClassName db "SimpleWinClass", 0
    WindowName db "Hello, World!", 0

section .bss
    hWnd resd 1       ; Handle to the window

section .text
    global _start
    extern GetMessageA, TranslateMessage, DispatchMessageA
    extern CreateWindowExA, DefWindowProcA, RegisterClassA
    extern ShowWindow, UpdateWindow, GetMessageA
    extern PostQuitMessage
    extern ExitProcess
    extern GetModuleHandleA, LoadIconA, LoadCursorA

    ; Window procedure to handle messages
WndProc:
    ; Handle messages sent to the window
    mov eax, [esp + 4]     ; Message parameter
    cmp eax, 0x0002        ; WM_DESTROY
    je .destroy
    ; Call the default window procedure for all other messages
    push dword [esp + 8]  ; wParam
    push dword [esp + 4]  ; lParam
    push dword [esp + 12] ; hWnd
    call DefWindowProcA
    ret

.destroy:
    ; Post a quit message
    call PostQuitMessage
    xor eax, eax
    ret

_start:
    ; Get the instance handle
    push 0
    call GetModuleHandleA
    mov ebx, eax           ; Save instance handle

    ; Load the default icon and cursor
    push 0
    call LoadIconA
    push eax               ; Icon handle

    push 0
    call LoadCursorA
    push eax               ; Cursor handle

    ; Create and register the window class
    push 0                 ; Extra class bytes
    push eax               ; Cursor handle
    push eax               ; Icon handle
    push ebx               ; Instance handle
    push ClassName         ; Class name
    call RegisterClassA

    ; Create the window
    push 0                 ; No extended styles
    push WindowName        ; Window title
    push ClassName         ; Class name
    push WS_OVERLAPPEDWINDOW ; Style
    push 100               ; Initial position x
    push 100               ; Initial position y
    push 800               ; Width
    push 600               ; Height
    push 0                 ; Parent window
    push 0                 ; Menu
    push ebx               ; Instance handle
    mov [hWnd], eax       ; Save the window handle
    call CreateWindowExA

    ; Show and update the window
    push SW_SHOW           ; Show normal
    push [hWnd]
    call ShowWindow
    push [hWnd]
    call UpdateWindow

    ; Message loop
.message_loop:
    push 0
    push dword [esp + 8]
    call GetMessageA
    test eax, eax
    jz .quit
    push dword [esp + 8] ; Message pointer
    call TranslateMessage
    push dword [esp + 8] ; Message pointer
    call DispatchMessageA
    jmp .message_loop

.quit:
    ; Exit the process
    push 0
    call ExitProcess

section .idata
    ; Importing libraries
    library user32, "user32.dll"
    library kernel32, "kernel32.dll"
    import user32, \
        GetMessageA, "GetMessageA", \
        TranslateMessage, "TranslateMessage", \
        DispatchMessageA, "DispatchMessageA", \
        CreateWindowExA, "CreateWindowExA", \
        DefWindowProcA, "DefWindowProcA", \
        RegisterClassA, "RegisterClassA", \
        ShowWindow, "ShowWindow", \
        UpdateWindow, "UpdateWindow", \
        LoadIconA, "LoadIconA", \
        LoadCursorA, "LoadCursorA", \
        PostQuitMessage, "PostQuitMessage"

    import kernel32, \
        GetModuleHandleA, "GetModuleHandleA", \
        ExitProcess, "ExitProcess"
