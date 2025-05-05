#CarMotors

Automotive service management system.

## Project Progress

### Day 1 (Eimer)
- Configured the project with Maven, JDK 17, and MySQL.
- Created the initial structure with packages and base classes. See `docs/classes.md`.
- Added a class diagram.

### Day 1 (Laia)
- Added tables and insertions.
- Added ER Diagram.
- Connected DBeaver with Git host.

### Day 2 (Laia)
- Improved `DatabaseConnection` to use `config/local.properties` for configuration.
- Added packages for suppliers and spare parts to improve project organization.
- Worked on integrating supplier and spare parts management into a unified interface (`CombinedInventoryView`).
- Fixed database table and column name mismatches (e.g., `supplier` table issues).
- Managed Git commits, resolved conflicts, and updated commit messages for clarity.

### Day 2 (Eimer)
- Improved `DatabaseConnection` to use `config/local.properties` for configuration.
- Configured `DatabaseConnection.java` as a singleton and resolved classpath issue by moving project to local path.
- Verified database connection, logging sample data with Log4J.
- Added basic classes in `services`, `customers`, and `invoicing` with initial interface.


### Day 3 (Eimer)
- Implemented `services`, `customers`, and `invoicing` with schema corrections.
- Created `WelcomeScreen.java` with `CardLayout` for navigation between sections.
- Integrated `SparePartView` and `SupplierView` into `WelcomeScreen.java` with their controllers and DAO.

### Day 3 (Laia)
- Enhanced `SupplierDAO` and `SparePartDAO` to include CRUD operations (Create, Read, Update, Delete) for better data management.
- Implemented `CombinedInventoryController` to synchronize supplier and spare parts data in `CombinedInventoryView`.
- Added input validation in `SparePartView` and `SupplierView` to prevent invalid data entries (e.g., negative quantities or empty fields).
- Updated ER Diagram to reflect new relationships between `suppliers`, `spare_parts`, and `inventory` tables.
- Resolved minor Git merge conflicts and ensured consistent commit messages for better project tracking.

### Day 4 (Laia)
- Developed `InventoryManagementService` to handle stock updates and low-stock alerts for spare parts.
- Integrated `InventoryManagementService` with `CombinedInventoryView` to display real-time stock levels.
- Added search and filter functionality to `SparePartView` for easier navigation of large datasets.
- Optimized database queries in `SparePartDAO` to improve performance for large inventories.
- Updated `docs/classes.md` with new service and controller classes and documented their responsibilities.
- Conducted testing on supplier and spare parts modules, fixing bugs related to data persistence and UI updates.