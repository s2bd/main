PROGRAM Rectangles
  IMPLICIT NONE
  REAL::x1,x2,y1,y2
  REAL::x3,x4,y3,y4
  REAL::x,y

  ! Define rectangle 1
  x1=1; y1=1; x2=3; y2=4
  ! Define rectangle 2
  x3=4; y3=5; x4=8; y4=9

  WRITE(*,*) 'Enter coordinates:'
  READ(*,*) x,y
    
  IF (x>=x1.AND.x<=x2.AND.y>=y1.AND.y<=y2) THEN
     WRITE(*,*) 'Inside rectangle 1'
  ELSE IF (x>=x3.AND.x<=x4.AND.y>=y3.AND.y<=y4) THEN
     WRITE(*,*) 'Inside rectangle 2'
  ELSE
     WRITE(*,*) 'Outside both rectangles'
  END IF
END PROGRAM Rectangles

