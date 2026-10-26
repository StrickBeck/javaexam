# Assignment 1 - Console Shop

**Student:** Kaisarbekov Argyn Zhasulanuly  
**Student ID:** 4211  
**Course:** Information and Communication Technologies  
**Date:** 26 October 2026

A simple console-based shop written in Java. The application, report, UML diagrams, code guide and oral defence guide are in English.

## Quick start

1. Extract the archive into a new, empty folder.
2. On Windows, open **run.cmd** to launch the application.
3. To inspect and run the Java code, open the project folder in **IntelliJ IDEA**, open **src/main/java/edu/ict/shop/Main.java**, and run its main method.

The project includes an IntelliJ module and a run configuration named **ICT Console Shop**. This computer uses the SDK **openjdk-27**. On another computer, select an installed JDK 17 or newer under File > Project Structure > Project SDK.

Application sources are under src/main/java. The separate automated checks are under src/test/java. If IntelliJ does not recognise them, mark these folders as Sources Root and Test Sources Root respectively.

No Maven or Gradle dependencies are required. The ready-to-run application is dist/ict-shop.jar.

## Project structure

| Class | Responsibility |
|---|---|
| User | Common user information |
| Customer | A customer; extends User |
| Employee | An employee; extends User |
| Product | Common product information |
| Book | A book and its author |
| Electronics | An electronic product and its brand |
| Clothing | A clothing product and its size |
| Order | Customer, purchased products, total and receipt |
| Shop | Collections of users, products and orders; shop operations |
| Main | Startup, Scanner input and console menus |

Shop performs the shop operations. Main handles input and menus. Classes in the model package describe users, products and orders.

## Demonstration accounts

| Role | Email | Password |
|---|---|---|
| Employee | employee@shop.local | Employee123 |
| Customer | customer@shop.local | Customer123 |

A password must contain at least four characters. The classroom login compares strings stored in memory. Passwords are excluded from user listings. This simple demonstration is not intended for real account credentials.

## Placing an order

Each product ID entered adds **one unit** of that product.

To buy two books and one mouse:

1. Choose option 1 in the main menu and log in as the customer.
2. Choose option 2 in the customer menu.
3. Enter **1**, **1**, then **2**, pressing Enter after each ID.
4. Enter **0** to finish selecting products.
5. Enter **y** to confirm the purchase.

The total is 3500 + 3500 + 15000 = **22000 KZT**. The receipt shows each purchased unit on a separate line. Repeated IDs are counted together when checking the available stock.

## Menus and storage

The main menu provides login, customer registration, product listing and exit.

The employee menu provides product listing, product creation, user listing, customer creation, employee creation and viewing all orders.

The customer menu provides product listing, order placement and viewing the current customer's orders.

The menus support adding and listing products. Product setters allow controlled changes to the name, price and stock in Java code.

All records are stored in ArrayList collections during the current run. Restarting the application restores two sample accounts and three sample products.

## Suggested reading order

1. **Product.java**: fields, constructor, getters and setters.
2. **Book.java**: extends, super(...) and @Override.
3. **User.java**, followed by Customer.java and Employee.java.
4. **Order.java**: the total and receipt.
5. **Shop.java**, especially placeOrder.
6. **Main.java**: how the menus connect these parts.

Use [the code guide](docs/CODE-GUIDE.md) for explanations and [the oral defence guide](docs/DEFENSE.md) for the demonstration and practice questions.

## Building and checking

On Windows:

    powershell.exe -NoProfile -ExecutionPolicy Bypass -File .\build.ps1
    powershell.exe -NoProfile -ExecutionPolicy Bypass -File .\test.ps1

Expected check result:

    PASS: 104 checks across 8 groups.

On Linux/macOS with a JDK installed:

    sh build.sh
    sh run.sh
    sh test.sh

To launch the packaged application directly:

    java -jar dist/ict-shop.jar

The project was checked on Windows using OpenJDK 27, targeting Java 17. The POSIX scripts were not run in this environment. Automated checks are separate from the application and are not included in the application JAR.

## Documents

- [Project report](docs/Kaisarbekov-Argyn-Zhasulanuly-Report.pdf): requirements, objective, design, implementation, testing, conclusion and complete application source.
- [UML class diagrams](docs/UML-Class-Diagram.pdf): classes and their relationships.
- [Editable UML](docs/class-diagram.puml): the diagram in PlantUML format.
- [Oral defence guide](docs/DEFENSE.md): demonstration steps and questions with example answers.
- [Code guide](docs/CODE-GUIDE.md): a simple explanation of the code.
- [Validation record](docs/TESTING.md): the checks performed.

Prices use double, as a familiar teaching example. Amounts are displayed with two decimal places; exact financial arithmetic could be a reason to use BigDecimal in a future version. Each receipt is built once when its order is created and saved as a string.

