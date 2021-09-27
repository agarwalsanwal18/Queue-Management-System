# Queue-Management-System

We assume that the customers arrive randomly. A new customer always joins the billing queue with the smallest length at that time. If there are multiple billing queues with the same smallest lengths, then the lowest numbered queue of those is chosen by the customer. If two customers arrive at the same time, we are assuming that they came one after the other at the same time instant, i.e., the first customer will already be in some queue, when the second customer is deciding which queue to join.

Different employees servicing the different billing queues are not equally efficient. The billing specialist (who takes the order and processes payment for a customer) in billing queue k will take k units of time in completing the order. After the order is completed, the customer order is printed automatically and
sent to the chef, who prepares the burgers in the sequence he/she receives the orders. If two orders arrive simultaneously then the chef chooses the order from the higher numbered billing queue first.

The chef has a large griddle on which at most M burger patties can be cooked simultaneously. Each burger patty gets cooked in exactly 10 units of time. Whenever a patty is cooked another patty starts cooking in the same time instant. Upon cooking, the burger is delivered to the customer in one unit of time. Whenever a customer gets all their burgers, they leave the restaurant instantaneously (it’s a take-away restaurant with no dine-in).

This whole process was simulated by various events. Events in the simulation environment are arrival/departure of a customer, completion of payment for an order,
completion of one or more burgers, etc. For each customer, the following states were maintained: waiting in queue, waiting for food, or left the building. A global clock was also maintained, which moves forward after all events of a given time point are simulated.

If there were multiple events happening at the same time instant, the events were executed in the following order:
  1. Billing specialist prints an order and sends it to the chef; customer leaves the queue.
  2. A cooked patty is removed from the griddle.
  3. The chef puts another patty on the griddle.
  4. A newly arrived customer joins a queue.
  5. Cooked burgers are delivered to customers.

If there is a query at the same time instant, the query must be answered after all the events for the time instant have been executed in the above-mentioned order.

Implemented two data structures- AVL Trees and Priority Queue using Min Heap.
