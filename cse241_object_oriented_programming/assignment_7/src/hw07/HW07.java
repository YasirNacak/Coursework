package hw07;
import javafx.util.Pair;

/**
 * Driver class for set and map classes.
 * @author Yasir
 */

public class HW07 {

	public static void main(String[] args) {
		GTUSet<Integer> mySet = new GTUSet<Integer>(Integer[].class);
		
		mySet.insert(34);
		mySet.insert(35);
		mySet.insert(36);
		mySet.insert(37);
		mySet.insert(38);
		mySet.insert(39);
		mySet.insert(40);
		mySet.insert(41);
		mySet.erase(34);
		mySet.erase(41);
		mySet.erase(37);
		mySet.erase(38);
		
		System.out.printf("first set:\n");
                System.out.println(mySet.toString());
                
		System.out.printf("count of 13: %d and count of 36: %d\n", mySet.count(13), mySet.count(36));
                
		System.out.printf("starting from the position where we find 36:\n");
		GTUIterator<Integer> findIter = mySet.find(36);
		while(findIter.hasNext()) {
			Integer val = findIter.next();
			System.out.printf("%d ", val);
		}
		System.out.printf("\n");
		
		System.out.printf("cleared the set\n");
		mySet.clear();
		
		System.out.printf("trying to insert 47 twice:\n");
		try {
			mySet.insert(47);
			mySet.insert(47);
		} catch (Exception e) {
			System.out.printf("%s\n", e.getMessage());
		}
		
		System.out.printf("first set:\n");
		System.out.println(mySet.toString());
		
		GTUSet<Integer> mySet2 = new GTUSet<Integer>(Integer[].class);
		
		System.out.printf("cleared the set\n");
		mySet.clear();
		mySet.insert(35);
		mySet.insert(36);
		mySet.insert(39);
		mySet.insert(40);
		
		mySet2.insert(34);
		mySet2.insert(35);
		mySet2.insert(37);
		mySet2.insert(38);
		mySet2.insert(39);
		mySet2.insert(40);
		mySet2.insert(41);
		
		System.out.printf("first set:\n");
		System.out.println(mySet.toString());
		System.out.printf("second set:\n");
		System.out.println(mySet2.toString());
		
		GTUSet<Integer> mySetIntersection = (GTUSet<Integer>) mySet.intersection(mySet2);
		
		System.out.printf("intersection of sets:\n");
		System.out.println(mySetIntersection.toString());
		
                GTUMap<String, Integer> myMap = new GTUMap(Pair[].class);
                Pair<String, Integer> p;
		p = new Pair<String, Integer>("one", 1);
                myMap.insert(p);
                p = new Pair<String, Integer>("two", 2);
                myMap.insert(p);
                p = new Pair<String, Integer>("three", 3);
                myMap.insert(p);
                p = new Pair<String, Integer>("four", 4);
		myMap.insert(p);
                
                System.out.printf("first map:\n");
		System.out.println(myMap.toString());
                
                GTUMap<String, Integer> myMap2 = new GTUMap(Pair[].class);
		p = new Pair<String, Integer>("one", 1);
                myMap2.insert(p);
                p = new Pair<String, Integer>("three", 3);
                myMap2.insert(p);
                p = new Pair<String, Integer>("five", 5);
                myMap2.insert(p);
                p = new Pair<String, Integer>("seven", 7);
		myMap2.insert(p);
                
                System.out.printf("second map:\n");
		System.out.println(myMap2);
                
                System.out.printf("erasing using key from second map:\n");
                myMap2.erase("seven");
                
                System.out.printf("printing using 'at' method:\n%s -> %d\n", "three", myMap.at("three"));
                
                System.out.printf("second map:\n");
		System.out.println(myMap2);
                
                GTUMap<String, Integer> myMapIntersection = (GTUMap) myMap.intersection(myMap2);
                
		System.out.printf("intersection of maps:\n");
		System.out.println(myMapIntersection);
	}
}
