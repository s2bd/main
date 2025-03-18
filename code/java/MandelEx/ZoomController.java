public class ZoomController {
    private double zoom;
    private double xOffset;
    private double yOffset;
    private double panSpeed;

    public ZoomController(double initialZoom) {
        this.zoom = initialZoom;
        this.xOffset = 0; // Initial x offset
        this.yOffset = 0; // Initial y offset
        this.panSpeed = 20 / zoom;
    }

    public double getZoom() {
        return zoom;
    }

    public double getPanSpeed() {
        return panSpeed;
    }

    public void zoomIn() {
        zoom *= 1.1;
        panSpeed = 20 / zoom;
    }

    public void zoomOut() {
        zoom /= 1.1;
        panSpeed = 20 / zoom;
    }

    public void pan(double deltaX, double deltaY) {
        xOffset += deltaX;
        yOffset += deltaY;
    }

    public double getXOffset() {
        return xOffset;
    }

    public double getYOffset() {
        return yOffset;
    }
}
