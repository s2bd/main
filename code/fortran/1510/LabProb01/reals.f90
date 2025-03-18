! COMP 1510 LAB PROBLEM 01
! @author Dewan Mukto (dmimukto)
! @stud_id 202004321

PROGRAM reals

! ensuring that no implicit variables are declared
implicit none

! variables are declared
REAL::largest_positive
REAL::smallest_positive
REAL::epsil

! certain NEW_LINE statements have been added for reading clarity when displaying the output

print *, "COMP 1510 Lab Problem 01 -  reals.f90"
print *, "Written by Dewan Mukto, Student ID : 202004321"
print *, NEW_LINE('a')

! uses the HUGE built-in Fortran function to output the value mentioned
print *, "The largest positive value that can be stored in a REAL variable is : ", NEW_LINE('a'), HUGE(largest_positive)
print *, NEW_LINE('a')

! uses the TINY built-in Fortran function to output the value mentioned
print *, "The smallest positive value that can be stored in a REAL variable is :", NEW_LINE('a'), TINY(smallest_positive)
print *, NEW_LINE('a')

! the smallest value such that 'epsilon' in "1.0 + epsilon" is greater than 1.0 (meaning not equal to 1.0) is found by the built-in Fortran function EPSILON
epsil = 1.0
print *, "The smallest REAL value 'epsilon' such that '1.0 + epsilon' is not equal to 1.0 is :", NEW_LINE('a'), EPSILON(epsil)

END PROGRAM reals
