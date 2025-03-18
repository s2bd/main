! COMP 1510 Lab Problem 04
! @author Dewan Mukto (dmimukto)
! @stud_id 202004321

! This program computes tables of binomial coefficients for values of k from 0 to n

PROGRAM binomial

  IMPLICIT NONE
  ! Variables are declared
  INTEGER::binom, k=0, n, binomial_coeff, n_factorial, factorial
  CHARACTER::str
  
  ! User input is provided via command line argument
  call GET_COMMAND_ARGUMENT(1,str)
  n = nint(real(str))

  write (*, '(a,"    ", a)') "k", "n!/(k!(n-k)!)"
  write (*, '(a)') "======================"
  do k = 0, n

    ! For each value of k, the binomial coefficient is calculated
    binom = binomial_coeff(n, k)
    write (*, '(i6,"    ",i8)') k, binom
  end do

END PROGRAM binomial



! Function that takes 2 integer arguments, n and k, and returns the value of the binomial coefficient
FUNCTION binomial_coeff(n, k) result(binom)
  IMPLICIT NONE
  INTEGER::n, k, s, binom, denom, n_factorial
  s = n - k

  binom = n_factorial(n) / n_factorial(k) * n_factorial(s)
  
END FUNCTION

! Function that takes an integer argument, n, and returns n!
FUNCTION n_factorial(n) result(n_fact)
  IMPLICIT NONE
  INTEGER, INTENT(IN)::n
  INTEGER:: n_fact, factorial

  n_fact = factorial(n)
  
END FUNCTION

! Function for calculating the factorials
FUNCTION factorial(inp) result(y)

  IMPLICIT NONE
  INTEGER, INTENT(IN)::inp
  INTEGER:: y, nth_fact=1, n_count=1
  
  DO WHILE (n_count <= inp)
    nth_fact = nth_fact * n_count
    n_count = n_count + 1
  END DO

  y = nth_fact
  
END FUNCTION factorial
