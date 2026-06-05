# Connection interface introduction
- What are the responsibilities of the Connection interface?
  - **Connection** interface represents an active session between the data source and the Java application.
  Key responsibilities of the interface are:
    - Communication: It creates the session with which our Java application can interact with the database.
    - Transaction Management: It can control when and how the changes to the database are saved.
    - Metadata: It can provide information about the database's capabilities.
  - This interface is in the **java.sql** package.
- Connection interface is Autocloseable, thus we should use it with a try-with resources block.