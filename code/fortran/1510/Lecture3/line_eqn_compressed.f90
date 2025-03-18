Program Equation
   implicit none
   real::x1,x2,y1,y2,m,c
   integer::k
   write(*,"(a)",advance="no") "Enter X1 value: "
   read (*, *) x1
   write(*,"(a)",advance="no") "Enter Y1 value: "
   read (*, *) y1
   write(*,"(a)",advance="no") "Enter X2 value: "
   read (*, *) x2
   write(*,"(a)",advance="no") "Enter Y2 value: "
   read (*, *) y2
   if (y2 - y1 == 0) then
      print *, "Line is vertical"
      print *, "Equation: x =", (x2 - x1)
   else if (x2 - x1 == 0) then
      print *, "Line is horizontal"
      print *, "Equation: y =", (y2 - y1)
   else
      m = ((y2 - y1)/(x2 - x1))
      c = (-m * x1 + y1)
      print *, "Equation: y =",m,"x + ",c
   end if 
End Program Equation
