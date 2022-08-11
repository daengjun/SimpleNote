package fastcampus.aop.part4.roomtest

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface UserDao {
    @Insert
    fun insert(user: User)

    @Update
    fun update(user: User)

    @Delete
    fun delete(user: User)

    @Query("SELECT * FROM User")
    fun getAll(): List<User>


    @Query("DELETE FROM User WHERE name = :name")
    fun deleteUserByName(name: String)

    @Query("SELECT * FROM User ORDER BY id DESC")
    fun readAllData() : LiveData<List<User>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addUser(user : User)
}