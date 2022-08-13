package fastcampus.aop.part4.roomtest

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.annotation.RequiresApi
import fastcampus.aop.part4.roomtest.databinding.ActivityMemoDetailBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MemoDetailActivity : AppCompatActivity() {

    lateinit var data : User

    private var mBinding: ActivityMemoDetailBinding? = null

    private val binding get() = mBinding!!

    private lateinit var title : String
    private lateinit var content : String
    private lateinit var id : String


    private lateinit var db: UserDatabase

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityMemoDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = UserDatabase.getInstance(applicationContext)!!

         data = intent.getSerializableExtra("data") as User

        title = data.name
        content = data.Content
        Log.d("sex", "onCreate: ${data.id}")
        id = data.id.toString()
        Log.d("sex", "시발 ${id}")

        binding.contentText.text = data.Content

        binding.back.setOnClickListener {
            onBackPressed()
        }

        binding.delete.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch { // 다른애 한테 일 시키기
                db!!.userDao().deleteUserByName(data.name)
                finish()
            }
        }

        binding.edit.setOnClickListener {
            Intent(baseContext, WritingActivity::class.java).apply {
                Log.d("sex", "onCreate 아이디값 사라있냐?: ${id}")
                putExtra("key", id )
                putExtra("title", title )
                putExtra("content", content )

                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }.run { baseContext.startActivity(this) }
            finish()
        }



    }


    override fun onDestroy() {
        // onDestroy 에서 binding class 인스턴스 참조를 정리해주어야 한다.
        mBinding = null
        super.onDestroy()
    }

}



