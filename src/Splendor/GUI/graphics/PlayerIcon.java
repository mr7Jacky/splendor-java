package Splendor.GUI.graphics;

import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;

/**
 * This enumerator provides 4 player icons with effect and graphics
 * Since the maximum allowable player in the game is 4,
 * We only provide 4 different player icons
 * All picture are from the link below
 * @see <a href="https://www.biography.com/.image/ar_1:1%2Cc_fill%2Ccs_srgb%2Cg_face%2Cq_auto:good%2Cw300/MTY2MzU4MjUzMDA4MDcwMzE4/portrait-of-leonardo-da-vinci-1452-1519-getty.jpg">
 *     https://www.biography.com/.image/ar_1:1%2Cc_fill%2Ccs_srgb%2Cg_face%2Cq_auto:good%2Cw300/MTY2MzU4MjUzMDA4MDcwMzE4/portrait-of-leonardo-da-vinci-1452-1519-getty.jpg</a>
 * @see <a href="https://masterpieces-of-art.com/wp-content/uploads/2014/08/mona-lisa-leonardo-da-vinci-e1427514342170-520x520.jpg">
 *         https://masterpieces-of-art.com/wp-content/uploads/2014/08/mona-lisa-leonardo-da-vinci-e1427514342170-520x520.jpg</a>
 * @see <a href="https://www.biography.com/.image/t_share/MTE5NTU2MzE1OTY2NDQwOTcx/raphael-41051-1-402.jpg">
 *     https://www.biography.com/.image/t_share/MTE5NTU2MzE1OTY2NDQwOTcx/raphael-41051-1-402.jpg</a>
 * @see <a href="https://www.biography.com/.image/ar_1:1%2Cc_fill%2Ccs_srgb%2Cg_face%2Cq_auto:good%2Cw_300/"+
 *             "MTY2NTIzMzc4MTI2MDM4MjM5/vincent_van_gogh_self_portrait_painting_musee_dorsay_via_wikimedia_"+
 *             "commons_promojpg.jpg">https://www.biography.com/.image/ar_1:1%2Cc_fill%2Ccs_srgb%2Cg_face%2Cq_auto:good%2Cw_300/"+
 *  *             "MTY2NTIzMzc4MTI2MDM4MjM5/vincent_van_gogh_self_portrait_painting_musee_dorsay_via_wikimedia_"+
 *  *             "commons_promojpg.jpg</a>
 * @author Jacky Lin, Yili Wang
 */
public enum PlayerIcon {

    PLAYER_1("https://www.biography.com/.image/ar_1:1%2Cc_fill%2Ccs_srgb%2Cg_face%2Cq_auto:good%2Cw_" +
            "300/MTY2MzU4MjUzMDA4MDcwMzE4/portrait-of-leonardo-da-vinci-1452-1519-getty.jpg"),
    PLAYER_2("https://masterpieces-of-art.com/wp-content/uploads/2014/08/mona-lisa-leonardo"+
            "-da-vinci-e1427514342170-520x520.jpg"),
    PLAYER_3("https://www.biography.com/.image/t_share/MTE5NTU2MzE1OTY2NDQwOTcx/raphael-41051-1-402.jpg"),
    PLAYER_4("https://www.biography.com/.image/ar_1:1%2Cc_fill%2Ccs_srgb%2Cg_face%2Cq_auto:good%2Cw_300/"+
            "MTY2NTIzMzc4MTI2MDM4MjM5/vincent_van_gogh_self_portrait_painting_musee_dorsay_via_wikimedia_"+
            "commons_promojpg.jpg");

    /** the shape of the player*/
    private Circle shape;
    /** the url string of the player portrait*/
    private String Url;

    /**
     * the constructor of the enumerator, initialize the effect and looking-like of the circle
     * representing different player with different pictures, from different website
     * @param url the url link to the picture of the player
     * @author Jacky Lin, Yili Wang
     */
    PlayerIcon(String url) {
        Url = url;
        shape = new Circle(45);
        shape.setStroke(Color.BLACK);
        Image img = new Image(url,false);
        shape.setFill(new ImagePattern(img));
        shape.setEffect(new DropShadow(+25d, 0d, +2d, Color.BLACK));
    }

    /**
     * Getter method of the shape
     * @return the circle represents the player
     */
    public Circle getShape() { return shape; }

    /**
     * Get the url for the playerIcon image
     * @return the url of the image
     */
    public String getUrl() { return Url; }
}
