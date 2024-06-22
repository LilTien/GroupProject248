import java.sql.SQLOutput;

public class LinkedList
{
    private Node head, tail, current;

    //Default LinkedList Class
    public LinkedList()                   {       head = tail = current = null;   }

    //To check whether is the linked list empty
    public boolean isEmpty()         {      return head == null;    }

    // to print data
    public String print()
    {
        if (isEmpty())
            return "( empty list )";
        else
            return "(" + head + ")";
    }

    //To add new Object data at the front of the LinkedList
    public void insertAtFront(Construction data)
    {
        if(isEmpty())
            head = tail = new Node(data);
        else
            head = new Node(data, head);
    }
    // Return the number of elements in the list
    public int size()
    {
        int count = 0;
        if (isEmpty())
            return count;

        current = head;
        while(current != null)
        {
            ++count;
            current = current.link;
        }
        return count;
    }
    //To add new Object data at the back of linked list
    public void insertAtBack(Construction data)
    {
        if(isEmpty())
            head = tail = new Node(data);
        else
            tail = tail.link = new Node(data);

    }

    public void removeNode(String key) {
        Node temp = head, prev = null;

        // If head node itself holds the key to be deleted
        if (temp != null && temp.data.getProjectID().equalsIgnoreCase(key)) {
            if (temp.link == null) {
                // If it's the only node in the list, we don't set head to null
                temp.data = null; // Or set to a placeholder value
            } else {
                head = temp.link; // Change head
            }
            return;
        }

        // Search for the key to be deleted, keep track of the previous node
        // as we need to change prev.next
        while (temp != null && !temp.data.getProjectID().equalsIgnoreCase(key)) {
            prev = temp;
            temp = temp.link;
        }

        // If key was not present in linked list
        if (temp == null) {
            System.err.println("Product Id doesn't found!!");
            return;
        }

        // If the node to be deleted is the last node
        if (temp.link == null) {
            // Instead of setting prev.next to null, set temp data to null
            // or a placeholder value
            temp.data = null;
        } else {
            // Unlink the node from linked list
            prev.link = temp.link;
        }
    }

    // Return the first element in the linked list
    public Construction getFirst() throws EmptyListException
    {
        if(isEmpty())
            throw new EmptyListException();

        current = head;
        return current.data;
    }

    // Return the next element in the linked list
    public Construction getNext()
    {
        if(current != tail)
        {
            current = current.link;
            return current.data;
        }
        else
            return null;

    }
    //remove any node in linked list


    // Return the last element in the linked list
    public Construction getLast() throws EmptyListException
    {
        if(isEmpty())
            throw new EmptyListException();

        return tail.data;
    }
    // Remove the first element in the linked list
    public Construction removeFromFront() throws EmptyListException
    {
        if(isEmpty())
            throw new EmptyListException();

        Construction d = head.data;

        if(head==tail)
            head=tail=null;
        else
        {
            Node curr = head;
            head = head.link;
            curr.link = null;
        }

        return d;

    }

    // Remove the last element in the linked list
    public Construction removeFromBack() throws EmptyListException
    {
        if(isEmpty())
            throw new EmptyListException();

        Construction d = tail.data;

        if(head==tail)
            head=tail=null;
        else
        {
            Node curr = head;
            while(curr.link != tail)
                curr = curr.link;

            tail = curr;
            curr.link = null;
        }

        return d;
    }
}