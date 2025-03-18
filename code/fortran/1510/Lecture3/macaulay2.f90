PROGRAM macaulay2
   IMPLICIT NONE
   REAL::userInput
   REAL::Macaulay

   write (*, "(a)", advance="no") "enter value: "
   read (*, *) userInput

   write (*, *) "the result is", Macaulay(userInput)

End Program macaulay2


FUNCTION Macaulay(x) result(y)
   real,intent(in)::x
   real::y
   
   if (x>0) then
      y = x
   else
      y = 0
   end if
   
END FUNCTION
