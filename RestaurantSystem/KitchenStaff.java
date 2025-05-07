public class KitchenStaff extends Thread {
    private String name;
    private OrderQueue orderQueue;
    private CompletedOrderQueue completedOrders;
    private boolean working = true;
    
    public KitchenStaff(String name, OrderQueue orderQueue, CompletedOrderQueue completedOrders) {
        this.name = name;
        this.orderQueue = orderQueue;
        this.completedOrders = completedOrders;
    }
    
    public void stopWorking() {
        working = false;
        interrupt(); // Interrupt if waiting
    }
    
    @Override
    public void run() {
        System.out.println(name + " started working");
        
        while(working) {
            try {
                String order = orderQueue.getNextOrder();
                if(order == null) break;
                
                System.out.println(name + " is preparing: " + order);
                Thread.sleep(2000 + (int)(Math.random() * 3000)); // Simulate cooking time
                
                String completedOrder = order + " (prepared by " + name + ")";
                synchronized(completedOrders) {
                    completedOrders.addCompletedOrder(completedOrder);
                }
                
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        
        System.out.println(name + " stopped working");
    }
}
