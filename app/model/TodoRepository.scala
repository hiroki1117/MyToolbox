package model

import java.sql.Date

import javax.inject.{Inject, Singleton}
import play.api.db.slick.DatabaseConfigProvider
import slick.jdbc.PostgresProfile

import scala.concurrent.{ExecutionContext, Future}

@Singleton
class TodoRepository @Inject() (dbConfigProvider: DatabaseConfigProvider)(implicit ec: ExecutionContext) {

  private val dbConfig = dbConfigProvider.get[PostgresProfile]

  //DB operationをスコープに入れる
  import dbConfig._
  import profile.api._

  private class TodoTable(tag: Tag) extends Table[Todo](tag, "todo") {
    def id = column[Long]("id", O.PrimaryKey, O.AutoInc)
    def content = column[String]("content")
    def created = column[Date]("created")
    def * = (id,content,created) <> ((Todo.apply _).tupled, Todo.unapply)
  }

  private val todo = TableQuery[TodoTable]

  def create(content: String): Future[Todo] = db.run{
    (todo.map(p => (p.content))
      returning todo.map(t => (t.id, t.created))
      into ((a, b) => Todo(b._1, a, b._2))
      ) += (content)
  }

  def delete(id: Long) = db.run{
    todo.filter(_.id === id).delete
  }

  def list(): Future[Seq[Todo]] = db.run{
    todo.result
  }
}
