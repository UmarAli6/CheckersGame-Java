package view;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.IOException;
import java.util.List;

/**
 *
 * @author Rabi_S & Umar_A
 */
public class ScoreIO {

    public static void serializeToFile(String filename, List<String> players) throws IOException {
        ObjectOutputStream out = null;
        try {
            out = new ObjectOutputStream(new FileOutputStream(filename));
            out.writeObject(players);
        } finally {
            if (out != null) {
                out.close();
            }
        }
    }

    public static List<String> deSerializeFromFile(String filename) throws IOException, ClassNotFoundException {
        ObjectInputStream in = null;
        try {
            in = new ObjectInputStream(new FileInputStream(filename));
            List<String> players = (List<String>) in.readObject();
            return players;
        } finally {
            if (in != null) {
                in.close();
            }
        }
    }
}
