package jackson.rick;

import jackson.rick.MyMap.Pair;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by rickjackson on 2/22/17.
 */
public class MyMapTest {
    private MyMap<Integer, String> myMap;
    private Pair<Integer, String> pair;
    
    
    @Before
    public void setup() {
        myMap = new MyMap<>();
    }
    
    @Test
    public void sizeTest() {
        assertEquals(myMap.size(), 0);
        assertNotEquals(myMap.size(), 0);
    }
    
    @Test
    public void isEmptyTest() {
        assertEquals(myMap.isEmpty(), false);
    }
    
    @Test
    public void containsKeyTest() {
        assertEquals(myMap.containsKey(null), false);
    }
    
    @Test
    public void containsValueTest() {
        assertEquals(myMap.containsValue(null), false);
    }
    
    @Test
    public void getTest() {
        assertEquals(myMap.get(null), null);
    }
    
    @Test
    public void putTest() {
        assertEquals(myMap.put(0, null), null);
    }
    
    @Test
    public void removeTest() {
        assertEquals(myMap.remove(null), null);
    }
    
    @Test
    public void putAllTest() {
        
    }
    
    @Test
    public void clearTest() {
        assertEquals(myMap.isEmpty(), false);
        myMap.clear();
        assertEquals(myMap.isEmpty(), true);
    }
    
    @Test
    public void keySet() {
        assertEquals(myMap.keySet(), null);
    }
    
    @Test
    public void valuesTest() {
        assertEquals(myMap.values(), null);
    }
    
    @Test
    public void pairSetTest() {
        assertEquals(myMap.pairSet(), null);
    }
    
    @Test
    public void equalsTest() {
        // assertEquals(myMap.equals(0), true);
    }
    
    @Test
    public void replaceAllTest() {
        
    }
    
    @Test
    public void putIfAbsentTest() {
        
    }
    
    @Test
    public void replaceTest() {
        
    }
}
