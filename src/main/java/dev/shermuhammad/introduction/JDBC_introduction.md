# JDBC Introduction

- What is JDBC?
  - JDBC stands for Java Database Connectivity. It is an API that allows Java applications to connect and talk to RDBMS
  like Postgresql, MySql, or Oracle.
  - Another interesting thing about JDBC is that it can connect not only to RDBMS but also any data source that
  implements a JDBC-compliant driver. For example, NoSQL databases and even CSV/Excel files via special drivers.
- What does JDBC do? Why do we need it?
  - RDBMS technologies work in the client/server format where the database acts as a server and exposes a particular
  port for clients to connect and talk to it. A client can be anything that can connect to the database server.
  For example, *psql* tool or our Java application. In the case of the *psql* client tool, it knows how to connect to
  the server. JDBC is a library that makes it possible for our Java applications to connect and execute SQL statements 
  on the database server.
  - JDBC offers Java applications a unified interface to access any data source that has JDBC compliant driver. This 
  means that if our application previously used Oracle RDBMS, and it needs to switch to Postgresql now. We simply
  change the driver and code written to connect and interact with the data source will work provided we followed SQL
  standards and didn't depend on the particular RDBMS features that are exclusive to that RDBMS.
- The JDBC API consists of two packages:
  - **java.sql**
  - **javax.sql**
  - These packages are included in all Java Editions. I am not sure about all Java Editions, but I am sure they are 
  included in Java 8+ editions.
- What is the general way to start working with JDBC API?
  1. We include the chosen JDBC data source driver in the classpath.
  2. We create a connection to the data source. I wrote a sample [code](DatabaseClient.java) that demonstrates how to 
  create a connection and interact with the database.
  3. We then create a statement.
  4. We execute the statement.
  5. That execution will return us a response. We process that response.
  6. We finally close the connection. We should remember that **Connection, Statement, and ResultSet** are 
  **AutoClosable**.