# Library management system

A command-based library management and book-ordering system developed as the final project for the **Data Structures** course during my bachelor's studies.

**Author:** Ava Moshfegh  
**Completed:** 23 December 2022

The system organizes books into categories and subcategories, supports catalog searches, and processes orders by priority. Customers can order individual books or collect several books in a personal shopping cart before submitting an order. A customer's previous orders can be retrieved through their unique identifier.

The project implements its own data structures for catalog organization, order scheduling, shopping carts, and customer order history.

## Features

- Hierarchical book categories with nested subcategories.
- Book records containing a title, author, and price.
- Commands to add and remove books and categories.
- Catalog-wide searches and book listings by category.
- A max-heap order queue that prioritizes higher-value orders and breaks price ties by submission time.
- Customer records with unique identifiers.
- Individual shopping carts implemented as max heaps ordered by book price.
- Checkout of one or more books as a single order.
- Customer-specific order history stored through a custom map.

## Catalog organization

The catalog is organized as a tree. Its root represents the library, and categories form branches beneath it. A category can contain books, subcategories, or both, allowing related subjects to be grouped at different levels.

For example, an Academic category can contain Engineering, which can contain Computer Science. Books about data structures and operating systems can then be placed under Computer Science. A separate General category can hold individual books alongside a Fantasy subcategory.

Every book stores:

| Field | Description |
| --- | --- |
| Title | The book's name, used when searching, ordering, or removing it. |
| Author | The name of the book's author. |
| Price | The book's price, used for cart ordering and order priority. |

Removing a category removes its branch from the catalog, including nested subcategories and their book entries.

## Data structures

| Structure | Role in the system |
| --- | --- |
| Category tree | Represents the catalog hierarchy and groups books under categories and subcategories. |
| Order max heap | Holds submitted orders, keeping the highest-priority order at the root. |
| Shopping-cart max heap | Holds a customer's selected books with higher-priced books taking priority. |
| Customer-history map | Associates each customer's identifier with their order history. |

These structures are implemented within the project rather than delegated to ready-made tree, heap, priority-queue, or map collections. Basic arrays or dynamic-array containers provide the underlying storage where needed.

## Order priority

The library's order queue uses two ordering rules:

1. An order with a higher price has higher priority.
2. If two orders have the same price, the order submitted earlier has higher priority.

For a multi-book order, the order value is the combined price of its books. Cart contents are submitted together as one order rather than as separate orders for each book.

After each insertion, the max heap maintains the highest-priority order at its root. Processing the queue repeatedly removes that root and restores the heap before moving to the next order.

For example, consider these submitted orders. Prices are illustrative and use arbitrary units.

| Order | Total price | Submission time | Processing position |
| --- | --- | --- | --- |
| A | 120 | 10:00 | 2 |
| B | 200 | 10:05 | 1 |
| C | 120 | 10:10 | 3 |

Order B is processed first because it has the highest value. A is processed before C because it was submitted earlier at the same value.

**`list orders` processes and empties the queue.** It repeatedly displays and removes the highest-priority order until no pending orders remain.

## Shopping carts and checkout

Each customer has a unique identifier and a personal shopping cart. Books added to a cart are stored in a max heap based on price, so the highest-priced book is at the root.

The shopping flow is:

1. Browse the catalog or search for a book.
2. Add one or more books to a customer's cart.
3. Finalize the cart as a single order.
4. Add the finalized order to the library's priority queue.
5. Retrieve the customer's submitted orders through the order-history command.

Adding a book to a cart does not submit an order. Only checkout places the selected books into the library's pending-order queue. Checkout is a direct command operation without a payment or sign-in step.

## Commands

The command interface provides catalog-management and customer-ordering operations. In the syntax below, text inside angle brackets represents a value to supply; the angle brackets themselves are not part of the command. `<book data>` contains the book's title, author, and price. `<i>` is the customer's unique identifier.

### Categories and books

| Command | Action |
| --- | --- |
| `add category <category name>` | Adds a new top-level category. |
| `add subcategory <subcategory name> to <category name>` | Adds a subcategory beneath an existing category. |
| `add book <book data> to <category name>` | Adds a book, including its title, author, and price, to a category. |
| `remove book <book name> from <category name>` | Removes a book from the specified category. |
| `remove category <category name>` | Removes a category and its nested contents. |

### Searching and browsing

| Command | Action |
| --- | --- |
| `search <book name>` | Displays the information for a book. |
| `list books` | Displays the information for all books in the catalog. |
| `list books from <category name>` | Displays the books in the specified category. |

### Ordering and order processing

| Command | Action |
| --- | --- |
| `order <book name>` | Submits an individual book order. |
| `list orders` | Displays and processes pending orders in priority order, removing them from the queue. |

### Customer carts and history

| Command | Action |
| --- | --- |
| `user <i> add <book name>` | Adds a book to the specified customer's shopping cart. |
| `user <i> order` | Finalizes the customer's cart and submits its contents as one order. |
| `order log of <i>` | Displays the customer's previous orders. |

## Example workflow

Create a category hierarchy:

```text
add category Academic
add subcategory Computing to Academic
add subcategory Algorithms to Computing
```

Add books to the appropriate category using `add book <book data> to <category name>`, supplying a title, author, and price for each book.

Once books named `Dune` and `Foundation` are in the catalog, these commands illustrate browsing and a multi-book checkout for a customer with identifier `1`:

```text
list books
search Dune
user 1 add Dune
user 1 add Foundation
user 1 order
order log of 1
```

An individual book can also be ordered directly:

```text
order Dune
```

Process all pending orders in priority order:

```text
list orders
```

Other catalog operations include listing a category's books and removing entries:

```text
list books from Algorithms
remove book Dune from Fiction
remove category Algorithms
```

The removal example assumes that `Dune` is stored under an existing `Fiction` category.

## Project focus

The project connects several data structures in one application: a tree for hierarchical organization, max heaps for price-based selection and scheduling, and a map for customer-specific history. It also handles a secondary ordering condition: preserving submission-time priority when two orders have equal prices.
