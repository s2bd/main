! COMP 1510 Problem Set 04
! @author Dewan Mukto (dmimukto)
! @stud_id 202004321

! This function finds the next element in the Collatz sequence

function Collatz(n) result(nextElement)

  implicit none
  ! 64-bit integer variables are declared
  integer*8::n, nextElement
  
  if(mod(n,2) == 0) then
    nextElement = n/2
  else
    nextElement = n*3 + 1
  end if
  
end function Collatz
