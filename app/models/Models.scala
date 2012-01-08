package models

import play.api.db._
import play.api.Play.current

import anorm._
import anorm.SqlParser._

case class User(
  id: Pk[Long] = NotAssigned, 
  email: String, password: String, fullname: String, isAdmin: Boolean)

object User {

  val simple = {
    get[Pk[Long]]("user.id") ~
    get[String]("user.email") ~
    get[String]("user.password") ~
    get[String]("user.fullname") ~
    get[Boolean]("user.isAdmin") map {
      case id ~ email ~ password ~ fullname ~ isAdmin => User(id, email, password, fullname, isAdmin)
    }
  }

  def insert(user: User) = {
    DB.withConnection { implicit connection =>
      SQL(
        """
          insert into user(email, password, fullname, isAdmin) 
          values ({email}, {password}, {fullname}, {isAdmin})
        """
      ).on(
        'email -> user.email, 
        'password -> user.password, 
        'fullname -> user.fullname, 
        'isAdmin -> user.isAdmin
      ).executeUpdate()
    }
  }

  def findById(id: Long): Option[User] = {
    DB.withConnection { implicit connection =>
      SQL("select * from user where id = {id}").on('id -> id).as(User.simple.singleOpt)
    }
  }

  def findAll(): Seq[User] = {
    DB.withConnection { implicit connection =>
      SQL("select * from user").as(User.simple *) 
    }
  }

  def last(): Option[User] = {
    DB.withConnection { implicit connection =>
      SQL("select * from user order by id asc").as(User.simple.singleOpt)
    }
  }

}

