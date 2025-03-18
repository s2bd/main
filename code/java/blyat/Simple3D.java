import javax.swing.*;
import javax.media.j3d.*;
import javax.vecmath.*;
import com.sun.j3d.utils.universe.*;

public class Simple3D extends JFrame {
    public Simple3D() {
        // Set up the JFrame
        setTitle("Simple 3D Example");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        // Create a SimpleUniverse
        SimpleUniverse universe = new SimpleUniverse();

        // Create a BranchGroup to hold the scene
        BranchGroup group = new BranchGroup();
        
        // Create a shape (a simple cube in this case)
        ColorCube cube = new ColorCube(0.4);
        group.addChild(cube);

        // Set up the view and add it to the universe
        universe.getViewingPlatform().setNominalViewingTransform();
        universe.addBranchGraph(group);
        
        // Make the frame visible
        setVisible(true);
    }

    public static void main(String[] args) {
        // Run the 3D application
        SwingUtilities.invokeLater(() -> new Simple3D());
    }
}
