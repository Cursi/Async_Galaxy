import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Test
{
    public static void main(String args[])
    {
        Hashtable<Integer, ConcurrentLinkedQueue<String>> h = new Hashtable<>();
        if(h.get(10) == null)
            h.put(10, new ConcurrentLinkedQueue<>());
        h.get(10).add("Test1");

        if(h.get(10) == null)
            h.put(10, new ConcurrentLinkedQueue<>());
        h.get(10).add("Test2");

        if(h.get(20) == null)
            h.put(20, new ConcurrentLinkedQueue<>());
        h.get(20).add("Test4");

        if(h.get(10) == null)
            h.put(10, new ConcurrentLinkedQueue<>());
        h.get(10).add("Test3");

        for (Map.Entry m:h.entrySet())
        {
            System.out.println(m.getKey()+" "+m.getValue());
        }
    }
}