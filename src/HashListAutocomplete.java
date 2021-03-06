import java.util.*;

public class HashListAutocomplete implements Autocompletor{
    private static final int MAX_PREFIX = 10;
    private Map<String, List<Term>> myMap = new HashMap<>();
    private int mySize;


    /**
     *
     * @param prefix
     * @param k
     * @return ret
     * Takes in a string of prefix and returns a list of size k, which all start with
     * prefix and has the highest weights. Since all possible prefixes are already
     * stored in the map it is easy to access them
     */
    @Override
    public List<Term> topMatches(String prefix, int k) {
        List<Term> all = myMap.get(prefix);
        if (all != null) {
            List<Term> list = all.subList(0, Math.min(k, all.size()));
            return list;
        }
        else{
            return new ArrayList<>();
        }
    }


    public HashListAutocomplete(String[] terms, double[] weights) {
        if (terms == null || weights == null) {
            throw new NullPointerException("One or more arguments null");
        }

        initialize(terms,weights);
    }

    /**
     *
     * @param terms is array of Strings for words in each Term
     * @param weights is corresponding weight for word in terms
     * For each possible integer starting from 0 to the smaller of MAX_PREFIX and the
     * string size, a key (prefix) is created. The method goes through each string and
     * add them to the values of the map
     */
    @Override
    public void initialize(String[] terms, double[] weights) {

        for(int i = 0; i<terms.length; i++){
            String t = terms[i];
            if(t!=null) {
                String key;
                int ke;
                if(MAX_PREFIX<t.length()){
                    ke = MAX_PREFIX+1;
                }
                else{
                    ke = t.length();
                }
                for (int k = 0; k <= ke; k++) {
                    key=t.substring(0, k);
                    List<Term> temp = new ArrayList<>();
                    if (weights[i] >= 0) {
                         if(myMap.containsKey(key)) {
                            temp = myMap.get(key);
                            temp.add(new Term(t, weights[i]));
                            myMap.put(key, temp);
                        }
                        else{
                            temp.add(new Term(t, weights[i]));
                            myMap.put(key, temp);
                        }
                    }
                    else {
                        throw new IllegalArgumentException();
                    }
                }
             }
            else{
                throw new NullPointerException();
             }
        }
        for(String key:myMap.keySet()){
            Collections.sort(myMap.get(key),
                    Comparator.comparing(Term::getWeight).reversed());
        }
    }

    /**
     *
     * @return #byte takes for storing everything in the map. Key has characters only;
     * values have characters and doubles
     */
    @Override
    public int sizeInBytes() {
        if (mySize == 0) {
            for(String key:myMap.keySet()){
                for (Term t : myMap.get(key)) {
                    mySize += BYTES_PER_DOUBLE +
                            BYTES_PER_CHAR * t.getWord().length();
                }
                mySize+= BYTES_PER_CHAR * key.length();
            }
        }
        return mySize;
    }
}
