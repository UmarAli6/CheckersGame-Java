package view;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Class representing Highscore.
 * @author Rabi_S & Umar_A
 */
public class Highscore {

    private ArrayList<String> highscore;

    public Highscore() throws IOException, ClassNotFoundException {
        highscore = new ArrayList();
    }

    public void deSerFromFile(String filename) throws IOException, ClassNotFoundException {
        highscore.addAll(ScoreIO.deSerializeFromFile(filename));
        Collections.sort(highscore);
    }

    public void serToFile(String filename) throws IOException {
        ScoreIO.serializeToFile(filename, highscore);
        Collections.sort(highscore);
    }

    public void addPlayersToList(String playerName, int noOfMoves) {
        String player = noOfMoves + "\t\t\t\t" + playerName + "\n";
        highscore.add(player);
        Collections.sort(highscore);
    }

    @Override
    public String toString() {
        String info = "";

        for (int i = 0; i < highscore.size(); i++) {
            info += highscore.get(i);
        }

        return info;
    }
}
