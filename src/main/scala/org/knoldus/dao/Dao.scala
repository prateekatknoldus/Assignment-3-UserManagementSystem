package org.knoldus.dao

import java.util.UUID

//Creating a Data Access Object(Dao) trait which contains the abstract methods for performing the CRUD operations on Database
trait Dao[T] {

  def create(t:T):Option[UUID]

  def getByID(id:Option[UUID]): T

  def update(id:Option[UUID], t:T)

  def delete(id:Option[UUID])

  def getAll: List[T]

}
