# Understanding the code

Read the classes in the order below. A class describes a kind of object; an object is one instance created from that class.

Main reads input, Shop performs operations, and the model classes describe users, products and orders.

## Product: common product information

Product stores id, name, price and stockQuantity. All fields are private.

The constructor sets the values when an object is created. In this.name = name, this.name is the field of the current object, while name is the method parameter.

Getters return values. Setters check new values before changing the fields. setPrice rejects negative prices, NaN and infinity. setStockQuantity rejects negative stock. setName rejects an empty name.

displayInfo returns a formatted string containing the ID, name, price and stock. Main prints that string.

The id field is final: its value is assigned in the constructor and cannot be reassigned.

## Book: a product with an author

Book extends Product means that a book is a product. Its common information is handled by Product. Book adds the private author field.

super(id, name, price, stockQuantity) calls the Product constructor. setAuthor checks and assigns the author.

Book overrides displayInfo. It calls super.displayInfo() for the common details, then adds the author. @Override asks the compiler to verify that this method overrides a parent method.

## Electronics: a product with a brand

Electronics extends Product and adds brand. Its setter rejects an empty brand. Its displayInfo method adds the brand to the common product description.

## Clothing: a product with a size

Clothing extends Product and adds size, such as M or L. Its setter rejects an empty size. Its displayInfo method adds the size to the product description.

## User: common user information

User stores id, name, email, password and role. Its fields are private.

The constructor checks the ID and uses setters to check the name, email and password. The email must contain @, and the password must have at least four characters.

checkPassword compares the entered password with the saved password using equals. This is a classroom example that stores the password in memory.

toString returns the ID, role, name and email. It does not expose the password.

The ID and role are final. The basic setters are also final, which prevents a subclass from overriding their validation.

## Customer: a user who buys products

Customer extends User. Its constructor passes the received values and the role Customer to the User constructor.

It is short because User already defines the common state and methods. Main opens the customer menu after a customer logs in.

## Employee: a user who manages records

Employee also extends User. Its constructor passes the role Employee to the parent constructor.

Main checks whether the logged-in object is an Employee and opens the employee menu. The available employee actions include adding users and products and viewing all orders.

## Order: a completed purchase

Order stores orderId, customer, products, totalAmount and receipt.

Each entry in products represents one purchased unit. If a book is selected twice, the same product reference appears twice.

The constructor checks its arguments and copies the selected list. It then visits each product and adds its price to the total. A StringBuilder builds the receipt line by line.

Example:

    [Book, Book, Mouse]
    3500 + 3500 + 15000 = 22000 KZT

The completed text is saved in receipt. printReceipt returns this saved string for Main to print. Later changes to catalogue names or prices do not change the saved receipt or total.

getProducts returns a new list containing the same product references. Clearing the returned list does not clear the list inside the order. The product objects themselves remain shared.

Order has a customer and products. This is an association, rather than inheritance.

## Shop: data and operations

Shop keeps the following collections:

    ArrayList<User> users
    ArrayList<Product> products
    ArrayList<Order> orders

ArrayList is a list that can grow as elements are added. The constructor adds the demonstration records.

addCustomer and addEmployee create users after checking for a duplicate email. addProduct adds a product and rejects duplicate product IDs. login finds the email and checks the password.

findProduct searches the catalogue by ID. It returns the matching product or throws an IllegalArgumentException if the product does not exist.

getCustomerOrders collects orders belonging to the specified customer.

### placeOrder step by step

1. Check that the customer is registered and the selection is not empty.
2. Check that each selected product belongs to the catalogue.
3. For each catalogue product, count its occurrences in the selection.
4. Compare that count with the available stock.
5. Create the Order, including its total and receipt.
6. Deduct one unit for each selected list entry.
7. Add the order to the orders list.

Stock is not changed before step 6. If a check or order construction fails, no part of the purchase is deducted.

The outer loop visits the catalogue. The inner loop counts the selected units of one product. chosen == product checks whether two references point to the same object. This works because Main selects the catalogue objects returned by findProduct.

Collection getters return copies of the lists. They do not copy the objects inside those lists.

A final list field cannot be assigned a different list, but elements can still be added to or removed from its existing list. final on Shop means that another class cannot extend Shop.

## Main: console interaction

The static main method starts the application. It creates Shop, Scanner and a Main object, then calls run.

run displays the main menu in a loop. switch chooses an action. Option 0 ends the loop.

login reads an email and password, calls Shop.login, and opens the appropriate menu. instanceof Employee checks the actual type of the returned user.

employeeMenu provides employee actions. customerMenu provides customer actions.

Main.placeOrder builds an ArrayList<Product> from the selected IDs. Every entered ID adds one unit. Entering 0 finishes the selection. After confirmation, it calls Shop.placeOrder and prints the receipt.

readText reads a whole, non-empty line. readInt converts it to int and asks again after invalid numeric input. readPrice similarly reads a finite, non-negative double.

try/catch handles errors and displays a message. If console input ends, run exits cleanly.

## Polymorphism in this project

An ArrayList<Product> can contain Book, Electronics and Clothing objects. Main calls displayInfo on a Product reference, and Java selects the method belonging to the actual object.

For example, a Product variable referring to a Book object calls Book.displayInfo. This is polymorphism.

## Terms to remember

- Class: a description of objects with shared fields and behaviour.
- Object: one instance of a class.
- Constructor: initialises a newly created object.
- private: restricts direct access to a member from other classes.
- Getter and setter: read a value or change it through a method.
- Encapsulation: keeps state inside an object and controls access.
- extends: declares inheritance.
- super: accesses a parent constructor or method.
- @Override: marks a method that overrides a parent method.
- Association: a relationship where one object refers to another.
- ArrayList: a list whose size can change.
- for: visits elements; while: repeats an action while a condition holds.
- return: sends a result back to the caller or ends a void method.
- throw: reports an invalid operation by throwing an exception.
- try/catch: handles an exception.

## Limits of the example

Data is kept in memory and resets when the application restarts. Prices use double, and the receipt displays two decimal places. The simple password comparison is intended for classroom use.
