package fastcampus.aop.part4.roomtest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import fastcampus.aop.part4.roomtest.databinding.ActivityWritingBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class WritingActivity : AppCompatActivity() {

    private lateinit var userViewModel: UserViewmodel

//    private var currentId : Int? =null

    private var mBinding: ActivityWritingBinding? = null
    private lateinit var db: UserDatabase

    private val binding get() = mBinding!!
    private var checkIntent = false
    private lateinit var title: String
    private lateinit var content: String
    private lateinit var id: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityWritingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        userViewModel = ViewModelProvider(
            this,
            UserViewmodel.Factory(application)
        ).get(UserViewmodel::class.java)

        if (intent.hasExtra("title")) {
            title = intent.getStringExtra("title").toString()
            content = intent.getStringExtra("content").toString()
            id = intent.getStringExtra("key").toString()

            Log.d("dsds", "onCreate: ${id}")
            binding.titleText.setText(title)
            binding.contentText.setText(content)
            checkIntent = true

        }

        db = UserDatabase.getInstance(applicationContext)!!

        binding.back.setOnClickListener {
            onBackPressed()
        }

        binding.save.setOnClickListener {

            if (binding.titleText.text.isEmpty() && binding.contentText.text.isEmpty()) {
                Toast.makeText(baseContext, "빈 문서는 저장 할 수 없습니다", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            } else if (checkIntent) {
                var newUser =
                    User(
                        id.toInt(),
                        binding.titleText.text.toString(),
                        binding.contentText.text.toString()
                    )

                userViewModel.updateUser(newUser)
                finish()


            } else {
                var newUser =
                    User(0, binding.titleText.text.toString(), binding.contentText.text.toString())

                CoroutineScope(Dispatchers.IO).launch {
                    db!!.userDao().insert(newUser)
                    finish()
                }
            }


        }

    }

    override fun onDestroy() {
        // onDestroy 에서 binding class 인스턴스 참조를 정리해주어야 한다.
        mBinding = null
        checkIntent = false
        super.onDestroy()
    }

}
