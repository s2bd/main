! COMP 1510 Problem Set 02
! @author Dewan Mukto (dmimukto)
! @stud_id 202004321

! This program computes the inverse of Euler's Number 1/e using the partial sum


PROGRAM euler_inverse

  IMPLICIT NONE
  ! variables are declared
  DOUBLE PRECISION::partial_sum=0.0, last_partial_sum=0.0 ! The 'double precision' data type is used instead of 'real' since it has a more accurate floating point representation
  INTEGER::k=0, y=0, N=1, factorial
  
  ! main DO loop for iterating over values of 'k' until the desired value is reached
  !DO k=0, N
   ! print *, "k =", k, "N =", N, "sum =", partial_sum
   ! partial_sum = partial_sum + (((-1)**k) / real(factorial(k)))
  !END DO
  
  DO WHILE (partial_sum - EXP(1.) < 0)
    print *, "k =", k, "sum =", partial_sum
    partial_sum = partial_sum + (((-1)**k) / real(factorial(k)))
    k = k + 1
  END DO
  
  N = k
  
  WRITE (*, '(a, i4)') "Exact value found at N = ", N
  
  ! The results are output
  N = k-1
  DO k=0, 12
    last_partial_sum = last_partial_sum + (((-1)**k) / gamma(real(k))))
  END DO
  print *, "k =", k, "sum =", last_partial_sum
  WRITE (*, '(a, f9.7)') "Value obtained by partial sum : ", last_partial_sum
  WRITE (*, '(a, f9.7)') "Value obtained from EXP(-1.) :  ", EXP(-1.)

END PROGRAM euler_inverse


FUNCTION factorial(k) result(y)

  IMPLICIT NONE
  INTEGER::k, y, nth_fact=1, n=1
  
  DO WHILE (n <= k)
    nth_fact = nth_fact * n
    n = n + 1
  END DO
  
  y = nth_fact
  
END FUNCTION factorial
