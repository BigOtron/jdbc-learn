# `nativeSQL(String sql)` method of `Connection` interface
- This method converts a standard, database-independent JDBC SQL string containing JDBC escape syntax into the exact 
native SQL dialect of the underlying database.
- Developers rarely need to call this method explicitly because the JDBC driver automatically invokes it behind the 
scenes before executing any query via a `Statement` or `PreparedStatement`.
- It is highly useful for debugging complex queries to see the literal SQL string being sent across the network, 
or when building custom developer tools, frameworks, and database migrators.
- The [NativeSql.java](NativeSQL.java) shows how a generic JDBC string translates into a specific database dialect 
(e.g., converting `{fn CURDATE()}` to PostgreSQL's `CURRENT_DATE`).