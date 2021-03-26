import java.util.LinkedList;
import java.util.Queue;

public class GlobalQueue {

    static Queue<Process> queue = new LinkedList<Process>();
   /*private static GlobalQueue globalQInstance = null;

    public static GlobalQueue getStreamInstance() {
    if (queue== null) {
       globalQInstance = new GlobalQueue();
    }
		return globalQInstance;
}*/
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
