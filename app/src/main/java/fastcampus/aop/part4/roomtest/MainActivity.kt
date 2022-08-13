package fastcampus.aop.part4.roomtest

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import fastcampus.aop.part4.roomtest.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var db: UserDatabase
    private lateinit var userViewModel: UserViewmodel

    private var mBinding: ActivityMainBinding? = null

    private val binding get() = mBinding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = UserDatabase.getInstance(applicationContext)!!

        val layoutManager = LinearLayoutManager(this)
        val adapter = RecyclerViewAdapter(this)
        binding.recyclerView.layoutManager = layoutManager

        CoroutineScope(Dispatchers.IO).launch { // 다른애 한테 일 시키기
            adapter.setData(db.userDao().getAll())
            binding.recyclerView.adapter = adapter
        }

        binding.choice.setOnClickListener {
            adapter.checkListOn = binding.choice.isChecked

            if(!adapter.checkListOn){
                adapter.clickEvent()
            }
        }

//        adapter.setOnItemClickListener(this)

        binding.writing.setOnClickListener {
            adapter.checkListOn = false
            adapter.clickEvent()

            binding.choice.isChecked = false


            val intent = Intent(this, MemoDetailActivity::class.java)
            intent.putExtra("check","ok")
            startActivity(intent)

        }

        binding.delete.setOnClickListener {

//            Toast.makeText(this,"삭제버튼 눌림",Toast.LENGTH_SHORT).show()

//            adapter.setOnItemClickListener(this)

            adapter.setOnItemClickListener(object : OnItemClickListener{
                override fun onItemClick(data: ArrayList<String>) {
                    Log.d("ㅇㄴㅇㄴ", "onItemClick: 뺴앰 $data")


                        CoroutineScope(Dispatchers.IO).launch {
                            // 다른애 한테 일 시키기
                            for(i:String in data) {

                                db!!.userDao().deleteUserByName(i)

                            }

                        }



                }

            })


                adapter.checkListOn = false
                adapter.clickEvent()

                binding.choice.isChecked = false

//            adapter.delete()
            adapter.delete()



        }


//        buttonClick.setOnClickListener {
//
//            var newUser = User(text1.text.toString(), text2.text.toString())
//
//            CoroutineScope(Dispatchers.IO).launch {
//                db!!.userDao().insert(newUser)
//            }
//        }

        userViewModel = ViewModelProvider(
            this,
            UserViewmodel.Factory(application)
        ).get(UserViewmodel::class.java)

        userViewModel.readAllData.observe(this, Observer {
            adapter.setData(it)
        })

//        deleteButtonClick.setOnClickListener {
//
//            CoroutineScope(Dispatchers.IO).launch { // 다른애 한테 일 시키기
//                db!!.userDao().deleteUserByName(text1.text.toString())
//                Log.d("test", "${db.userDao().getAll()}")
//            }
//        }
    }

    override fun onDestroy() {
        // onDestroy 에서 binding class 인스턴스 참조를 정리해주어야 한다.
        mBinding = null

        super.onDestroy()
    }

//    override fun onItemClick(data: ArrayList<String>) {
//        TODO("Not yet implemented")
//    }

//    override fun onItemClick(data: ArrayList<String>) {
//
//        Log.d("ㅇㄴㅇㄴ", "onItemClick: 시밸래매 ")
//    }


}