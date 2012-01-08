package test

import org.specs2.mutable._

import play.api.test._
import play.api.test.Helpers._

object ModelsSpec extends Specification {
  import models._

  "User" should {
    "1件登録したのち、1件取得される。" in {
      running(FakeApplication(additionalConfiguration = inMemoryDatabase())) {
        val count = User.insert(
          User(email = "test@example.com", password = "hogehoge", fullname = "Joe", isAdmin = true)
        )
        count must equalTo(1)
        val Some(result) = User.findById(1)
        result.email must equalTo("test@example.com")
        result.password must equalTo("hogehoge")
        result.fullname must equalTo("Joe")
        result.isAdmin must equalTo(true)
      }
    }
    
    "複数件登録したのち、すべて取得される。" in {
      running(FakeApplication(additionalConfiguration = inMemoryDatabase())) {
        User.insert(
          User(email = "sample@example.com", password = "fuga", fullname = "Alice", isAdmin = false)
        )
        User.insert(
          User(email = "test@example.com", password = "hogehoge", fullname = "Joe", isAdmin = true)
        )
        val users = User.findAll
        users.length must equalTo(2)
        users(0).email must equalTo("sample@example.com")
        users(0).password must equalTo("fuga")
        users(0).fullname must equalTo("Alice")
        users(0).isAdmin must equalTo(false)
        users(1).email must equalTo("test@example.com")
        users(1).password must equalTo("hogehoge")
        users(1).fullname must equalTo("Joe")
        users(1).isAdmin must equalTo(true)
      }
    }
    
    "登録されているデータがないので、0件取得される。" in {
      running(FakeApplication(additionalConfiguration = inMemoryDatabase())) {
        User.findById(1) must equalTo(None)
      }
    }
    
  }
}

