program pendulumPeriod

   implicit none

   ! Variables are declared
   real::pi = 3.141592654
   real::g = 9.8
   real::L
   real::T

   ! User is prompted for the input value(s)
   write (*, "(a)", advance="no") "Enter value of L (in metres) :"
   read (*, *) L

   ! Equation is used to perform the calculation
   T = 2 * pi * ( sqrt( L / g ))

   ! The result is output
   print *, "The value of T is :", T, " seconds."

end program pendulumPeriod
