! COMP 1510 LAB PROBLEM 02
! @author Dewan Mukto (dmimukto)
! @stud_id 202004321

PROGRAM rebate
  ! making sure that all variables are explicitly defined
  IMPLICIT NONE
  ! variables are declared
  REAL::tuition, total_tuition, total_rebate=0.0
  INTEGER::num_courses
  
  ! input section, where the input data is retrieved
  WRITE (*, '(a)', ADVANCE='no') "Enter number of courses : "
  READ (*, *) num_courses
  WRITE (*, '(a)', ADVANCE='no') "Enter tuition amount per course : "
  READ (*, *) tuition
  
  ! determining the total tuition based on the number of the courses
  total_tuition = tuition * num_courses
  
  ! main processing section, deciding the amounts and percentages of rebate to apply.
  IF (num_courses == 1) THEN
    IF (total_tuition <= 500) THEN
      !----------------------- 80% rebate on the first $500
      total_rebate = total_rebate + total_tuition*0.80
    ELSE IF (total_tuition > 500 .and. total_tuition <= 950) THEN
      !----------------------- 80% rebate on the first $500
      total_rebate = total_rebate + 500*0.80
      total_tuition = total_tuition - 500
      !----------------------- 60% on the next $450
      total_rebate = total_rebate + total_tuition*0.60
    ELSE IF (total_tuition > 950) THEN
      !----------------------- 80% rebate on the first $500
      total_rebate = total_rebate + 500*0.80
      total_tuition = total_tuition - 500
      !----------------------- 60% rebate on the next $450
      total_rebate = total_rebate + 450*0.60
      total_tuition = total_tuition - 450
      !----------------------- 40% rebate on the remainder
      total_rebate = total_rebate + total_tuition*0.40
    END IF
  ! For more than 1 course, the following rebate scheme is applied
  ELSE IF (num_courses > 1) THEN
    IF (total_tuition <= 600) THEN
      !----------------------- 70% rebate on the first $600
      total_rebate = total_rebate + total_tuition*0.70
    ELSE IF (total_tuition > 600 .AND. total_tuition <= 1100) THEN
      !----------------------- 70% rebate on the first $600
      total_rebate = total_rebate + 600*0.70
      total_tuition = total_tuition - 600
      !----------------------- 50% rebate on the next $500
      total_rebate = total_rebate + total_tuition*0.50
    ELSE IF (total_tuition > 1100) THEN
      !----------------------- 70% rebate on the first $600
      total_rebate = total_rebate + 600*0.70
      total_tuition = total_tuition - 600
      !----------------------- 50% rebate on the next $500
      total_rebate = total_rebate + 500*0.50
      total_tuition = total_tuition - 500
      !----------------------- 30% rebate on the remainder
      total_rebate = total_rebate + total_tuition*0.30
    END IF
  END IF
  
  ! output section, where the total rebate is displayed
  WRITE (*, '(a,f9.2)') "The total tuition rebate is: $",total_rebate
  
END PROGRAM rebate
