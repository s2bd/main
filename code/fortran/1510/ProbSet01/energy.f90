PROGRAM totalEnergy

   IMPLICIT NONE

   ! Variables are declared
   REAL::g = 9.8, m, v, h, total_energy
   CHARACTER::std_val_g

   ! User is prompted for the input value(s)
   WRITE (*, "(a)", ADVANCE="no") "Enter value of m (in kg) : "
   READ (*, *) m
   WRITE (*, "(a)", ADVANCE="no") "Enter value of v (in metres per second) : "
   READ (*, *) v
   WRITE (*, "(a)", ADVANCE="no") "Enter value of h (in metres) : "
   READ (*, *) h
   WRITE (*, "(a)", ADVANCE="no") "Use the value of g = 9.8 m/s^2 ? (Y/N) : "
   READ (*, *) std_val_g
   
   ! For customized values of 'g'
   IF (std_val_g == 'N' .OR. std_val_g =='n') THEN
      WRITE (*, "(a)", ADVANCE="no") "Enter custom value of g (in metres per second squared) : "
      READ (*, *) g
   END IF
   
   ! Conditional statements to decide when to apply which formula
   IF (v == 0) THEN
      ! For objects at rest, calculate only potential energy as total energy
      total_energy = m * g * h
   ELSE IF (h == 0) THEN
      ! For objects at ground level, calculate only kinetic energy as total energy
      total_energy = ( m * v**2 ) / 2
   ELSE
      ! For objects somewhere in between, calculate the sum of both energies
      total_energy =  (m * g * h) + (( m * v**2 ) / 2)
   END IF

   ! The result is output
   PRINT *, "The total energy of the object is :", total_energy, " joules."

END PROGRAM totalEnergy
