package client.components;


import javafx.scene.image.ImageView;

public class LogoImage extends ImageView {
    /**
     * Constructor for LogoImage
     */
    public LogoImage() {
        super("/client/images/logo-without-bg.png");
        super.setFitHeight(100);
        super.setFitWidth(100);
    }
}
