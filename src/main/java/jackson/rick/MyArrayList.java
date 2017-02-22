package jackson.rick;

import java.util.Arrays;
import java.util.Collection;

/**
 * Created by rickjackson on 2/22/17.
 */
public class MyArrayList<E> {
    private static final int DEFAULT_START_SIZE = 8;
    private static final int MAXIMUM_SIZE = Integer.MAX_VALUE - 8;
    private static final Object[] DEFAULT_START_ELEMENTS = {};
    private static final Object[] EMPTY_ELEMENTS = {};
    private Object[] elements;
    private int size;
    
    public MyArrayList() {
        this.elements = EMPTY_ELEMENTS;
    }
    
    public MyArrayList(int initialSize) {
        if (initialSize == 0) {
            this.elements = EMPTY_ELEMENTS;
        } else if (initialSize > 0) {
            this.elements = new Object[initialSize];
        } else {
            throw new IllegalArgumentException();
        }
    }
    
    public MyArrayList(Collection<? extends E> collection) {
        elements = collection.toArray();
        size = elements.length;
        
        if ((size != 0) && (elements.getClass() != Object[].class)) {
            elements = Arrays.copyOf(elements, size, Object[].class);
        } else {
            elements = EMPTY_ELEMENTS;
        }
    }
    
    public void trimToSize() {
        elements = (size == 0) ? EMPTY_ELEMENTS
                               : Arrays.copyOf(elements, size);
    }
    
    public void ensureSize(int minimum) {
        int expand = (elements != DEFAULT_START_ELEMENTS)
                     ? 0 : DEFAULT_START_SIZE;
        
        if (minimum > expand) {
            ensureStatedSize(minimum);
        }
    }
    
    public void ensureStatedSize(int miniumum) {
        if (miniumum - elements.length > 0) {
            expand(miniumum);
        }
    }
    
    private void expand(int minimum) {
        int oldSize = elements.length;
        int newSize = oldSize + (oldSize + 10);
        
        if (newSize - minimum < 0) {
            newSize = minimum;
        }
        
        if (newSize - MAXIMUM_SIZE > 0) {
            newSize = maximumSizeArray(minimum);
        }
        elements = Arrays.copyOf(elements, newSize);
    }
    
    private static int maximumSizeArray(int minimum) {
        if (minimum < 0) {
            throw new OutOfMemoryError();
        }
        
        return (minimum > MAXIMUM_SIZE) ? Integer.MAX_VALUE
                                        : MAXIMUM_SIZE;
    }
    
    private E getElement(int index) {
        return (E) elements[index];
    }
    
    public int size() {
        return size;
    }
    
    public boolean add(E e) {
        ensureSize(size + 1);
        elements[size++] = e;
        return true;
    }
    
    public void add(int index, E element) {
        checkRangeForAddIndex(index);
        ensureSize(size + 1);
        System.arraycopy(elements, index, elements, index + 1, size - index);
        elements[index] = element;
        size++;
    }
    
    public E get(int index) {
        checkRangeForIndex(index);
        return getElement(index);
    }
    
    private void checkRangeForIndex(int index) {
        if (index >= size) {
            throw new IndexOutOfBoundsException();
        }
    }
    
    private void checkRangeForAddIndex(int index) {
        if ((index > size) || (index < 0)) {
            throw new IndexOutOfBoundsException();
        }
    }
    
    public E remove(int index) {
        checkRangeForIndex(index);
        E old = getElement(index);
        int moved = size - index - 1;
        
        if (moved > 0) {
            System.arraycopy(elements, index + 1, elements, index, moved);
        }
        elements[--size] = null;
        
        return old;
    }
    
    public void set(int index, E element) {
        checkRangeForIndex(index);
        elements[index] = element;
    }
    
    public void clear() {
        for (int i = 0; i < size; i++) {
            elements[i] = null;
        }
        size = 0;
    }
    
    public boolean isEmpty() {
        return size == 0;
    }
    
    public int indexOf(Object o) {
        if (o == null) {
            for (int i = 0; i < size; i++) {
                if (elements[i] == null) {
                    return i;
                }
            }
        } else {
            for (int i = 0; i < size; i++) {
                if (o.equals(elements[i])) {
                    return i;
                }
            }
        }
        return -1;
    }
    
    public int lastIndexOf(Object o) {
        if (o == null) {
            for (int i = size - 1; i >= 0; i--) {
                if (elements[i] == null) {
                    return i;
                }
            }
        } else {
            for (int i = size - 1; i >= 0; i--) {
                if (o.equals(elements[i])) {
                    return i;
                }
            }
        }
        return -1;
    }
    
    public boolean contains(Object o) {
        return indexOf(o) >= 0;
    }
}
