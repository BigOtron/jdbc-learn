# About `setAutoCommit()` method
- This method acts like a switch that can turn on/off the commit feature of a database. By default, it is set to true, 
and every query is carried out in a separate transaction. If we want to carry out multiple queries in a single 
transaction, then we need to switch off auto commit mode and manage the operations ourselves.
- [SetAutoCommit.java](SetAutoCommit.java) demonstrates how to use this method and additionally shows a common pattern
of managing the transaction. If an exception occurs during some operation, then we catch that exception and 
`rollback()` all the changes that took place in the current transaction. We should call this method on a `Connection`
whose `autoCommit` mode is turned off. A popular pattern is to throw whatever exception inside the transaction to the
caller to process it.