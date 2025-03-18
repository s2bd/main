! COMP 1510 LAB PROBLEM 02
! @author Dewan Mukto (dmimukto)
! @stud_id 202004321

program rebate
  ! making sure that all variables are explicitly defined
  implicit none
  ! variables are declared
  real::tuition, total_tuition, total_rebate=0.0
  integer::num_courses
  
  ! input section, where the input data is retrieved
  write (*, "(a)", advance='no') "Enter number of courses : "
  read (*, *) num_courses
  write (*, "(a)", advance='no') "Enter tuition amount per course : "
  read (*, *) tuition
  ! determining the total tuition based on the number of courses
  total_tuition = tuition * num_courses

  ! main processing section, deciding the amounts and percentages of rebate to apply.
  if (num_courses == 1) then
    if (total_tuition >= 500) then
    ! 80% on the first $500
      total_rebate = total_rebate + 500*0.80
      total_tuition = total_tuition - 500
    end if
    if (total_tuition >= 450) then
    ! 60% on the next $450
      total_rebate = total_rebate + 450*0.60
      total_tuition = total_tuition - 450
    end if
    ! 40% on the remainder
    total_rebate = total_rebate + total_tuition*0.40
   ! For more than 1 course, the following rebate scheme is applied
   else if (num_courses > 1) then
     if (total_tuition >= 600) then
     ! 70% on the first $600
      total_rebate = total_rebate + 600*0.70
      total_tuition = total_tuition - 600
     end if
     if (total_tuition >= 500) then
     ! 50% on the next $500
      total_rebate = total_rebate + 500*0.50
      total_tuition = total_tuition - 500
     end if
     ! 30% on the remainder
     total_rebate = total_rebate + total_tuition*0.30
   end if

  ! output section, where the total rebate is displayed
  write (*, '(a,f9.2)') "The total tuition rebate is: $",total_rebate

end program rebate
