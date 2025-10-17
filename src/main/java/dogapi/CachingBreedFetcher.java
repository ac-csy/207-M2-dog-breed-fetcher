package dogapi;

import java.util.*;

/**
 * This BreedFetcher caches fetch request results to improve performance and
 * lessen the load on the underlying data source. An implementation of BreedFetcher
 * must be provided. The number of calls to the underlying fetcher are recorded.
 *
 * If a call to getSubBreeds produces a BreedNotFoundException, then it is NOT cached
 * in this implementation. The provided tests check for this behaviour.
 *
 * The cache maps the name of a breed to its list of sub breed names.
 */
public class CachingBreedFetcher implements BreedFetcher {
    // TODO Task 2: Complete this class
    private int callsMade = 0;
    private final BreedFetcher fetcher;
    private Map<String, List<String>> breedFetcherCache;
    public CachingBreedFetcher(BreedFetcher fetcher) {
        this.fetcher = fetcher;
        this.breedFetcherCache = new HashMap<String, List<String>>();
    }

    @Override
    public List<String> getSubBreeds(String breed) throws BreedNotFoundException {
        // return statement included so that the starter code can compile and run.
        //System.out.println(cache);
       if (breedFetcherCache.containsKey(breed)) {
           return breedFetcherCache.get(breed);
        }
       else {
           try {
               List<String> subBreeds = this.fetcher.getSubBreeds(breed);
               breedFetcherCache.put(breed, subBreeds);
               // System.out.println(cache);
               // System.out.println("help");
               callsMade++;
               return subBreeds;
           } catch (BreedNotFoundException event) {
               callsMade++;
               throw new BreedNotFoundException("Error fetching sub-breeds from the API: " + event.getMessage());
           }
       }
    }

    public int getCallsMade() {
        return callsMade;
    }
}