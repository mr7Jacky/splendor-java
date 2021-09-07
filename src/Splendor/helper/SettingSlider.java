/* *****************************************
 * CSCI205 - Software Engineering and Design
 * Fall 2019
 * Instructor: Prof. Brian King
 *
 * Name: Jacky Lin
 * Section: 9:00-9:52 AM
 * Date: 12/6/19
 * Time: 11:26 PM
 *
 * Project: lab
 * Package: lab
 * Class: CSCI205
 *
 * Description:
 *
 * ****************************************
 */

package Splendor.helper;

import javafx.scene.control.Slider;

/**
 * The slider object that using in changing the setting
 * With the certain property
 * @author Jacky Lin
 */
public class SettingSlider {

    /** the special customized slider */
    private Slider theSlider;

    /**
     * The constructor the initialize the slider
     * @param min min number
     * @param max max number
     */
    public SettingSlider(int min, int max, int init) {
        theSlider = new Slider(min,max,init);
        theSlider.setShowTickLabels(true);
        theSlider.setShowTickMarks(true);
        theSlider.setSnapToTicks(true);
        theSlider.setMajorTickUnit(1);
        theSlider.setMinorTickCount(0);
    }

    /**
     * Getter method of the slider
     * @return the slider object
     */
    public Slider getTheSlider() { return theSlider; }
}
