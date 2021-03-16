package org.knoldus.repository

import org.knoldus.model.{Type, User}
import org.scalatest.BeforeAndAfter
import org.scalatest.flatspec.AnyFlatSpec

import java.util.UUID

// class having Unit Test Cases for UserRepository class
class UserRepositorySpec extends AnyFlatSpec with BeforeAndAfter{

  // Unit Test Cases for create method
  behavior of "create method"

  it should "create a user and returns its ID if the ID is not provided by the user itself" in{
    val userRepository = new UserRepository
    val newUser = User(None,"prateek", "12345", Type.Customer)

    val userId = userRepository.create(newUser)
    assert(userId.isDefined)
  }

  it should "not create a user if the ID has been provided by the user itself " in{
    val userRepository = new UserRepository
    val newUser = User(Some(UUID.randomUUID()),"shubham", "54321", Type.Admin)

    val userId = userRepository.create(newUser)
    assert(userId.isEmpty)
  }

  // Unit Test Cases for getByID method
  behavior of "getByID method"

  it should "return the user if the given user ID exists" in{

    val userRepository = new UserRepository
    val newUser = User(None,"prateek", "12345", Type.Customer)
    val userId = userRepository.create(newUser)

    val retrievedUser = userRepository.getByID(userId)
    assert(retrievedUser.id == userId)
  }

  it should "throw NoSuchElementException if the given user ID does not exists" in{
    val userRepository = new UserRepository
    assertThrows[NoSuchElementException]{
      userRepository.getByID(Some(UUID.randomUUID()))
    }
  }

  // Unit Test Cases for update method
  behavior of "update method"

  it should "return true if it successfully updated a user corresponding to the given user id" in{
    val userRepository = new UserRepository
    val newUser = User(None,"prateek", "12345", Type.Customer)
    val userId = userRepository.create(newUser)
    val updatedUser= userRepository.getByID(userId).copy(username = "chaitanya")

    val isUserUpdated = userRepository.update(userId,updatedUser)
    assert(isUserUpdated)
  }

  it should "throw a NoSuchElementException if the given user ID does not exists" in{
    val userRepository = new UserRepository
    val newUser = User(None,"prateek", "12345", Type.Customer)
    val userId = userRepository.create(newUser)
    val updatedUser= userRepository.getByID(userId).copy(username = "chaitanya")

    assertThrows[NoSuchElementException]{
      userRepository.update(Some(UUID.randomUUID()),updatedUser)
    }
  }

  // Unit Test Cases for delete method
  behavior of "delete method"

  it should "return true if it successfully deleted a user corresponding to the given user id" in{
    val userRepository = new UserRepository
    val newUser = User(None,"prateek", "12345", Type.Customer)
    val userId = userRepository.create(newUser)

    val isUserDeleted = userRepository.delete(userId)
    assert(isUserDeleted)
  }

  it should "throw a NoSuchElementException if the given user ID does not exists" in{
    val userRepository = new UserRepository

    assertThrows[NoSuchElementException]{
      userRepository.delete(Some(UUID.randomUUID()))
    }
  }

  // Unit Test Cases for getAll method
  behavior of "getAll method"

  it should "return a list of all the users" in{
    val userRepository = new UserRepository
    val newUser = User(None,"prateek", "12345", Type.Customer)
    val userId = userRepository.create(newUser)

    val retrievedUsersList = userRepository.getAll
    assert(retrievedUsersList == List(userRepository.getByID(userId)))
  }

  it should "return an empty list if no users exits in database" in{
    val userRepository = new UserRepository
    assert(userRepository.getAll.isEmpty)
  }

}
