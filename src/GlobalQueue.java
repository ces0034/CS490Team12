import java.util.LinkedList;
import java.util.Queue;

//A queue class that just returns most of what a queue already does but now synchronized.
public class GlobalQueue implements Cloneable{

    static Queue<Process> queue;

    public GlobalQueue()
    {
        queue = new LinkedList<>();

    }

    public Queue<Process> get()
    {
        return queue;
    }

    public void add(Process process)
    {
        synchronized(queue)
        {
            queue.add(process);
        }

    }

    public void remove(Process process)
    {
       synchronized(queue)
        {
            queue.remove(process);
        }
    }
    public Process peek()
    {
        synchronized(queue) {
            Process process = queue.peek();
            return process;
        }
    }
    protected Object clone() throws CloneNotSupportedException {
        GlobalQueue q = (GlobalQueue)super.clone();
        for (Process process: q.get())
        {
            process=(Process)super.clone();
            q.add(process);
        }
        return q;
    }

    public Process poll()
    {
        synchronized(queue) {
            Process process = queue.poll();
            return process;
        }
    }

    public boolean isEmpty()
    {
       synchronized(queue) {
            return queue.isEmpty();
       }
    }

    public int getTotalSize()
    {
        synchronized(queue) {
            return queue.size();
        }
    }




}
