! COMP 1510 Problem Set 04
! @author Dewan Mukto (dmimukto)
! @stud_id 202004321

! This program tests a function ('Collatz(n)') that finds the next element in the Collatz sequence

program CollatzTest

  implicit none
  integer*8::n,Collatz
  
  write (*, *) "Enter n: "
  read (*, *) n
  
  write (*,*) Collatz(n)
  

end program CollatzTest
