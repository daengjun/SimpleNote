package fastcampus.aop.part4.roomtest

import android.app.Application
import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class UserViewmodel(application: Application) : AndroidViewModel(application) {

    val readAllData : LiveData<List<User>>
    private val repository : UserRepository

    init {
        val userDao = UserDatabase.getInstance(application)!!.userDao()
        repository = UserRepository(userDao)
        readAllData = repository.readAllData
    }

    fun addUser(user : User){
        viewModelScope.launch(Dispatchers.IO) {
            repository.addUser(user)
        }
    }

    // ViewModel에 파라미터를 넘기기 위해서, 파라미터를 포함한 Factory 객체를 생성하기 위한 클래스
    class Factory(val application: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return UserViewmodel(application) as T
        }
    }
}