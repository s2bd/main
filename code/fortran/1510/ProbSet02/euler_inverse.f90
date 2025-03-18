! COMP 1510 Problem Set 02
! @author Dewan Mukto (dmimukto)
! @stud_id 202004321

! This program computes the inverse of Euler's Number 1/e using the partial sum

PROGRAM euler_inverse

  IMPLICIT NONE
  ! variables are declared
  DOUBLE PRECISION::partial_sum=0.0 ! The 'double precision' data type is used instead of 'real' since it has a more accurate floating point representation
  INTEGER::k=0, y=0, N=1, factorial
  
  ! Partial sum operations via a DO loop
  DO WHILE (partial_sum - EXP(1.) < 1e-6)
    print *, "k =", k, "sum =", partial_sum
    partial_sum = partial_sum + (((-1)**k) / real(factorial(k)))
    k = k + 1
  END DO
  
  ! Assigning the last value of k as the number of terms N required for reaching an accurate approximation of the exact value of the inverse of Euler's number
  N = k
  
  ! The results are output
  WRITE (*, '(a, f10.8)') "Value from EXP(-1.) :   ", EXP(-1.)
  WRITE (*, '(a, i4)') "Exact value found at N = ", N

END PROGRAM euler_inverse

! Function for calculating the factorials
FUNCTION factorial(k) result(y)

  IMPLICIT NONE
  INTEGER::k, y, nth_fact=1, n=1
  
  DO WHILE (n <= k)
    nth_fact = nth_fact * n
    n = n + 1
  END DO
  
  y = nth_fact
  
END FUNCTION factorial
