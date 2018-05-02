package hk.ust.cse.comp4521.watsup.dummy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import hk.ust.cse.comp4521.watsup.Event;

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 * <p>
 * TODO: Replace all uses of this class before publishing your app.
 */
public class EventContent {

    /**
     * An array of sample (dummy) items.
     */
    public static final List<Event> ITEMS = new ArrayList<Event>();

    /**
     * A map of sample (dummy) items, by ID.
     */
    public static final Map<String, Event> ITEM_MAP = new HashMap<String, Event>();

    private static final int COUNT = 5;

    static {
        // Add some sample items.
        for (int i = 1; i <= COUNT; i++) {
            addItem(createDummyItem(i));
        }
    }

    private static void addItem(Event item) {
        ITEMS.add(item);
//        ITEM_MAP.put(item.id, item);
    }

    private static Event createDummyItem(int position) {
        return null;
//                new Event(String.valueOf(position), "Item " + position, makeDetails(position));
    }

    private static String makeDetails(int position) {
        StringBuilder builder = new StringBuilder();
        builder.append("Details about Item: ").append(position);
        for (int i = 0; i < position; i++) {
            builder.append("\nMore details information here.");
        }
        return builder.toString();
    }


}
