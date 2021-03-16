package org.knoldus.bootstrap

import org.knoldus.repository.{Dao, UserRepository}
import org.knoldus.model.{Type, User}
import org.knoldus.service.UserService

// Object for testing services of User Management System
object MainClassOfAssignment {

  // Main method
  def main(args: Array[String]): Unit = {

    val userDB:Dao[User] = new UserRepository() // instance of UserRepository is created
    val userService = new UserService(userDB) // instance of UserService is created

    // Step 1 => Creating Two Users:-Customer and Admin.
    // creating user1 which is of type Customer
    val user1 = User(None, "prateek", "12345", Type.Customer) // instance of User class is created
    val user1_id = userService.createUser(user1) // creates user1 and returns its ID

    // creating user2 which is of type Admin
    val user2 = User(None, "chaitanya", "54321", Type.Admin) // instance of User class is created
    val user2_id = userService.createUser(user2) // creates user2 and returns its ID

    // Step 2 => Listing or Getting all users
    println("List of All Users => "+userService.getAllUsers) // prints the list of all users after retrieving it from the database.

    // Step 3 => Update username of Admin
    val updated_user2= userService.getUserByID(user2_id).copy(username = "shubham") // updated the username of Admin(i.e. user2)
    userService.updateUser(user2_id,updated_user2) // applied this update in the database

    // Step 4 => Getting the updated Admin
    println("Updated User => "+userService.getUserByID(user2_id)) // prints the updated Admin(i.e. user2) details after retrieving it from the database.

    // Step 5 => Delete Customer
    userService.deleteUser(user1_id) // deleted the Customer(i.e. user1) from the database

    // Step 6- Trying to get the deleted Customer(i.e. user1) from the database. So it will throw a NoSuchElementException. To check it, uncomment the below line of code.
    //userService.getUserByID(user1_id)
  }

}