# Database Communication Responsibility of Connection Interface
- When you want to execute SQL statements against a database, you don't interact with the database engine directly.
Instead, you use the Connection interface as a factory to manufacture Statement objects.
- The **Connection interface** provides three primary factory methods to create different types of statements, each 
serving a distinct architectural purpose:
  - `createStatement()` returns `Statement`
  - `prepareStatement(String sql)` returns `PreparedStatement`. It is best used for dynamic queries, CRUD operations, 
  and repetitive executions. It is pre-compiled and secure from SQL injections, and it accepts parameters.
  - `prepareCall(String sql)` returns `CallableStatement`. It executes database stored procedures.
- 