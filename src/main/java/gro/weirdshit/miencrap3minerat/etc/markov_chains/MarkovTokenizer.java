package gro.weirdshit.miencrap3minerat.etc.markov_chains;

import java.io.Serializable;
import java.util.ArrayList;

public class MarkovTokenizer implements Serializable {
        
    static char[] separators = {
        '.', ',', '!', '?', ';', ':', '(', ')', '[', ']', '{', '}', '"', '-', '—', '–', '…', '«',
        '»', '/', '\\', '*', '#', '@', '%', '&', '+', '=', '<', '>', '|', '~', '^', '_', '$',
    };
    static String startToken = "<begin>";
    static String endToken = "<end>";
    static String unkToken = "�";

    public static ArrayList<String> splitWord(String word) {
        ArrayList<String> result = new ArrayList<>();
        StringBuilder current = new StringBuilder();

        for (int i = 0; i < word.length(); ++i) {

            char chr = word.charAt(i);
            if (chr == ' ') {
                result.add(current.toString().toLowerCase());
                current = new StringBuilder();
                continue;
            }

            boolean sep = false;
            for(int j : separators) if (j == chr) sep = true;

            if (sep) {
                result.add(current.toString());
                result.add(String.valueOf(chr));
                current = new StringBuilder();
            } else 
                current.append(chr);
        }
        result.add(current.toString().toLowerCase());

        return result;
    }

    public static ArrayList<Integer> encode(String txt, MarkovVocab vocab) {
        //int start = vocab.getOrAdd(startToken);
        int end = vocab.getOrAdd(endToken);

        ArrayList<Integer> result = encodeNoEnd(txt, vocab);
        result.add(end);

        return result;
    }    

    public static ArrayList<Integer> encodeNoEnd(String txt, MarkovVocab vocab) {
        //int start = vocab.getOrAdd(startToken);
        
        ArrayList<Integer> result = new ArrayList<>();

        for (String i : txt.split(" ")) {
            for (String piece : splitWord(i)) {
                if (piece.trim().isEmpty()) continue;
                int id = vocab.getOrAdd(piece);

                result.add(id);
            }
        }


        return result;
    }    

    public static String decode(ArrayList<Integer> info, MarkovVocab vocab) {

        StringBuilder res = new StringBuilder();

        for (Integer i : info) {
            res.append(vocab.to_string.get(i));
            res.append(' ');
        }

        return res.toString();
    }

}
