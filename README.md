
# Business requirement

1. The system has 2 main module, Customer and Retail. The customer will buy production from the retail via his / her pre-deposited account.
2. Buy product flow is that, customer submit an order for x quantity product. The order should have product sku and quantity (how many product the customer want to buy) information. Then the total amount (quantity * price) is deducted from customer’s pre-deposited account. And the amount would be added to retail account, the quantity would be deducted from retail inventory.
3. Customer can invoke a REST API to do the deposit. Adding balance to his/her pre-deposited account. Currently, only on currency is okay.
4. Retail can invoke a REST API to increase the product inventory.
5. Retail will have a scheduler job that can do the account balance settlement everyday. Comparing the sold product value and retail account balance, 2 amount should be equally.

———————————————————————————————

Object: Customer, Retail

1. Pre-Deposited account: 
    - customer can do the deposit: Adding balance
    - buy product: deducted from customer’s pre-deposited account
    - deposit: adding balance
2. Order: 
    -  x quantity product: how many product the customer want to buy
    - total amount: quantity * price
    - retail account: the amount would be added to retail account
    - retail inventory: the quantity would be deducted from retail inventory
3. Retail account: 
    - amount sold
    - settlement: a scheduler job that can do the account balance settlement everyday (order’s sold product = retail account balance)
4. Retail inventory: 
    - quantity sold
    - increase the product inventory

==========================================<br />
# System design requirement

1. The system should be implemented with Java programing language.  Using Spring, Spring Boot and Spring Cloud tech stack.
2. The system would provide REST API to the client, and the REST API design should follow REST API best practices.
3. The system must be a runnable Spring Boot application, all functionalities can work well.
4. Unit testing coverage at least 80% for the core code.
5. Code must be configurable and extensible. Follow the SOLID principles. Will add new features during the online interview.
6. DDD design methodology is better to have. Clearing design for the Boundary Context and Aggregate Root.

———————————————————————————————
1. Spring, Spring Boot, Spring Cloud
2. REST API
3. Microservices
4. Unit testing: least 80%
5. SOLID principles
6. DDD design

==========================================<br />
# Project structure

Services:
1. Gateway
2. Pre-Deposited account
3. Order
4. Retail account
5. Retail inventory<br />
Lib:
1. Parent
2. Data

==========================================<br />
# System design
<div align="left">
<img alt="System design" src="https://i.ibb.co/7gj95Xs/system-design.png">
</div>

