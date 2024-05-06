package tn.esprit.coco.configuration;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class BadWordFilter {
    private static final Set<String> BAD_WORDS = new HashSet<>(Arrays.asList("ffff", "bbbb", "shit"));

    public static String filterText(String input) {
        for (String word : BAD_WORDS) {
            input = input.replaceAll("(?i)" + word, "****");
        }
        return input;
    }
}
