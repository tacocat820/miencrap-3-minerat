package gro.weirdshit.miencrap3minerat.etc.markov_chains;

import java.io.Serializable;
import java.util.ArrayList;

public class MarkovModel implements Serializable {
    public MarkovVocab vocab = new MarkovVocab();
    public MarkovChain chain;

    public MarkovModel(int order) {
        this.chain = new MarkovChain(order);
    }

    public int predictNext(ArrayList<Integer> list) {
        return this.chain.predictNext(list, this.vocab);
    }

    public  ArrayList<Integer> encode(String string) {
        return  MarkovTokenizer.encode(string, this.vocab);
    }
    public  ArrayList<Integer> encodeNoEnd(String string) {
        return  MarkovTokenizer.encodeNoEnd(string, this.vocab);
    }
    public String decode(ArrayList<Integer> list) {
        return MarkovTokenizer.decode(list, this.vocab);
    }
    public void train(ArrayList<Integer> tokens) {
        this.chain.train(tokens);
    }
}
