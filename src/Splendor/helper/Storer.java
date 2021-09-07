/* *****************************************
 * CSCI205 - Software Engineering and Design
 * Fall 2019
 * Instructor: Prof. Brian King
 *
 * Name: Jacky Lin
 * Section: 9:00-9:52 AM
 * Date: 11/11/19
 * Time: 12:44 PM
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

import java.io.*;

/**
 * This class writes some data file in the game. It will be read when the game initializes.
 * @see <a href= "https://kodejava.org/how-do-i-store-objects-in-file/">
 *     https://kodejava.org/how-do-i-store-objects-in-file/</a>
 * @author Jacky Lin
 */
public class Storer {

    /**
     * This method stores a card data to cards.dat
     * @param o the object to store
     * @author Jacky Lin
     */
    public void store(Object o) {
        File data = new File(this.getClass().getResource("/Splendor/data").getPath() + "/cards.dat");
        try (FileOutputStream fos = new FileOutputStream(data)) {
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(o);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method reads stored card data
     * @return an pre-stored object
     * @author Jacky Lin
     */
    public Object read() {
        File data = new File(this.getClass().getResource("/Splendor/data").getPath() + "/cards.dat");
        Object o = null;
        try (FileInputStream fis = new FileInputStream(data)) {
            ObjectInputStream ois = new ObjectInputStream(fis);
            o = ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return o;
    }

}
