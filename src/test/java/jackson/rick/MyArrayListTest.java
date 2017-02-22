package jackson.rick;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by rickjackson on 2/22/17.
 */
public class MyArrayListTest {
    private MyArrayList<Object> list;
    
    @Before
    public void setup() {
        list = new MyArrayList<>();
        list.add(1);
        list.add('c');
        list.add("String");
        list.add(true);
    }
    
    @Test
    public void getElementTest() {
        assertEquals(list.get(0), 1);
        assertEquals(list.get(1), 'c');
        assertEquals(list.get(2), "String");
        assertEquals(list.get(3), true);
    }
    
    @Test
    public void sizeTest() {
        assertEquals(list.size(), 4);
    }
    
    @Test
    public void addTest() {
        assertTrue(list.add(88));
        list.add(4, "Other");
        assertEquals(list.get(4), "Other");
    }
    
    @Test
    public void getTest() {
        assertEquals(list.get(0), 1);
        assertEquals(list.get(1), 'c');
    }
    
    @Test
    public void removeTest() {
        assertEquals(list.remove(0), 1);
        assertNotEquals(list.size(), 4);
        assertNotEquals(list.get(0), 1);
    }
    
    @Test
    public void setTest() {
        list.set(0, 88);
        assertNotEquals(list.get(0), 1);
        assertEquals(list.get(0), 88);
        assertEquals(list.get(1), 'c');
    }
    
    @Test
    public void clearTest() {
        assertEquals(list.size(), 4);
        list.clear();
        assertNotEquals(list.size(), 4);
        assertEquals(list.size(), 0);
    }
    
    @Test
    public void isEmptyTest() {
        assertFalse(list.isEmpty());
        list.clear();
        assertTrue(list.isEmpty());
    }
    
    @Test
    public void indexOfTest() {
        assertEquals(list.indexOf(1), 0);
        assertEquals(list.indexOf(88), -1);
        list.add(0, 88);
        assertEquals(list.indexOf(88), 0);
    }
    
    @Test
    public void lastIndexOfTest() {
        assertEquals(list.lastIndexOf(1), 0);
        list.add(1);
        assertNotEquals(list.lastIndexOf(1), 0);
        assertEquals(list.lastIndexOf(1), 4);
    }
}
