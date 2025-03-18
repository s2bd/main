! COMP 1510 Lab Problem 06
! @author Dewan Mukto (dmimukto)
! @stud_id 202004321

! This program tests the implemented functionality of the module "type_vectors" by
! computing the dot product of two non-zero predefined vectors

program vector3d_test
  ! Calling in the required module
  use type_vector3d
  
  ! Making sure no variables are declared implicitly
  implicit none
  
  ! Declaring variables as the derived data type
  type(vector3d)::vector_1, vector_2
  
  ! Defining the components of the 3D vectors
  vector_1%x = 1.
  vector_1%y = 2.
  vector_1%z = 3.
  vector_2%x = 4.
  vector_2%y = 5.
  vector_2%z = 6.
  
  ! Testing the "dot_product" function by outputting the dot product in itself
  write (*,'(A,F5.1)') "The dot product of (1.,2.,3.) and (4.,5.,6.) is", dot_product(vector_1,vector_2)
  
end program vector3d_test
