import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class CommonestWordsFinder {

    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Usage: java CommonestWordsFinder <filename>");
            return;
        }

        String filename = args[0];

        try {
            List<String> commonest = commonestWords(filename);
            System.out.println("Commonest word(s): " + commonest);
        } catch (IOException e) {
            System.err.println("An error occurred while reading the file: " + e.getMessage());
        } catch (UnsupportedLanguageException e) {
            System.err.println("Unsupported text format: " + e.getMessage());
        }
    }

    public static List<String> commonestWords(String filename) throws IOException, UnsupportedLanguageException {
        Map<String, Integer> wordCount = new HashMap<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] words = line.split("\\s+");
                for (String word : words) {
                    if (word.matches("\\p{ASCII}+")) {
                        throw new UnsupportedLanguageException("Please use Ukrainian language and avoid numbers!");
                    }
                    wordCount.put(word, wordCount.getOrDefault(word, 0) + 1);
                }
            }
        }

        if (wordCount.isEmpty()) {
            throw new IOException("File is empty");
        }

        // Find max frequency
        int maxFrequency = Collections.max(wordCount.values());

        // Find words with max frequency
        List<String> commonestWords = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : wordCount.entrySet()) {
            if (entry.getValue() == maxFrequency) {
                commonestWords.add(entry.getKey());
            }
        }

        return commonestWords;
    }

    public static class UnsupportedLanguageException extends Exception {
        public UnsupportedLanguageException(String message) {
            super(message);
        }
    }
}