public class Manager extends Thread {
    private String name;
    private OrderQueue orderQueue;
    private CompletedOrderQueue completedOrders;
    private boolean monitoring = true;
    
    public Manager(String name, OrderQueue orderQueue, CompletedOrderQueue completedOrders) {
        this.name = name;
        this.orderQueue = orderQueue;
        this.completedOrders = completedOrders;
    }
    
    public void stopMonitoring() {
        monitoring = false;
        interrupt(); // Interrupt if waiting
    }
    
    @Override
    public void run() {
        System.out.println(name + " started monitoring");
        
        while(monitoring) {
            try {
                Thread.sleep(5000); // Check status every 5 seconds
                
                int pending = orderQueue.getPendingOrderCount();
                int completed = completedOrders.getCompletedOrderCount();
                
                System.out.println("\n[Manager Report] Pending orders: " + pending + 
                                  " | Completed orders: " + completed + "\n");
                
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        
        System.out.println(name + " stopped monitoring");
    }
}
