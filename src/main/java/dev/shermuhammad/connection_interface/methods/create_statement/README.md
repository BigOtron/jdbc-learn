# `createStatement()` method of `Connection` interface
- This method is used to create a `Statement` object which is then used to send queries to the database.
- `Statement` is used when the query doesn't have a parameter and doesn't run multiple times. In that case,
`PreparedStatement` is recommened to use because it is parameterized and pre-compiled in the database.
- [CreateStatement.java](CreateStatement.java) demonstrates how to use this method.
- The `ResultSet` returned by the methods of `Statement` is read-only and forward-only, meaning that we can't update
the values of the rows in place, and we can't move back while processes the `ResultSet`.
- The [ResultSetUsingStatement.java](ResultSetUsingStatement.java) shows the above ideas in action.