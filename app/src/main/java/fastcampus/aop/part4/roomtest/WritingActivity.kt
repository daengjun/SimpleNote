package fastcampus.aop.part4.roomtest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import fastcampus.aop.part4.roomtest.databinding.ActivityMainBinding
import fastcampus.aop.part4.roomtest.databinding.ActivityWritingBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class WritingActivity : AppCompatActivity() {

    private var mBinding: ActivityWritingBinding? = null
    private lateinit var db: UserDatabase

    private val binding get() = mBinding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityWritingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = UserDatabase.getInstance(applicationContext)!!

        binding.back.setOnClickListener {
            onBackPressed()
        }

        binding.save.setOnClickListener {

            if(binding.titleText.text.isEmpty()&&binding.contentText.text.isEmpty()){
                Toast.makeText(baseContext,"빈 문서는 저장 할 수 없습니다",Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            var newUser = User(binding.titleText.text.toString(), binding.contentText.text.toString())

            CoroutineScope(Dispatchers.IO).launch {
                db!!.userDao().insert(newUser)
                finish()
            }
        }

    }

    override fun onDestroy() {
        // onDestroy 에서 binding class 인스턴스 참조를 정리해주어야 한다.
        mBinding = null
        super.onDestroy()
    }

}