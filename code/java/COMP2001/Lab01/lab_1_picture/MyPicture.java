
/**
 * Contains a single method that automatically sets up and renders the shapes
 * according to what they wanted
 *
 * @author Dewan Mukto (dmimukto)
 * @version 20 Sept 2022
 */
public class MyPicture
{
    // instance variables
    private Circle sun;
    private Circle land;
    private Person person1;
    private Person person2;

    /**
     * Generates the basic shapes for the image
     */
    public MyPicture()
    {
        sun = new Circle();
        land = new Circle();
        person1 = new Person();
        person2 = new Person();
    }

    /**
     * Draws the image by manipulating the shapes
     */
    public void draw()
    {
        // the sun
        sun.changeSize(45);
        sun.moveHorizontal(-100);
        sun.moveVertical(12);
        sun.makeVisible();
        sun.changeColor("yellow");
        // the land
        land.changeSize(1000);
        land.moveHorizontal(-450);
        land.moveVertical(140);
        land.makeVisible();
        land.changeColor("green");
        // the smaller person
        person1.changeSize(50,25);
        person1.moveHorizontal(-32);
        person1.moveVertical(16);
        person1.makeVisible();
        // the normal person
        person2.makeVisible();
    }
    /**
     *  Animates the sun to descend below ground level, to produce a 'sunset'
     */
    public void sunset() {
        sun.slowMoveVertical(200);
    }
}
