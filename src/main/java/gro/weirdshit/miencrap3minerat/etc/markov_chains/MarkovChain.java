package gro.weirdshit.miencrap3minerat.etc.markov_chains;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;

import gro.weirdshit.miencrap3minerat.MIENCRAP3MINERAT;

class TokenAndCount implements Serializable {
    int token, count;

    public TokenAndCount(int token, int count) {
        this.token = token;
        this.count = count;
    }
}

class DoubleMap implements Serializable {
    HashMap<Integer, Integer> lookup;
    TreeMap<Integer, Integer> inverse;

    public DoubleMap() {
        this.lookup = new HashMap<>();
        this.inverse = new TreeMap<>();
    }

    void put(int key, int val) {
        lookup.put(key, val);
        inverse.put(val, key);
    }

    int findValForKey(int key) {
        return lookup.get(key);
    }

    int findKeyForVal(int val) {
        return inverse.get(val);
    }

    boolean containsKey(int key) {
        return lookup.containsKey(key);
    }

    boolean containsVal(int val) {
        return inverse.containsKey(val);
    }

    void remove(int key, int val) {
        lookup.remove(key);
        inverse.remove(val);
    }

    void remove(int key) {
        inverse.remove(lookup.get(key));
        lookup.remove(key);
    }

    void removeByVal(int val) {
        lookup.remove(inverse.get(val));
        inverse.remove(val);
    }

    TokenAndCount getFirstInInverse() {
        for (Map.Entry<Integer,Integer> i : inverse.entrySet()) 
            return new TokenAndCount(i.getValue(), i.getKey());
        return null;
    }
}

public class MarkovChain implements Serializable {
    private int order = 5; 
    int start; int end;
    HashMap<ArrayList<Integer>, DoubleMap> chain = new HashMap<>();
    // KEY = token
    // VAL = count

    public MarkovChain() {
        
    }
    public MarkovChain(int order) {
        this.order = order;
    }

    public void put(ArrayList<Integer> tokens, int next) {
        if (chain.containsKey(tokens)) {
            DoubleMap m = chain.get(tokens);

            if (m.containsKey(next)) {
                int cnt = m.findValForKey(next);
                m.remove(next);    
                m.put(next, cnt + 1);
            } else {
                m.put(next, 1);
            }
        } else {
            DoubleMap put = new DoubleMap();
            put.put(next, 1);
            chain.put(tokens, put);
        }
    }

    public void train(ArrayList<Integer> tokens) {
        if (tokens.size() < order) return;

        for (int i = -order; i < tokens.size() - order; ++i) {
            ArrayList<Integer> key = new ArrayList<>(tokens.subList(Math.max(0, i), i + order));
            Integer next = tokens.get(i + order);

            for (int j = 0; j < key.size(); ++j) {
                MIENCRAP3MINERAT.LOGGER.info(key.subList(j, key.size()) + " -> " + next.toString());
                put(new ArrayList<>(key.subList(j, key.size())), next);
            }
        }
    }

    public Integer predictNext(ArrayList<Integer> tokens, MarkovVocab vocab) {

        for (int i = tokens.size() - order; i < tokens.size(); ++i) {
            if (i < 0) continue;
            ArrayList<Integer> sub = new ArrayList<>(tokens.subList(i, tokens.size()));

            if (chain.containsKey(sub)) {
                TokenAndCount first = chain.get(sub).getFirstInInverse();
                if (first != null) return first.token;
            }
        }

        // TODO: shit out a random token instead
            MIENCRAP3MINERAT.LOGGER.info("error404 shit");

        Random rand = new Random();
        return rand.nextInt(0, vocab.size());
    }
}
