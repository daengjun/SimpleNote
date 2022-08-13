package fastcampus.aop.part4.roomtest

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
data class User(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    var name: String,
    var Content: String
) : Serializable {

}
