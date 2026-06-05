# Database Communication Responsibility of Connection Interface
- When you want to execute SQL statements against a database, you don't interact with the database engine directly.
Instead, you use the Connection interface as a factory to manufacture Statement objects.
- The **Connection interface** provides three primary factory methods to create different types of statements, each 
serving a distinct architectural purpose:
  - `createStatement()` returns `Statement`
  - `prepareStatement(String sql)` returns `PreparedStatement`. It is best used for dynamic queries, CRUD operations, 
  and repetitive executions. It is pre-compiled and secure from SQL injections, and it accepts parameters.
  - `prepareCall(String sql)` returns `CallableStatement`. It executes database-stored procedures.
- The [StatementCodeExample.java](StatementCodeExample.java) demonstrates how to create a connection and a statement.
- The [SqlInjectionExample.java](SqlInjectionExample.java) shows a simulated SQL injection attack that `Statement`
allows to happen. In this case, we are recommended to use `PreparedStatement`;
- The [PreparedStatementExample.java](PreparedStatementExample.java) demonstrates how to use `PreparedStatement` 
queries. We can use parameters with `PreparedStatement` as the code shows thus such statements are safe from SQL
injections that we saw with `Statement` queries.