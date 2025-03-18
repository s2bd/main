! COMP 1510 Lab Problem 06
! @author Dewan Mukto (dmimukto)
! @stud_id 202004321

! This is a module with a derived data type that implements a 3D vector

module type_vector3d
  implicit none
  type::vector3d
    real :: x,y,z
  end type vector3d
  
  contains
  
  real function dot_product(a,b) result(p)
    implicit none
    type(vector3d),intent(in) :: a,b
    
    p = a%x * b%x + a%y * b%y + a%z * b%z
    
  end function
  
end module
