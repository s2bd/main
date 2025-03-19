! CALCUS by Dewan Mukto (www.dewanmukto.github.io)
! CALCUS is a multi-purpose "calculation utility service" based on
! Fortran 90. Currently, this version is for beta testing only.
! Expect almost NO GUARANTEE of stability or confirmation that certain
! features will work properly.

! Functions are defined
function func_bin2dec(x) result(y)
   real::x
   character::y
   
   y = "error"
   
end function
function func_bin2hex(x) result(y)
   real::x
   character::y
   
   y = "error"
   
end function
function func_dec2bin(x) result(y)
   real::x
   character::y
   
   y = "error"
   
end function
function func_dec2hex(x) result(y)
   real::x
   character::y
   
   y = "error"
   
end function
function func_hex2bin(x) result(y)
   real,intent(in)::x
   character::y
   
   y = "error"
   
end function
function func_hex2dec(x) result(y)
   real,intent(in)::x
   character::y
   
   y = "error"
   
end function


program calcus
! Disabling the default Fortran feature of declaring variables
! implicitly
implicit none
! Variables are declared. Do not touch unless you know what you are
! trying to do!
character::command
real,intent(in)::base_init
real,intent(in)::base_conv
real,intent(in)::numb_init
real,intent(in)::numb_conv
! Default blank command pre-loaded
command = " "

! Default splash screen
print *, "*********************************************************"
print *, "  WELCOME TO CALCUS 2022 - CALCULATION UTILITY SERVICE  "
print *, "              To get started, type 'help'               "
print *, "          To access version info, type 'about'          "
print *, "*********************************************************"
print *, ""

do while (command != "exit" .or. "quit")
   write (*,"(a)", advance="no") ">"
   read (*,*) command
   select case(command)
      case("help")
         print *, "What do you mean? CALCUS is so easy to understand that"
         print *, "even a 9-year old child can use it!"
         print *, "(Okay, just kidding!)"
         print *, "Refer to the website : dewanmukto.github.io/calcus for more"
         print *, "information. This program is supposed to be compact"
         print *, "and versatile. So no extra effort has been made to"
         print *, "improve the user interface. Sorry about that!"
         new_line('a')
         print *, "Anyways, use common sense and try to do things as you"
         print *, "normally would do with a regular calculator."
      case("f90 compile")
         print *, "Open terminal. Locate the .f90 Fortran file."
         print *, "Type 'gfortran FILENAME.f90 -o OUTPUTNAME.exe'"
         print *, " or  'gfortran FILENAME.f90 -o OUTPUTNAME' "
         print *, "That should do it!"
      case("base-n")
        print *, "Base-N Mode activated"
        do while command != "done"
           write (*,'(a)',advance="no") "Enter initial base (2/10/16):"
           read base_init
           write (*, '(a)',advance="no") "Enter initial number:"
           read numb_init
           write (*, '(a)',advance="no") "Enter conversion base (2/10/16):"
           read base_conv
           if (base_init == 2) .and. (base_conv == 10) then
            func_bin2dec(numb_init)
           else if (base_init == 2) .and. (base_conv == 16) then
            func_bin2hex(numb_init)
           else if (base_init == 10) .and. (base_conv == 2) then
            func_dec2bin(numb_init)
           else if (base_init == 10) .and. (base_conv == 16) then
            func_dec2hex(numb_init)
           else if (base_init == 16) .and. (base_conv == 10) then
            func_hex2dec(numb_init)
           else if (base_init == 16) .and. (base_conv == 2) then
            func_hex2bin(numb_init)
        print *, "Base-N Mode deactivated"
   end select
end do



end program calcus
