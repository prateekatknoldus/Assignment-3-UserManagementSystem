package org.knoldus.repository

import java.util.UUID

//Creating a Data Access Object(Dao) trait which contains the abstract methods for performing the CRUD operations on Database
trait Dao[T] {

  def create(entity: T): Option[UUID]

  def getByID(id: Option[UUID]): T

  def update(id: Option[UUID], updatedEntity: T):Boolean

  def delete(id: Option[UUID]): Boolean

  def getAll: List[T]

}
