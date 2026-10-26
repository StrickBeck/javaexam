# Oral defence preparation

Kaisarbekov Argyn Zhasulanuly, Student ID 4211.

## Short introduction

"This is a console-based shop written in Java. It has customers, employees, product categories and orders. The model package describes these objects. Shop stores the data and performs the operations. Main displays the menus and reads input. The project demonstrates encapsulation, inheritance and associations."

## Demonstration

1. Start Main.java in IntelliJ IDEA or open run.cmd.
2. Log in as employee@shop.local with password Employee123.
3. Choose option 3 and show the users.
4. Choose option 2 and add a book: type 1, name OOP Practice, price 2000, stock 3, author Argyn. Its ID will be 4.
5. Choose option 1. Show the catalogue and explain the different details of books, electronics and clothing.
6. Log out using option 0.
7. Log in as customer@shop.local with password Customer123.
8. Choose option 2. Enter **1, 1, 2, 0**, one value per line, then confirm with **y**.
9. Explain the calculation: 3500 + 3500 + 15000 = 22000 KZT.
10. Choose option 3 to show the customer's history. Choose option 1 to show the remaining stock: 8 books and 4 mice.
11. To demonstrate rejection, choose product ID 2 five times when only four units remain, then enter 0 and y. The purchase is rejected, with no stock deduction.
12. Log in as the employee and choose **6** to view all orders.
13. Open the UML diagrams and explain the relationships.

These IDs and stock values assume a fresh application run. Enter each ID separately and press Enter. There is no separate quantity prompt: repeating an ID selects another unit.

## Where to demonstrate each concept

| Concept | Example in the code |
|---|---|
| Class and object | Product and new Book(...) |
| Fields and methods | price, stockQuantity and displayInfo() |
| Constructor | Product(...) |
| Encapsulation | private and setPrice/setStockQuantity |
| Inheritance | Book extends Product; Customer extends User |
| super | Initialising common fields and calling the parent displayInfo method |
| Overriding | Book.displayInfo() |
| Polymorphism | Main.showProducts(): a Product reference invokes the actual product type's method |
| Association | Order.customer and Order.products |
| Collection | ArrayList fields in Shop |
| Loops | Visiting products and counting selected units |
| Input | Scanner and readText/readInt |
| Condition | Checking stock before a purchase |
| Scope | The local wanted variable and the private products field |

## Practice questions and example answers

**What is the difference between a class and an object?**

A class describes the fields and behaviour of its objects. An object is one instance of a class. Book is a class; a particular Java textbook created with new Book(...) is an object.

**How are responsibilities divided between classes?**

User, Customer, Employee, Product, Book, Electronics, Clothing and Order describe the shop's objects. Shop manages the collections and operations. Main handles console interaction.

**Why are Customer and Employee so short?**

User already defines their common fields and methods. Their constructors pass the appropriate role to the parent constructor.

**Why are User and Product not abstract?**

Ordinary parent classes are sufficient to demonstrate the inheritance required by this assignment. The application creates specific customer, employee and product subclasses.

**Why use private fields and setters?**

They provide controlled access. For example, setStockQuantity checks the value before assignment and rejects negative stock.

**What does final mean?**

On a field, it prevents reassignment after initialisation. A final list reference still allows changes to the list's contents. On a method, final prevents overriding. On Shop, final prevents inheritance from that class.

**Where is polymorphism used?**

The ArrayList<Product> contains Book, Electronics and Clothing objects. showProducts calls displayInfo, and Java selects the override belonging to the actual object.

**What does an order store?**

It stores the customer, product list, order ID, total and receipt text. Each list entry represents one unit.

**How can a customer buy several units of one product?**

The customer selects the same ID several times. Shop counts how many times the product appears in the selected list.

**What do the two nested loops in placeOrder do?**

The outer loop visits each catalogue product. The inner loop counts the selected units of that product. The count is then compared with its stock.

**Why does a rejected purchase leave stock unchanged?**

All selected products are checked, and a valid Order is created, before a separate loop reduces stock.

**Why does an old receipt stay the same after a catalogue price changes?**

Order builds the receipt in its constructor and saves it in the receipt string. printReceipt returns the saved text. The total is also saved when the order is created.

**Why does getProducts return a new ArrayList?**

It returns a copy of the list. Clearing that copy does not clear the internal list. The Product objects inside are still shared.

**Why use double instead of BigDecimal?**

double is a familiar type from the course and keeps the teaching example simple. BigDecimal would be a possible improvement for exact financial calculations.

**How is the password checked?**

The classroom example compares strings using equals. The password exists only in memory during the current run and is excluded from user listings.

**What do the tests cover?**

They cover purchases, stock, repeated products, invalid values, cancellation, menus and private fields. The separate test runner reports 104 checks across eight groups.

**What happens when the application closes?**

Its in-memory records are cleared. The next run recreates the sample accounts and products.

## Before the defence

Start with Product and Book, then read User and its subclasses. Continue with Order, Shop.placeOrder and Main. Practise tracing one action from a menu choice to the saved order and updated stock.

Keep the PDF report open, the source ready in your IDE and the application ready to run.
