package org.knoldus.model

import java.util.UUID

// Creating User class
case class User(id:Option[UUID], username:String, password:String, email:String, _type:Type)