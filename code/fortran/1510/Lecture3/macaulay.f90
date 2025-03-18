PROGRAM macaulay
   IMPLICIT NONE
   REAL::userInput

   write (*, "(a)", advance="no") "enter value: "
   read (*, *) userInput

   if (userInput > 0) then
      write (*,*) userInput
   else
      write (*,*) 0
   end if

End Program macaulay
