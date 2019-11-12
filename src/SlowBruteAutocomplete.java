import java.util.*;

//init time: 0.01287	for BruteAutocomplete
//init time: 0.01070	for BinarySearchAutocomplete
//init time: 0.1903	for HashListAutocomplete
//init time: 0.006765	for SlowBruteAutocomplete
//search	size	#match	BruteAutoc	BinarySear	HashListAu	SlowBruteA
//	17576	50	0.00705771	0.01068411	0.00002345	0.05987540
//	17576	50	0.00099156	0.00505939	0.00001002	0.03422508
//a	676	50	0.00073146	0.00032064	0.00000754	0.00249846
//a	676	50	0.00066468	0.00029744	0.00000721	0.00274878
//b	676	50	0.00061655	0.00020847	0.00000540	0.00189231
//c	676	50	0.00061381	0.00029235	0.00001044	0.00110017
//g	676	50	0.00063199	0.00030669	0.00000681	0.00100143
//ga	26	50	0.00103156	0.00007644	0.00000705	0.00046765
//go	26	50	0.00052359	0.00009651	0.00000757	0.00115403
//gu	26	50	0.00049214	0.00006756	0.00000749	0.00043659
//x	676	50	0.00045119	0.00018853	0.00000571	0.00060243
//y	676	50	0.00139035	0.00046579	0.00001644	0.00099452
//z	676	50	0.00060700	0.00031757	0.00000770	0.00116686
//aa	26	50	0.00049070	0.00007339	0.00001123	0.00048965
//az	26	50	0.00054089	0.00007292	0.00000842	0.00050354
//za	26	50	0.00139107	0.00006364	0.00000729	0.00065689
//zz	26	50	0.00036088	0.00004354	0.00000556	0.00027512
//zqzqwwx	0	50	0.00046886	0.00003942	0.00000312	0.00036800
//size in bytes=246064	 for BruteAutocomplete
//size in bytes=246064	 for BinarySearchAutocomplete
//size in bytes=1092468	 for HashListAutocomplete
//size in bytes=246064	 for SlowBruteAutocomplete
//
//Process finished with exit code 0
//
//
//
//
// Above is the result of running threeletterwords.txt with all four autocomplete methods.
// This class is similar in efficiency with BruteAutocomplete but takes much more time than
//HashListAutocomplete and BinarySearch.



public class SlowBruteAutocomplete extends BruteAutocomplete {
    /**
     * Create immutable instance with terms constructed from parameter
     *
     * @param terms   words such that terms[k] is part of a word pair 0 <= k < terms.length
     * @param weights weights such that weights[k] corresponds to terms[k]
     * @throws NullPointerException     if either parameter is null
     * @throws IllegalArgumentException if terms.length != weights.length
     * @throws IllegalArgumentException if any elements of weights is negative
     * @throws IllegalArgumentException if any elements of terms is duplicate
     */
    public SlowBruteAutocomplete(String[] terms, double[] weights) {
        super(terms, weights);
    }

    @Override
    public List<Term> topMatches(String prefix, int k) {
        List<Term>list= new ArrayList<>();
        if (k < 0) {
            throw new IllegalArgumentException("Illegal value of k:"+k);
        }
        for (Term t : myTerms) {
            if (t.getWord().startsWith(prefix)){
                list.add(t);
            }
        }
        if(list!=null) {
            Collections.sort(list, Comparator.comparing(Term::getWeight));
            Collections.reverse(list);
            List<Term> all = list.subList(0, Math.min(k, list.size()));
            return all;
        }
        else{
            return new ArrayList<>();
        }
    }

}
