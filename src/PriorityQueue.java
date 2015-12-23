import java.util.Arrays;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: sthirumuru
 * Date: 18/12/15
 * Time: 10:50 AM
 * To change this template use File | Settings | File Templates.
 */
public class PriorityQueue<T> {

    private int size;
    private T[] elements;
    private int lastIndex = -1;
    public PriorityQueue(int size)
    {
        this.size = size;
        elements = (T[])new Object[this.size];
    }

    public PriorityQueue(List<T> list, int size)
    {
        this.size = size;
        elements = (T[])new Object[this.size];
        for(int i=0; i<list.size(); i++)
        {
            elements[i]= list.get(i);
        }
        lastIndex = list.size() - 1;
        heapify();
    }

    public void add(T element)
    {
        if(lastIndex ==  size - 1)
        {
            throw new IndexOutOfBoundsException();
        }

        elements[lastIndex + 1] = element;
        lastIndex ++;
        siftUp(lastIndex);
    }

    public T getMin()
    {
        if(lastIndex < 0) return null;
        return elements[0];
    }

    public T extractMin()
    {
        if(lastIndex < 0) return null;
        T min = elements[0];
        if(lastIndex > 0)
        {
            elements[0] = elements[lastIndex];
        }
        elements[lastIndex] = null;
        lastIndex --;
        siftDown(0);
        return min;
    }


    private void siftUp(int childIndex)
    {
        int curChildIndex = childIndex;
        int parentIndex = (childIndex - 1)/ 2;

        while(parentIndex >= 0 &&
                ((Comparable)elements[curChildIndex]).compareTo(elements[parentIndex]) < 0)
        {
            T temp = elements[parentIndex];
            elements[parentIndex] = elements[curChildIndex];
            elements[curChildIndex] = temp;
            curChildIndex = parentIndex;
            parentIndex = (curChildIndex - 1)/2;
        }
    }

    private void siftDown(int parentIndex)
    {
        int curParentIndex = parentIndex;
        int leftChildIndex = 2*curParentIndex + 1;
        int rightChildIndex =  2*curParentIndex + 2;
        while(true)
        {
            int minChildIndex = leftChildIndex;
            if(rightChildIndex <= lastIndex)
            {
                minChildIndex = ((Comparable)elements[leftChildIndex]).compareTo(elements[rightChildIndex]) > 0 ? rightChildIndex : leftChildIndex;
            }

            if(minChildIndex <= lastIndex && ((Comparable)elements[minChildIndex]).compareTo(elements[curParentIndex]) < 0)
            {
                T temp = elements[parentIndex];
                elements[curParentIndex] = elements[minChildIndex];
                elements[minChildIndex] = temp;
                curParentIndex = minChildIndex;
                leftChildIndex = 2*curParentIndex + 1;
                rightChildIndex = 2*curParentIndex + 2;
            }
            else
            {
                break;
            }
        }
    }

    public void heapify()
    {
        for(int i=((lastIndex + 1)/2); i >= 0; i--)
        {
            siftDown(i);
        }
    }

    public int getSize()
    {
        return lastIndex + 1;
    }

    public boolean isEmpty()
    {
        return lastIndex < 0;
    }

    public String toString()
    {
        StringBuilder retValue = new StringBuilder();
        for(int i = 0; i <= lastIndex; i++)
        {
            retValue.append(elements[i]).append(" ");
        }
        return retValue.toString();
    }


    public static void main(String[] args) {
        PriorityQueue<Integer> pq = new PriorityQueue<Integer>(10);
        pq.add(5);
        System.out.println(pq);
        pq.add(2);
        System.out.println(pq);
        pq.add(10);
        System.out.println(pq);
        pq.add(3);
        System.out.println(pq);

        while (!pq.isEmpty())
        {
            System.out.println(pq.extractMin());
        }

        pq = new PriorityQueue<Integer>(Arrays.asList(10, 4, 8, 7, 1, 99, 87), 10);
        System.out.println(pq);

    }
}
