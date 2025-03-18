! ??????????????????????????
! author name
! additional details

! FORTRAN PROGRAM TEMPLATE 101
! does not work on its own; must define variables and values properly
! use any text editor/IDE to write/modify the code
! use "gfortran FILENAME.f90 -o FILENAME" to compile
! then "./FILENAME" to run

PROGRAM name

  IMPLICIT NONE
  
  REAL::var1
  REAL, PARAMETER::var2
  CHARACTER::var3
  INTEGER::var4
  
  ! basic input and storing value to variable
  WRITE (*, '(a)', advance='no') "Input statement context : "
  READ (*, *) variable
  
  ! if conditionals demo
  IF ( conditional ) THEN
    ! do what
    PRINT *, "if demo 1"
  ELSE IF ( conditional ) THEN
    ! do what
    PRINT *, "if demo 2"
  END IF
  
  ! select case conditionals demo
  SELECT CASE ( something )
  CASE ( conditional value )
    ! do what
  CASE ( conditional value )
    ! do what
  END SELECT
  
  ! output normal
  WRITE (*, *) "Text"
  ! output formatted
  WRITE (*, '(a, f5.2)') "Text", floatvalue ! total 5 digits, 2 digits shown after decimal
  
END PROGRAM name
