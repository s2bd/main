PROGRAM fibonacci

  IMPLICIT NONE
  
  INTEGER::N, val1=0, val2=1, next_val
  
  N = 14770000000
  
  if (N == 1) THEN
    write (*, *) val1
  else if (N == 2) THEN
    write (*, *) val2
  end if
  write (*, *) val1
  write (*, *) val2
  do while (N > 2)
    next_val = val1 + val2
    val1 = val2
    val2 = next_val
    write (*,*) next_val
    N = N - 1
  end do
  

END PROGRAM fibonacci
