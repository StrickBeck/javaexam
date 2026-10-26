# Validation

Date: 26 October 2026.
Platform: Windows. Compiler/runtime: OpenJDK 27.
Compilation target: Java 17 (--release 17).

Command:

    powershell.exe -NoProfile -ExecutionPolicy Bypass -File .\test.ps1

Actual result:

    Build successful: dist\ict-shop.jar
    PASS: 104 checks across 8 groups.

Production and test sources compiled with -Xlint:all without warnings.

## Eight groups

1. Model fields and inheritance: three demo product types, polymorphic output and invalid names, IDs, prices/stock.
2. Accounts: roles, login, case-insensitive/trimmed email, duplicate email, invalid input and user additions.
3. Successful orders: repeated products as individual units, total 22000 KZT, stock, receipts and own/all history.
4. Rejected orders: combined demand, no partial deduction, empty/null lists, unknown products/customer, no stock, overflowing total and next order ID.
5. Receipt and collection behaviour: stored receipt/total, copied lists and catalogue changes after purchase.
6. Console workflow: all three product types, both user types, invalid price recovery, checkout, history and exit.
7. Input/error handling: invalid integer/menu choice, login failure, cancellation, empty selection, insufficient stock, missing product and EOF.
8. Reflection checks: state fields in the application classes are private.

ShopTests.java is an optional test runner under src/test/java. It is separate from the application and is not packaged in the application JAR.

The Windows launcher and packaged JAR are also checked. POSIX scripts are supplied but were not run here. The application uses in-memory storage and a single console thread; these checks do not claim server concurrency or exact financial arithmetic.
