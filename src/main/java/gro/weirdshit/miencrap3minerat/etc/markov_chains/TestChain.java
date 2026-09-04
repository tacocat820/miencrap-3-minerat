package gro.weirdshit.miencrap3minerat.etc.markov_chains;

import gro.weirdshit.miencrap3minerat.MIENCRAP3MINERAT;

public class TestChain {
    public static MarkovVocab vocab = new MarkovVocab();
    public static MarkovChain chain = new MarkovChain(2);

    public static void generate() {
        MIENCRAP3MINERAT.LOGGER.info("hello markov");
        MIENCRAP3MINERAT.LOGGER.info(MarkovTokenizer.encode("sesbian lex", vocab).toString());
        MIENCRAP3MINERAT.LOGGER.info(MarkovTokenizer.decode(MarkovTokenizer.encode("sesbian lex", vocab), vocab));
        chain.train(MarkovTokenizer.encode("we shall now play MIENCRAP 3 MINERAT", vocab));

        var sesbian = MarkovTokenizer.encodeNoEnd("shall now", vocab);
        int size = sesbian.size();
        while (sesbian.getLast() != 0 && sesbian.size() < size + 50) sesbian.add(chain.predictNext(sesbian, vocab));

        MIENCRAP3MINERAT.LOGGER.info(MarkovTokenizer.decode(sesbian, vocab));
    }
}
