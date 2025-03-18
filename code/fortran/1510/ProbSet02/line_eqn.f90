! COMP 1510 Problem Set 02
! @author Dewan Mukto (dmimukto)
! @stud_id 202004321

! This program computes the slope-intercept form of a line based on
! coordinates that are input by the user. Also, it identifies if a
! line is vertical or horizontal

PROGRAM Equation
   
   IMPLICIT NONE
! initialization of variables and types
   REAL::x1,x2,y1,y2,m,b

! PRIMARY INPUT INTERFACE
! receives x1,x2,y1,y2 values from user input
   WRITE(*,"(a)",ADVANCE="no") "Enter X1 value: "
   READ (*, *) x1
   WRITE(*,"(a)",ADVANCE="no") "Enter Y1 value: "
   READ (*, *) y1
   WRITE(*,"(a)",ADVANCE="no") "Enter X2 value: "
   READ (*, *) x2
   WRITE(*,"(a)",ADVANCE="no") "Enter Y2 value: "
   READ (*, *) y2
   
! PROCESSING THE DATA
! Deciding beforehand whether the line is vertical or horizontal, in order to avoid certain errors like dividing over zero for the case where the difference of the x coordinates is zero
   IF (abs(y2) - abs(y1) == 0) THEN
      PRINT *, "Line is horizontal"
      WRITE (*,'(a,f9.5)') "Equation: y =", y1
   ELSE IF (abs(x2) - abs(x1) == 0) THEN
      PRINT *, "Line is vertical"
      WRITE (*, '(a,f9.5)') "Equation: x =", x1
   ELSE
! We know the standard "general" equation for a line is
! y = mx + b
! where m = gradient (dy/dx) and b = a constant
      m = ((y2 - y1)/(x2 - x1))
      b = (-m * x1 + y1)
      WRITE (*, '(a,f9.5,a,f9.5)') "Equation: y =",m,"x + ",b
   END IF
   
END PROGRAM Equation
