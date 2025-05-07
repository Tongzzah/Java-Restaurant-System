public class Waiter extends Thread {
    private String name;
    private CompletedOrderQueue completedOrders;
    private boolean working = true;
    
    public Waiter(String name, CompletedOrderQueue completedOrders) {
        this.name = name;
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
                String order = completedOrders.getNextCompletedOrder();
                if(order == null) break;
                
                System.out.println(name + " is delivering: " + order);
                Thread.sleep(1000 + (int)(Math.random() * 2000)); // Simulate delivery time
                
                System.out.println(name + " delivered: " + order);
                
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        
        System.out.println(name + " stopped working");
    }
}
