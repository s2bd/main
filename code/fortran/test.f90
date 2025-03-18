program tester
implicit none
integer::num
real::total, amt
amt = 5
write(*,'(a)') "How many items?"
read (*,*) num
write(*, '(a)') "What was total?"
read (*,*) total
if (num <= 5) then
  amt = amt + num
else if (num > 5) then
  amt = amt + num*0.75
end if
if (total > 100) then
  amt = amt*0.85
end if ! for total > 100
write (*, '(a,f5.2)') "The total cost is $",amt
end program tester
