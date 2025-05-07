import java.util.Scanner;

public class Restaurant {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        OrderQueue orderQueue = new OrderQueue();
        CompletedOrderQueue completedOrders = new CompletedOrderQueue();
        
        // Create kitchen staff (threads)
        KitchenStaff chef1 = new KitchenStaff("Chef John", orderQueue, completedOrders);
        KitchenStaff chef2 = new KitchenStaff("Chef Sarah", orderQueue, completedOrders);
        
        // Create waiter (thread)
        Waiter waiter = new Waiter("Waiter Mike", completedOrders);
        
        // Create manager monitor (thread)
        Manager manager = new Manager("Manager Lisa", orderQueue, completedOrders);
        
        // Start all threads
        chef1.start();
        chef2.start();
        waiter.start();
        manager.start();
        
        // Customer order input
        System.out.println("Restaurant Order System (type 'exit' to quit)");
        while(true) {
            System.out.print("Enter customer order: ");
            String order = scanner.nextLine();
            
            if(order.equalsIgnoreCase("exit")) {
                // Signal all threads to stop
                chef1.stopWorking();
                chef2.stopWorking();
                waiter.stopWorking();
                manager.stopMonitoring();
                break;
            }
            
            if(!order.isEmpty()) {
                synchronized(orderQueue) {
                    orderQueue.addOrder(order);
                    orderQueue.notifyAll(); // Notify waiting chefs
                }
            }
        }
        
        scanner.close();
    }
}
