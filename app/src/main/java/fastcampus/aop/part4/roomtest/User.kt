package fastcampus.aop.part4.roomtest

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User (
    var name: String,
    var Content: String
){
    @PrimaryKey(autoGenerate = true) var id: Int = 0
}
