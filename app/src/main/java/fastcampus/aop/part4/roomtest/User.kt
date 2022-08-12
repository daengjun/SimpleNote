package fastcampus.aop.part4.roomtest

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
data class User  (
    var name: String,
    var Content: String
) : Serializable {
    @PrimaryKey(autoGenerate = true) var id: Int = 0
}
