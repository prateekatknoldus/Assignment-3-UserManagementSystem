package org.knoldus.service

import org.knoldus.dao.Dao
import org.knoldus.model.User
import java.util.UUID

// Implementation of UserService class to provide services to the user
class UserService(userDB: Dao[User]) {

  // method to create a user without bothering about how the user is actually created
  def createUser(user: User):Option[UUID]={
    userDB.create(user)
  }

  // method to get the user based on its id without bothering about how the user is actually fetched from database
  def getUserByID(id:Option[UUID]):User = {
    userDB.getByID(id)
  }

  // method to update the user without bothering about how the user is actually updated
  def updateUser(id:Option[UUID], updatedUser: User):Unit = {
    userDB.update(id,updatedUser)
  }

  //method to delete a user based on its id without bothering about how the user is actually deleted
  def deleteUser(id:Option[UUID]):Unit = {
    userDB.delete(id)
  }

  // method to get all the users without bothering about how they are actually fetched
  def getAllUsers:List[User] = userDB.getAll
}
