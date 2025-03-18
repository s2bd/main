PROGRAM pendulumPeriod

   IMPLICIT NONE

   ! Variables are declared
   REAL,PARAMETER::pi = 3.141592654
   REAL::g = 9.8, L, T
   CHARACTER::std_val_g

   ! User is prompted for the input value(s)
   WRITE (*, "(a)", ADVANCE="no") "Enter value of L (in metres) : "
   READ (*, *) L
   WRITE (*, "(a)", ADVANCE="no") "Use the value of g = 9.8 m/s^2 ? (Y/N) : "
   READ (*, *) std_val_g
   
   ! For customized values of 'g'
   IF (std_val_g == 'N' .OR. std_val_g =='n') THEN
      WRITE (*, "(a)", ADVANCE="no") "Enter custom value of g (in metres per second squared) : "
      READ (*, *) g
   END IF

   ! Equation is used to perform the calculation
   T = 2 * pi * ( sqrt( L / g ))

   ! The result is output
   PRINT *, "The value of T is :", T, " seconds."

END PROGRAM pendulumPeriod
