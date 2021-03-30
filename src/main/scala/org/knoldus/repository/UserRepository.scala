package org.knoldus.repository

import org.knoldus.model.User

import java.util.UUID
import scala.collection.mutable.ListBuffer

// Implementation of UserRepository class having methods to perform the CRUD operations
class UserRepository extends Dao[User] {

  //created a ListBuffer(which is a mutable data structure) for storing User Objects
  private val usersList:ListBuffer[User]= ListBuffer.empty[User]

  override def create(user: User):Option[UUID]={
    val userID = UUID.randomUUID()
    user match {
      case User(None, _, _,_) => usersList.append(user.copy(id=Some(userID))); usersList.last.id
      case User(Some(_), _, _,_) => None
    }
  }

  override def getByID(id:Option[UUID]):User = {
    val filteredList = usersList.filter(user => {user.id == id})
    val retrievdUser = filteredList.head
    retrievdUser
  }

  override def update(id:Option[UUID], updatedUser: User):Boolean= {
    val indexOfUser = usersList.indexOf(getByID(id))
    usersList.update(indexOfUser, updatedUser)
    true
  }

  override def delete(id:Option[UUID]):Boolean= {
    val indexOfUser = usersList.indexOf(getByID(id))
    usersList.remove(indexOfUser)
    true
  }

  override def getAll:List[User] = usersList.toList
}
