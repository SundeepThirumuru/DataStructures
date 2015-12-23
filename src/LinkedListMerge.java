import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by IntelliJ IDEA.
 * User: sthirumuru
 * Date: 16/12/15
 * Time: 10:29 AM
 * To change this template use File | Settings | File Templates.
 */
public class LinkedListMerge {


    public static int getSize(Node head)
    {
        int count = 0;
        Node temp = head;
        while(temp != null)
        {
            count ++;
            temp = temp.next;
        }
        return count;
    }

    public static Node getNode(Node head, int index)
    {
        int i = 0;
        Node curNode = head;
        while (curNode != null && i < index)
        {
            curNode = curNode.next;
            i ++;
        }
        if(i == index)
        {
            return curNode;
        }

        throw new IndexOutOfBoundsException(Integer.toString(index));
    }

    public static Node getNodeFromLast(Node head, int indexFromLast)
    {
        int size = getSize(head);
        if(indexFromLast >= size)
        {
            throw new IndexOutOfBoundsException(Integer.toString(indexFromLast));
        }
        return getNode(head, size - indexFromLast - 1);
    }

    public static boolean detectLoop(Node head)
    {
        Node slowPointer = head;
        Node fastPointer = head;
        while(fastPointer != null)
        {
            slowPointer = slowPointer.next;
            fastPointer = fastPointer.next;
            if(fastPointer != null)
            {
                fastPointer = fastPointer.next;
            }
            else
            {
                break;
            }
            if(slowPointer == fastPointer)
            {
                return true;
            }
        }
        return false;
    }

    public static boolean detectLoop2(Node head)
    {
        Set<Node> visitedNodes = new LinkedHashSet<Node>();
        Node curNode = head;
        while(curNode != null)
        {
            if(visitedNodes.contains(curNode))
            {
                return true;
            }
            visitedNodes.add(curNode);
            curNode = curNode.next;
        }
        return false;
    }

    public static Node merge(Node head1, Node head2)
    {
        if(head2 == null)
        {
            return head1;
        }

        if(head1 == null)
        {
            return head2;
        }

        Node head3 = null;
        Node curNode1 = head1;
        Node curNode2 = head2;

        if(((Integer)curNode1.value).compareTo((Integer)curNode2.value) > 0)
        {
            head3 = curNode2;
            curNode2 = curNode2.next;
        }
        else
        {
            head3 = curNode1;
            curNode1 = curNode1.next;
        }
        Node curNode3 = head3;

        while(curNode1 != null && curNode2 != null)
        {
            if(((Integer)curNode1.value).compareTo((Integer)curNode2.value) > 0)
            {
                curNode3.next = curNode2;
                curNode2 = curNode2.next;
            }
            else
            {
                curNode3.next = curNode1;
                curNode1 = curNode1.next;
            }
            curNode3 = curNode3.next;
        }

        if(curNode1 != null)
        {
            curNode3.next = curNode1;
        }
        else
        {
            curNode3.next = curNode2;
        }
        return head3;
    }

    public static void main(String[] args) {
        Node head = new Node();
        Node prevNode = null;
        Node curNode = head;
        for(int i = 1; i <= 10; i++)
        {
            curNode.value = i;
            curNode.next = new Node();
            prevNode = curNode;
            curNode = curNode.next;
        }

        if(prevNode != null)
        {
            prevNode.next = null;
        }

        System.out.println(getSize(head));
        System.out.println(getNode(head, 4));
        System.out.println(getNodeFromLast(head, 5));

        head = new Node();
        curNode = head;
        Node loopNode = null;
        prevNode = null;
        for(int i = 1; i <= 10; i++)
        {
            curNode.value = i;
            curNode.next = new Node();
            if(i == 4)
            {
                loopNode = curNode;
            }
            prevNode = curNode;
            curNode = curNode.next;
        }
        prevNode.next = loopNode;

        System.out.println(detectLoop(head));
        System.out.println(detectLoop2(head));

        head = new Node();
        curNode = head;

        prevNode = null;
        List<Integer> list = Arrays.asList(3, 8, 15, 19, 28);
        for(int i = 0; i < list.size(); i++)
        {
            curNode.value = list.get(i);
            curNode.next = new Node();
            prevNode = curNode;
            curNode = curNode.next;
        }
        prevNode.next = null;

        Node head1 = null;
        head = new Node();
        curNode = head;

        prevNode = null;
        list = Arrays.asList(2, 5, 21, 24, 32);
        for(int i = 0; i < list.size(); i++)
        {
            curNode.value = list.get(i);
            curNode.next = new Node();
            prevNode = curNode;
            curNode = curNode.next;
        }
        prevNode.next = null;
        Node head2 = head;
        LinkedListReversal.printList(head1);
        LinkedListReversal.printList(head2);
        LinkedListReversal.printList(merge(head1, head2));
    }


}
