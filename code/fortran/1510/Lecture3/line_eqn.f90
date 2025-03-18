! COMP1510 Problem Set 02 -- 18 Jan 2022
! @author Dewan Mukto (dmimukto)

Program Equation
   implicit none

! initialization of variables and types
   real::x1,x2,y1,y2,m,c
   integer::k

! PRIMARY INPUT INTERFACE
! receives x1,x2,y1,y2 values from user input
   write(*,"(a)",advance="no") "Enter X1 value: "
   read (*, *) x1
   write(*,"(a)",advance="no") "Enter Y1 value: "
   read (*, *) y1
   write(*,"(a)",advance="no") "Enter X2 value: "
   read (*, *) x2
   write(*,"(a)",advance="no") "Enter Y2 value: "
   read (*, *) y2
   
! PROCESSING THE DATA
! Deciding beforehand whether the line is vertical or horizontal, in order to avoid certain errors like dividing over zero for the case where the difference of the x coordinates is zero
   if (y2 - y1 == 0) then
      print *, "Line is vertical"
      print *, "Equation: x =", (x2 - x1)
   else if (x2 - x1 == 0) then
      print *, "Line is horizontal"
      print *, "Equation: y =", (y2 - y1)
   else
! We know the standard "general" equation for a line is
! y = mx + c
! where m = gradient (dy/dx) and c = constant
      m = ((y2 - y1)/(x2 - x1))
      c = (-m * x1 + y1)
      print *, "Equation: y =",m,"x + ",c
   end if
   
End Program Equation
