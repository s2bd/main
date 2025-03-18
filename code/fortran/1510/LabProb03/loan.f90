! COMP 1510 LAB PROBLEM 03
! @author Dewan Mukto (dmimukto)
! @stud_id 202004321

PROGRAM loan
  ! Making sure that all variables are explicitly defined
  IMPLICIT NONE
  
  ! Variables are named according to the question's pseudocode
  ! x : loan amount, r : interest rate, n : loan period, p : monthly payment rate
  REAL::x, r, p, interest, principal
  INTEGER::n, month=0
  
  ! Input values and store them to the variables they represent
  write (*, '(a)') "Enter the amount of the loan"
  read (*, *) x
  write (*, '(a)') "Enter the interest rate"
  read (*, *) r
  write (*, '(a)') "Enter the time (in months)"
  read (*, *) n
  
  ! The monthly payment amount is pre-calculated
  p = x * ((r * (1+r)**n) / ((1+r)**n - 1))
  
  ! The output is presented in a type of 'table' format, seperated into neat columns
  write (*, '(a,"    ",a,"  ",a,"  ",a,"  ",a)') "Month", "Payment", "Interest", "Principal", "Balance"
  write (*, '(i3,"    ",a,"  ",a,"  ",a,"   ",f8.2)') 0, "        ","         ","       ",x
  ! Main looping algorithm that displays the loan information
  do while ((x-p) > 0 .and. n > 0)
    month = month + 1
    interest = x * r
    principal = p - interest
    x = x - principal
    write (*, '(i3,"    ",f8.2,"  ",f8.2,"  ",f8.2,"   ",f8.2)')  month, p, interest, principal, x
    n = n - 1
  end do
  
  ! One more 'special case' iteration of the algorithm for clearing up the loan (if there are enough months and the payment amount is reduced) 
  if (n > 0) then
    month = month + 1
    interest = x * r
    principal = x
    x = x - principal
    p = principal + interest
    write (*, '(i3,"    ",f8.2,"  ",f8.2,"  ",f8.2,"   ",f8.2)')  month, p, interest, principal, x
    n = n - 1
  end if
  
  
END PROGRAM loan
