package org.knoldus.model

import java.util.UUID

case class User(id:Option[UUID], username:String, password:String, _type:Type)