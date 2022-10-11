
# Business requirement

1. The system has 2 main module, Customer and Retail. The customer will buy production from the retail via his / her pre-deposited account.
2. Buy product flow is that, customer submit an order for x quantity product. The order should have product sku and quantity (how many product the customer want to buy) information. Then the total amount (quantity * price) is deducted from customer’s pre-deposited account. And the amount would be added to retail account, the quantity would be deducted from retail inventory.
3. Customer can invoke a REST API to do the deposit. Adding balance to his/her pre-deposited account. Currently, only on currency is okay.
4. Retail can invoke a REST API to increase the product inventory.
5. Retail will have a scheduler job that can do the account balance settlement everyday. Comparing the sold product value and retail account balance, 2 amount should be equally.

———————————————————————————————</br>
<h2><b>Brief</b></h2>

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

———————————————————————————————<br />
<h2><b>Brief</b></h2>
1. Spring, Spring Boot, Spring Cloud<br />
2. REST API<br />
3. Microservices<br />
4. Unit testing: least 80%<br />
5. SOLID principles<br />
6. DDD design<br />

==========================================<br />
# System design
<div align="left">
<img alt="System design" src="https://i.ibb.co/7gj95Xs/system-design.png">
</div>

==========================================<br />
# Directory Structure
<pre>
├── <a href="./database">database</a>: List of databases that will need to be created
├── <a href="./gateway">gateway</a>: Handles navigation between services, authen user, auto-registration and healcheck
├── <a href="./lib">lib</a>: For docker-compose
├── <a href="./order">order</a>: Manage order, update state for order after customer successful payment
├── <a href="./pre-deposited-account">pre-deposited-account</a>: Manage customers and customer accounts; allow customers to deposit their accounts; pay for the order they just created
├── <a href="./retail-account">retail-account</a>: Manage retail; receive amount for retail after customer successful payment; daily settlement
├── <a href="./retail-inventory">retail-inventory</a>: Product management for retail; this service will initiate the flow buy product; retail can call to increase inventory
</pre>
==========================================<br />
# Run a local

<pre>docker-compose up -d</pre>

==========================================<br />
# Init the base data

1. Add a new Customer
<pre> curl -X POST http://localhost:18000/pre-deposited-account/customer/add -H "Content-Type: application/json" -H "Authorization: Bearer ab53273e-4521-11ed-b878-0242ac120002" -d "[{\"email\":\"an.nguyen4@gmail.com\",\"name\":\"Nguyen the An\",\"phone\":\"0984364164\",\"token\":\"ddd0993fe9dfccfd6d7054f60f4db4df580581a73e3587aa543b6f8a6838947a\"},{\"email\":\"manh.nguyen3@gmail.com\",\"name\":\"Nguyen Huu Manh\",\"phone\":\"0942563135\",\"token\":\"fbdb8f91044939a2fd4e90d5b3dcce5f857bf38aecef5ef2951c717cee1771b5\"}]"</pre> 
2. Add a new Retail
<pre> curl -X POST http://localhost:18000/retail-account/retail/add -H "Content-Type: application/json" -H "Authorization: Bearer ab53273e-4521-11ed-b878-0242ac120002" -d "[{\"email\":\"louis@gmail.com\",\"name\":\"louis vuitton\",\"phone\":\"0934541542\",\"token\":\"fb614f798424df7a50464ac8ec16a42d97e8633a474e4c4008cc191c7dbe4ee8\"},{\"email\":\"gucci@gmail.com\",\"name\":\"gucci\",\"phone\":\"0912432853\",\"token\":\"0735d1fa98d865f55f63892cf296207acd9e75a007f1689b4dc839bb8293cf50\"}]"</pre> 
3. Add a new Product for retail id=1 (use retail's token as a representative)
<pre>  curl -X POST http://localhost:18000/retail-inventory/product/add -H "Content-Type: application/json" -H "Authorization: Bearer fb614f798424df7a50464ac8ec16a42d97e8633a474e4c4008cc191c7dbe4ee8" -d "\[{\"description\":\"Danh cho nu sieu dep\",\"name\":\"Ao somi\",\"price\":39000,\"quantity\":10,\"retailId\":1},{\"description\":\"Danh cho cac sep\",\"name\":\"Quan au\",\"price\":120000,\"quantity\":10,\"retailId\":1}\]"</pre> 
4. Add a new Product for retail id=2 (use retail's token as a representative)
<pre> curl -X POST http://localhost:18000/retail-inventory/product/add -H "Content-Type: application/json" -H "Authorization: Bearer 0735d1fa98d865f55f63892cf296207acd9e75a007f1689b4dc839bb8293cf50" -d "\[{\"description\":\"Mua dong den roi\",\"name\":\"Mu len\",\"price\":50000,\"quantity\":5,\"retailId\":2},{\"description\":\"Chat dong len roi\",\"name\":\"Quan sit\",\"price\":10000,\"quantity\":40,\"retailId\":2}\]"</pre> 

==========================================<br />
# Main flow

1. Deposit for customer id=1 (use user's token as a representative)
<pre> curl -X POST http://localhost:18000/pre-deposited-account/deposit/add -H "Content-Type: application/json" -H "Authorization: Bearer ddd0993fe9dfccfd6d7054f60f4db4df580581a73e3587aa543b6f8a6838947a" -d "{\"value\":600000}" </pre> 
2. Deposit for customer id=2 (use user's token as a representative)
<pre>  curl -X POST http://localhost:18000/pre-deposited-account/deposit/add -H "Content-Type: application/json" -H "Authorization: Bearer fbdb8f91044939a2fd4e90d5b3dcce5f857bf38aecef5ef2951c717cee1771b5" -d "{\"value\":600000}"</pre> 
3. Increase the product inventory for product id=1 (use retail's token as a representative)

4. Buy product flow

5. Settlement (manual trigger option)
<pre> curl -X GET http://localhost:18000/retail-account/settlement/manual-trigger -H "Authorization: Bearer ab53273e-4521-11ed-b878-0242ac120002"</pre>

