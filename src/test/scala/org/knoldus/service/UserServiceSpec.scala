package org.knoldus.service

import org.knoldus.model.{Type, User}
import org.knoldus.repository.UserRepository
import org.mockito.MockitoSugar.{mock, when}
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import java.util.UUID
import org.scalatest.OptionValues
import org.scalatest.concurrent.ScalaFutures.whenReady

// class having Unit Test Cases for UserService class
class UserServiceSpec extends AnyFlatSpec with Matchers with OptionValues {
  val mockedUserRepository: UserRepository = mock[UserRepository]
  val userService = new UserService(mockedUserRepository)

  // Unit Test Cases for createUser method
  behavior of "createUser method"

  it should "create a user and returns its ID if the ID is not provided by the user itself" in{
    val newUser:User = User(None,"prateek", "12345", Type.Customer)
    when(mockedUserRepository.create(newUser)) thenReturn Some(UUID.randomUUID())

    val newUserIdFuture = userService.createUser(newUser)
    whenReady(newUserIdFuture){ newUserId =>
      assert(newUserId.isDefined)
    }
  }

  it should "not create a user if the ID has been provided by the user itself " in{
    val newUser:User = User(Some(UUID.randomUUID()),"shubham", "54321", Type.Admin)
    when(mockedUserRepository.create(newUser)) thenReturn None

    val newUserIdFuture = userService.createUser(newUser)
    whenReady(newUserIdFuture) { newUserId =>
      assert(newUserId.isEmpty)
    }

  }

  // Unit Test Cases for getUserByID method
  behavior of "getUserByID method"

  it should "return the user if the given user ID exists" in{
    val newUser = User(None,"prateek", "12345", Type.Customer)
    val newUserId = UUID.randomUUID()
    when(mockedUserRepository.getByID(Some(newUserId))) thenReturn newUser.copy(id = Some(newUserId))

    val retrievedUserFuture = userService.getUserByID(Some(newUserId))
    whenReady(retrievedUserFuture){ retrievedUser=>
      retrievedUser.id should contain(newUserId)
    }
  }

  it should "throw NoSuchElementException if the given user ID does not exists" in{
    val randomId = Some(UUID.randomUUID())
    when(mockedUserRepository.getByID(randomId)) thenThrow new NoSuchElementException()

    whenReady(userService.getUserByID(randomId).failed){ exception =>
      exception shouldBe a[NoSuchElementException]
    }
  }

  // Unit Test Cases for updateUser method
  behavior of "updateUser method"

  it should "return true if it successfully updated a user corresponding to the given user id" in{
    val userId = Some(UUID.randomUUID())
    val updatedUser = User(Some(UUID.randomUUID()),"Rahul", "12345", Type.Customer)

    when(mockedUserRepository.update(userId,updatedUser)) thenReturn true

    whenReady(userService.updateUser(userId,updatedUser)){ isUserUpdated =>
      assert(isUserUpdated)
    }
  }

  it should "throw a NoSuchElementException if the given user ID does not exists" in{
    val randomId = Some(UUID.randomUUID())
    val updatedUser = User(Some(UUID.randomUUID()),"shubham", "54321", Type.Admin)
    when(mockedUserRepository.update(randomId,updatedUser)) thenThrow new NoSuchElementException()

    whenReady(userService.updateUser(randomId,updatedUser).failed){ exception =>
      exception shouldBe a[NoSuchElementException]
    }
  }


  // Unit Test Cases for deleteUser method
  behavior of "deleteUser method"

  it should "return true if it successfully deleted a user corresponding to the given user id" in{
    val userId = Some(UUID.randomUUID())
    when(mockedUserRepository.delete(userId)) thenReturn true

    whenReady(userService.deleteUser(userId)){ isUserDeleted =>
      assert(isUserDeleted)
    }
  }

  it should "throw a NoSuchElementException if the given user ID does not exists" in{
    val randomId = Some(UUID.randomUUID())
    when(mockedUserRepository.delete(randomId)) thenThrow new NoSuchElementException()

    whenReady(userService.deleteUser(randomId).failed){ exception =>
      exception shouldBe a[NoSuchElementException]
    }
  }

  // Unit Test Cases for getAllUsers method
  behavior of "getAllUsers method"

  it should "return a list of all the users" in{
    when(mockedUserRepository.getAll) thenReturn List(User(Some(UUID.randomUUID()),"shubham", "54321", Type.Admin),User(Some(UUID.randomUUID()),"prateek", "3021", Type.Customer))

    val usersListFuture = userService.getAllUsers

    whenReady(usersListFuture){ usersList =>
      assert(usersList.nonEmpty)
    }
  }

  it should "return an empty list if no users exits in database" in{
    when(mockedUserRepository.getAll) thenReturn List.empty

    val usersListFuture = userService.getAllUsers

    whenReady(usersListFuture){ usersList =>
      assert(usersList.isEmpty)
    }
  }

}
