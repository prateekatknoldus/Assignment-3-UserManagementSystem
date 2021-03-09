package org.knoldus.db

import org.knoldus.dao.Dao
import org.knoldus.model.User
import java.util.UUID
import scala.collection.mutable.ListBuffer

// Implementation of UserRepository class having methods to perform the CRUD operations
class UserDatabase extends Dao[User] {

  //created a ListBuffer(which is a mutable data structure) for storing User Objects
  private val usersList:ListBuffer[User]= ListBuffer.empty[User]

  // method to create a user and to store it in the usersList
  override def create(user: User):Option[UUID]={
    val userID = UUID.randomUUID()
    user match {
      case User(None, _, _, _,_) => usersList.append(user.copy(id=Some(userID))); usersList.last.id
      case User(Some(_), _, _, _,_) => null
    }
  }

  // method to get the user based on its id
  override def getByID(id:Option[UUID]):User = {
    val filteredList = usersList.filter(user => {user.id == id})
    if(filteredList.isEmpty)
      {
        throw new NoSuchElementException("User does not exist")
      }
    else
      {
        filteredList.head
      }
  }

  // method to update an user based on its id
  override def update(id:Option[UUID], updatedUser: User):Unit= {
    val indexOfUser = usersList.indexOf(getByID(id))
    usersList.update(indexOfUser,updatedUser)
  }

  //method to delete an user based on its id
  override def delete(id:Option[UUID]):Unit= {
    val indexOfUser = usersList.indexOf(getByID(id))
    usersList.remove(indexOfUser)
  }

  // method to get all the users
  override def getAll:List[User] = usersList.toList
}
