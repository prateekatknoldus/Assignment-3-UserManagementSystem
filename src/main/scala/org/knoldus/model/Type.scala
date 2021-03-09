package org.knoldus.model

sealed trait Type

// defining the types of User
object Type{
  case object Customer extends Type
  case object Admin extends Type
}


