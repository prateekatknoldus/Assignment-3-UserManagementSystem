package org.knoldus.service

import org.knoldus.repository.Dao
import org.knoldus.model.User

import java.net.UnknownServiceException
import java.util.UUID

// Implementation of UserService class to provide services to the user
class UserService(userDB: Dao[User]) {

  // method to create a user without bothering about how the user is actually created
  def createUser(user: User):Option[UUID]={
    val userId = userDB.create(user)
    if(userId.isDefined){
      userId
    }
    else {
      None
    }
  }

  // method to get the user based on its id without bothering about how the user is actually fetched from database
  def getUserByID(id:Option[UUID]):User = {
    userDB.getByID(id)
  }

  // method to update the user without bothering about how the user is actually updated
  def updateUser(id:Option[UUID], updatedUser: User):Boolean = {
    userDB.update(id,updatedUser)
  }

  //method to delete a user based on its id without bothering about how the user is actually deleted
  def deleteUser(id:Option[UUID]):Boolean = {
    userDB.delete(id)
  }

  // method to get all the users without bothering about how they are actually fetched
  def getAllUsers:List[User] = {
    val retrievedUsersList = userDB.getAll
    if(retrievedUsersList.nonEmpty){
      retrievedUsersList
    }
    else {
      List.empty
    }
  }
}
