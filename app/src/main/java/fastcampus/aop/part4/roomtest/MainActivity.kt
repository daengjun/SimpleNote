package fastcampus.aop.part4.roomtest

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import fastcampus.aop.part4.roomtest.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var contexts: Context
    private lateinit var db: UserDatabase
    private lateinit var userViewModel: UserViewmodel

    private var mBinding: ActivityMainBinding? = null

    private val binding get() = mBinding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = UserDatabase.getInstance(applicationContext)!!

        contexts = binding.delete.context

        val layoutManager = LinearLayoutManager(this)
        val adapter = RecyclerViewAdapter(this)
        binding.recyclerView.layoutManager = layoutManager

        CoroutineScope(Dispatchers.IO).launch { // 다른애 한테 일 시키기
            adapter.setData(db.userDao().getAll())
            binding.recyclerView.adapter = adapter
        }

        binding.choice.setOnClickListener {

            adapter.checkListOn = binding.choice.isChecked

            if (!adapter.checkListOn) {
                adapter.clickEvent()
            }
        }

        binding.writing.setOnClickListener {
            adapter.checkListOn = false
            adapter.clickEvent()

            binding.choice.isChecked = false


            val intent = Intent(this, MemoDetailActivity::class.java)
            intent.putExtra("check", "ok")
            startActivity(intent)

        }

        binding.delete.setOnClickListener {

            adapter.setOnItemClickListener(object : OnItemClickListener {
                override fun onItemClick(data: ArrayList<String>) {

                    if(data.size > 0){

                        val builder = AlertDialog.Builder(contexts)
                        builder.setTitle("${data.size}개의 메모를 삭제 하시겠습니까?")
                            .setMessage("$data")
                            .setNegativeButton("취소",
                                DialogInterface.OnClickListener { dialog, id ->

                                })
                            .setPositiveButton("확인",
                                DialogInterface.OnClickListener { dialog, id ->

                                    CoroutineScope(Dispatchers.IO).launch {
                                        // 다른애 한테 일 시키기
                                        for (i: String in data) {

                                            db!!.userDao().deleteUserByName(i)
                                        }
                                    }

                                    adapter.checkListOn = false
                                    adapter.clickEvent()

                                    binding.choice.isChecked = false

                                })
                        builder.show()

                    }



                }

            })


            adapter.delete()

        }

        userViewModel = ViewModelProvider(
            this,
            UserViewmodel.Factory(application)
        ).get(UserViewmodel::class.java)

        userViewModel.readAllData.observe(this, Observer {
            adapter.setData(it)
        })

    }

    override fun onDestroy() {
        // onDestroy 에서 binding class 인스턴스 참조를 정리해주어야 한다.
        mBinding = null

        super.onDestroy()
    }


}