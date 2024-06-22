public class Queue extends LinkedList
{
    public Queue()
    {
        super();
    }

    public void enqueue (Construction obj)
    {
        insertAtBack(obj);
    }

    public Construction dequeue()
    {
        return removeFromFront();
    }

    public Construction getFront()
    {
        return getFirst();
    }

    public Construction getEnd()
    {
        Construction obj = removeFromBack();
        insertAtBack(obj);
        return obj;
    }

    public boolean isEmpty()
    {
        return super.isEmpty();
    }

    public String print()
    {
        return super.print();
    }

}