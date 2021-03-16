package org.knoldus.service

import org.knoldus.model.{Type, User}
import org.knoldus.repository.UserRepository
import org.mockito.MockitoSugar.{mock, when}
import org.scalatest.flatspec.AnyFlatSpec

import java.util.UUID

// class having Unit Test Cases for UserService class
class UserServiceSpec extends AnyFlatSpec{

  // Unit Test Cases for createUser method
  behavior of "createUser method"

  it should "create a user and returns its ID if the ID is not provided by the user itself" in{
    val mockedUserRepository = mock[UserRepository]
    val newUser:User = User(None,"prateek", "12345", Type.Customer)
    when(mockedUserRepository.create(newUser)) thenReturn Some(UUID.randomUUID())

    val userService = new UserService(mockedUserRepository)
    val newUserId = userService.createUser(newUser)
    assert(newUserId.isDefined)
  }

  it should "not create a user if the ID has been provided by the user itself " in{
    val mockedUserRepository = mock[UserRepository]
    val newUser:User = User(Some(UUID.randomUUID()),"shubham", "54321", Type.Admin)
    when(mockedUserRepository.create(newUser)) thenReturn None

    val userService = new UserService(mockedUserRepository)
    val newUserId = userService.createUser(newUser)
    assert(newUserId.isEmpty)
  }

  // Unit Test Cases for getUserByID method
  behavior of "getUserByID method"

  it should "return the user if the given user ID exists" in{
    val mockedUserRepository = mock[UserRepository]
    val newUser = User(None,"prateek", "12345", Type.Customer)
    when(mockedUserRepository.create(newUser)) thenReturn Some(UUID.randomUUID())

    val userService = new UserService(mockedUserRepository)
    val newUserID = userService.createUser(newUser)

    when(mockedUserRepository.getByID(newUserID)) thenReturn newUser.copy(id = newUserID)

    val retrievedUser = userService.getUserByID(newUserID)
    assert(retrievedUser.id == newUserID)
  }

  it should "throw NoSuchElementException if the given user ID does not exists" in{
    val mockedUserRepository = mock[UserRepository]
    val userService = new UserService(mockedUserRepository)
    val randomId = Some(UUID.randomUUID())
    when(mockedUserRepository.getByID(randomId)) thenThrow new NoSuchElementException()

    assertThrows[NoSuchElementException]{
      userService.getUserByID(randomId)
    }
  }

  // Unit Test Cases for updateUser method
  behavior of "updateUser method"

  it should "return true if it successfully updated a user corresponding to the given user id" in{
    val mockedUserRepository = mock[UserRepository]
    val newUser = User(None,"prateek", "12345", Type.Customer)
    when(mockedUserRepository.create(newUser)) thenReturn Some(UUID.randomUUID())
    val userService = new UserService(mockedUserRepository)
    val newUserID = userService.createUser(newUser)
    when(mockedUserRepository.getByID(newUserID)) thenReturn newUser.copy(id = newUserID)

    val updatedUser = userService.getUserByID(newUserID).copy(username="chaitanya")
    when(mockedUserRepository.update(newUserID,updatedUser)) thenReturn true

    val isUserUpdated = userService.updateUser(newUserID,updatedUser)
    assert(isUserUpdated)
  }

  it should "throw a NoSuchElementException if the given user ID does not exists" in{
    val mockedUserRepository = mock[UserRepository]
    val userService = new UserService(mockedUserRepository)
    val randomId = Some(UUID.randomUUID())
    val updatedUser = User(Some(UUID.randomUUID()),"shubham", "54321", Type.Admin)
    when(mockedUserRepository.update(randomId,updatedUser)) thenThrow new NoSuchElementException()

    assertThrows[NoSuchElementException]{
      userService.updateUser(randomId,updatedUser)
    }
  }

  // Unit Test Cases for deleteUser method
  behavior of "deleteUser method"

  it should "return true if it successfully deleted a user corresponding to the given user id" in{
    val mockedUserRepository = mock[UserRepository]
    val newUser = User(None,"prateek", "12345", Type.Customer)
    when(mockedUserRepository.create(newUser)) thenReturn Some(UUID.randomUUID())
    val userService = new UserService(mockedUserRepository)
    val newUserID = userService.createUser(newUser)

    when(mockedUserRepository.delete(newUserID)) thenReturn true

    val isUserDeleted = userService.deleteUser(newUserID)
    assert(isUserDeleted)
  }

  it should "throw a NoSuchElementException if the given user ID does not exists" in{
    val mockedUserRepository = mock[UserRepository]
    val userService = new UserService(mockedUserRepository)
    val randomId = Some(UUID.randomUUID())
    when(mockedUserRepository.delete(randomId)) thenThrow new NoSuchElementException()

    assertThrows[NoSuchElementException]{
      userService.deleteUser(randomId)
    }
  }

  // Unit Test Cases for getAllUsers method
  behavior of "getAllUsers method"

  it should "return a list of all the users" in{
    val mockedUserRepository = mock[UserRepository]
    val userService = new UserService(mockedUserRepository)
    when(mockedUserRepository.getAll) thenReturn List(User(Some(UUID.randomUUID()),"shubham", "54321", Type.Admin),User(Some(UUID.randomUUID()),"prateek", "3021", Type.Customer))

    assert(userService.getAllUsers.nonEmpty)
  }

  it should "return an empty list if no users exits in database" in{
    val mockedUserRepository = mock[UserRepository]
    val userService = new UserService(mockedUserRepository)
    when(mockedUserRepository.getAll) thenReturn List.empty

    assert(userService.getAllUsers.isEmpty)
  }

}
