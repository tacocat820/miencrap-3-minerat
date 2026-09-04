package gro.weirdshit.miencrap3minerat.etc.markov_chains;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class MarkovVocab implements Serializable {
    public HashMap<String, Integer> to_id;
    public ArrayList<String> to_string;

    public MarkovVocab() {
        this.to_id = new HashMap<>();
        this.to_string = new ArrayList<>();
    }

    public int size() {
        return this.to_string.size();
    }
    public int getOrAdd(String token) {
        if (to_id.containsKey(token)) 
            return to_id.get(token);
        else {
            to_string.add(token);
            to_id.put(token, to_string.size() - 1);
            return to_string.size() - 1;
        }
    }
}
