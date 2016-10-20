package rajeevpc.anagram;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;

public class AnagramDictionary {

    private static final int MIN_NUM_ANAGRAMS = 5;
    private static final int DEFAULT_WORD_LENGTH = 3;
    private static final int MAX_WORD_LENGTH = 7;
    private Random random = new Random();
    private static final String LOG_TAG = "AnagramDict";
    private ArrayList<String> wordList = new ArrayList<>();
    private HashSet<String> wordSet = new HashSet<>();
    private HashMap<String, ArrayList<String>> lettersToWord = new HashMap<String, ArrayList<String>>();
    private HashMap<Integer, ArrayList<String>> sizeToWords = new HashMap<>();
    private int wordLength = DEFAULT_WORD_LENGTH;

    public AnagramDictionary(InputStream wordListStream) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(wordListStream));
        String line;
        while((line = in.readLine()) != null) {
            String word = line.trim();
            wordList.add(word);
            wordSet.add(word);
            if (lettersToWord.containsKey(sortLetters(word))) {
                lettersToWord.get(sortLetters(word)).add(word);
            } else {
                lettersToWord.put(sortLetters(word), new ArrayList<String>(Arrays.asList(word)));
            }
            // store in length hashmap
            if (sizeToWords.containsKey(word.length())) {
                sizeToWords.get(word.length()).add(word);
            } else {
                sizeToWords.put(word.length(), new ArrayList<String>(Arrays.asList(word)));
            }
        }
    }

    public boolean isGoodWord(String word, String base) {
        if (!wordSet.contains(word)){
            return false;
        }
        if (word.contains(base)){
            return false;
        }
        return true;
    }

    public ArrayList<String> getAnagrams(String targetWord) {
        ArrayList<String> result = new ArrayList<String>();
        targetWord = this.sortLetters(targetWord);
        for (String s: this.wordList){
            if (targetWord.equals(this.sortLetters(s))){
                result.add(s);
                Log.d(LOG_TAG, s);
            }
        }
        return result;
    }

    public ArrayList<String> getAnagramsWithOneMoreLetter(String word) {
        ArrayList<String> result = new ArrayList<String>();
        String key;
        for (char c = 'a'; c <= 'z'; c++){
            key = sortLetters(word + c);
            if (lettersToWord.containsKey(key)){
                Log.d(LOG_TAG, key);
                result.addAll(lettersToWord.get(key));
            }
        }
        // delete bad words
        for (int i = 0; i < result.size(); i++){
            if (!isGoodWord(result.get(i), word)){
                result.remove(i);
                i--;
            }
        }
        return result;
    }

    public String pickGoodStarterWord() {
        ArrayList<String> wordArray = sizeToWords.get(Math.min(MAX_WORD_LENGTH, wordLength));

        int maxLen = wordArray.size();
        int sp = random.nextInt(maxLen), loopIndex;
        String key;
        for (loopIndex=sp; loopIndex < (maxLen+sp+1); loopIndex++){
            key = wordArray.get(loopIndex % maxLen);
            if (getAnagramsWithOneMoreLetter(key).size() >= MIN_NUM_ANAGRAMS){
                break;
            }
        }

        wordLength += 1;
        return wordArray.get(loopIndex % maxLen);
    }

    /*
    Helper functions
    */
    public String sortLetters(String s){
        char[] chars = s.toCharArray();
        Arrays.sort(chars);
        return new String(chars);
    }
}