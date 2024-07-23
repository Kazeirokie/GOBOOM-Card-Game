import java.util.ArrayList;
import java.util.List;

public class Caretaker extends GUIGame {

    private static List<Memento> history = new ArrayList<>();
    private static int currentIndex = -1;

    public static void save(GameLogic game, Deck deck, Table table) {
        // Create a new Memento and add it to the history
        Memento memento = new Memento(game, deck, table);
        history.add(memento);
        currentIndex = history.size() - 1;
    }

    public static void undo(GameLogic game, Deck deck, Table table) {
        if (currentIndex > 0) {
            currentIndex--;
            Memento memento = history.get(currentIndex);
            memento.restore(game, deck, table);

        }
    }

    public static void redo(GameLogic game, Deck deck, Table table) {
        if (currentIndex < history.size() - 1) {
            currentIndex++;
            Memento memento = history.get(currentIndex);
            memento.restore(game, deck, table);
        }
    }
}
