Program Subroutine
   implicit none
   
   real::x,mc,sine,cosine
   integer::k
   
   do k = -100,100
      x = real(k) * 1e-1
      call compute_several(x,mc,sine,cosine)
      write(*,'(E20.10,",",E20.10,",",E20.10",",E20.10)') x, mc, sine, cosine
      
   end do
   
End Program Subroutine


subroutine compute_several(x, mc, sine, cosine)
   real,intent(in)::x
   real,intent(out)::mc,sine,cosine
   real::Macaulay
   mc = Macaulay(x)
   sine = sin(x)
   cosine = cos(x)
end subroutine

FUNCTION Macaulay(x) result(y)
   real,intent(in)::x
   real::y
   
   if (x>0) then
      y = x
   else
      y = 0
   end if
   
END FUNCTION
