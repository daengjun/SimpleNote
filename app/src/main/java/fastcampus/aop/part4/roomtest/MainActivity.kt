package fastcampus.aop.part4.roomtest

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import androidx.room.RoomDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var db: UserDatabase
    private lateinit var userViewModel: UserViewmodel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val text2 = findViewById<TextView>(R.id.inputText)
        val text1 = findViewById<TextView>(R.id.inputName)
        val buttonClick = findViewById<Button>(R.id.inputButton)
        val deleteButtonClick = findViewById<Button>(R.id.DeleteButton)
        val recyclerView = findViewById<RecyclerView>(R.id.recycler)
        db = UserDatabase.getInstance(applicationContext)!!

        val layoutManager = LinearLayoutManager(this)
        val adapter = RecyclerViewAdapter()
        recyclerView.layoutManager = layoutManager

        CoroutineScope(Dispatchers.IO).launch { // 다른애 한테 일 시키기

            adapter.setData(db.userDao().getAll())
            recyclerView.adapter = adapter
        }

        buttonClick.setOnClickListener {

            var newUser = User(text1.text.toString(), text2.text.toString())

            CoroutineScope(Dispatchers.IO).launch {
                db!!.userDao().insert(newUser)
            }
        }

        userViewModel = ViewModelProvider(
            this,
            UserViewmodel.Factory(application)
        ).get(UserViewmodel::class.java)

        userViewModel.readAllData.observe(this, Observer {
            adapter.setData(it)
        })

        deleteButtonClick.setOnClickListener {

            CoroutineScope(Dispatchers.IO).launch { // 다른애 한테 일 시키기
                db!!.userDao().deleteUserByName(text1.text.toString())
                Log.d("test", "${db.userDao().getAll()}")
            }
        }
    }
}