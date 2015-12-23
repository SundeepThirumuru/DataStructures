/**
 * Created by IntelliJ IDEA.
 * User: sthirumuru
 * Date: 15/12/15
 * Time: 6:54 PM
 * To change this template use File | Settings | File Templates.
 */
public class LinkedListReversal {


    public static Node reverseList(Node head) {
        if (head == null || head.next == null) return head;
        Node nextNode = head.next;
        head.next = null;
        while (nextNode != null) {
            Node tempNode = nextNode.next;
            nextNode.next = head;
            head = nextNode;
            nextNode = tempNode;
        }
        return head;
    }

    public static Node reverseList2(Node head)
    {
        Node reverseHead = null;
        while(head != null)
        {
            Node temp = head.next;
            head.next = reverseHead;
            reverseHead = head;
            head = temp;
        }
        return reverseHead;
    }


    public static void main(String[] args) {
        Node head = new Node();
        Node node = head;
        Node prevNode = null;
        for (int i = 1; i < 10; i++) {
            node.value = i;
            node.next = null;
            if (prevNode != null) {
                prevNode.next = node;
            }
            prevNode = node;
            node = new Node();
        }
        printList(head);
//        printList(reverseList(head));
        printList(reverseList2(head));


    }

    public static void printList(Node node) {
        System.out.println("Printing list");
        while (node != null) {
            System.out.println(node.value);
            node = node.next;
        }
    }
}
