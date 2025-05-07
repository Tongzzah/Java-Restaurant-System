import java.util.LinkedList;
import java.util.Queue;

public class CompletedOrderQueue {
    private Queue<String> completedOrders = new LinkedList<>();
    private boolean isOpen = true;
    
    public synchronized void addCompletedOrder(String order) {
        if(isOpen) {
            completedOrders.add(order);
            notifyAll(); // Notify waiter
        }
    }
    
    public synchronized String getNextCompletedOrder() throws InterruptedException {
        while(completedOrders.isEmpty() && isOpen) {
            wait(); // Wait for completed orders
        }
        
        if(!isOpen) {
            return null;
        }
        
        return completedOrders.poll();
    }
    
    public synchronized void close() {
        isOpen = false;
        notifyAll();
    }
    
    public synchronized int getCompletedOrderCount() {
        return completedOrders.size();
    }
}
