! COMP 1510 Problem Set 02
! @author Dewan Mukto (dmimukto)
! @stud_id 202004321

! This program computes the toll bridge charges for the
! vehicles that cross, based on the vehicle type and
! their weight.

PROGRAM TollBridge

   IMPLICIT NONE
! initialization of variables, constants and types
   CHARACTER::vehicle, weight_comparison
   REAL::toll_amount
   REAL,PARAMETER::car_rate=14.00, bus_rate=18.00, truck_rate=17.00, truck_overrate=25.00

   PRINT *, "C for car, B for bus, T for truck"
   WRITE (*,"(a)",ADVANCE="no") "Enter type (C/B/T): "
   READ (*, *) vehicle

   SELECT CASE (vehicle)
   CASE ("C")
      toll_amount = car_rate
      WRITE (*,'(a,f5.2)') "Your toll fee is: $", toll_amount
   CASE ("B")
      toll_amount = bus_rate
      WRITE (*,'(a,f5.2)') "Your toll fee is: $", toll_amount
   CASE ("T")
      PRINT *, "If weight is less than or equal to 14000 pounds, enter '<'"
      PRINT *, "Or else enter '>' if weight is more than 14000 pounds."
      WRITE (*,"(a)",ADVANCE="no") "Enter symbol: "
      READ (*,*) weight_comparison
      IF (weight_comparison=="<") THEN
         toll_amount = truck_rate
         WRITE (*,'(a,f5.2)') "Your toll fee is: $", toll_amount
      ELSE IF (weight_comparison==">") THEN
         toll_amount = truck_overrate
         WRITE (*,'(a,f5.2)') "Your toll fee is: $", toll_amount
      END IF
   END SELECT

END PROGRAM TollBridge
