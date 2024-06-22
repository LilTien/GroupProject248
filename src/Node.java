public class Node           // class Node is used to create a Node
{
    Construction data;
    Node link;

    //Default constructor
    public Node()
    {
        data = null;
        link = null;
    }

    //Normal constructor
    public Node(Construction data)
    {
        this.data = data;
        link = null;
    }

    public Node(Construction data, Node next)
    {
        this.data = data;
        this.link = next;
    }

    //Update data
    public void setData(Construction newData)
    {
        data = newData;
    }

    //Update the reference to the next node
    public void setNext(Node newNext)
    {
        link = newNext;
    }
    //To get the data
    public Construction getData()
    {
        return data;
    }

    //To get the next data
    public Node getNext()
    {
        return link;
    }

    //To print
    public String toString()
    {
        if (link == null)
            return data.toString() + "";
        else
            return data.toString() + "," + link.toString();
    }

}