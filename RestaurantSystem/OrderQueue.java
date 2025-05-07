import java.util.LinkedList;
import java.util.Queue;

public class OrderQueue {
    private Queue<String> orders = new LinkedList<>();
    private boolean isOpen = true;
    
    public synchronized void addOrder(String order) {
        if(isOpen) {
            orders.add(order);
            System.out.println("Added order: " + order);
        }
    }
    
    public synchronized String getNextOrder() throws InterruptedException {
        while(orders.isEmpty() && isOpen) {
            wait(); // Wait for new orders
        }
        
        if(!isOpen) {
            return null;
        }
        
        return orders.poll();
    }
    
    public synchronized void close() {
        isOpen = false;
        notifyAll(); // Notify all waiting threads
    }
    
    public synchronized int getPendingOrderCount() {
        return orders.size();
    }
}
