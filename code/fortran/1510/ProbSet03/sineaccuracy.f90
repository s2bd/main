! COMP 1510 Problem Set 03
! @author Dewan Mukto (dmimukto)
! @stud_id 202004321

! This program 

program SineAccuracy
  implicit none
  integer::k
  real::a,b,c,d
  logical::l
  real,dimension(2,1600)::myArray

  open(unit=55,file="result.txt")

  do k=1,1600
    read(55,'(F10.5,E15.8)') a,b
    myArray(1,k) = a
    myArray(2,k) = b
  end do

  close(55)
  
  open(unit=56,file="p.csv")

  do k=1,1600
    c = sin(myArray(1,k))
    d = abs(myArray(2,k)-c)
    write (56,'(F10.5,",",E17.10,",",E17.10,",",E17.10)')  myArray(1,k),myArray(2,k),c,d
    write (*,'(F10.5,",",E17.10)') myArray(1,k),myArray(2,k)
  end do
  
  close(56)

end program
