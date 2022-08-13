package fastcampus.aop.part4.roomtest

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import fastcampus.aop.part4.roomtest.databinding.ActivityMemoDetailBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MemoDetailActivity : AppCompatActivity() {

    lateinit var data: User

    private var mBinding: ActivityMemoDetailBinding? = null

    private val binding get() = mBinding!!

    private lateinit var check: String

    private lateinit var title: String
    private lateinit var content: String
    private lateinit var id: String
    private var checkIntent = true
//    private var checkSave = false

    private lateinit var userViewModel: UserViewmodel

    private lateinit var db: UserDatabase

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityMemoDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        userViewModel = ViewModelProvider(
            this,
            UserViewmodel.Factory(application)
        ).get(UserViewmodel::class.java)

        db = UserDatabase.getInstance(applicationContext)!!

        check = intent.getStringExtra("check").toString()

        if (!intent.hasExtra("check")) {
            data = intent.getSerializableExtra("data") as User
            checkIntent = false
//            checkSave = false

            binding.saveLayout.isVisible = false
            binding.editLayout.isVisible = true

            title = data.name
            content = data.Content
            id = data.id.toString()

            binding.title.text = data.name
            binding.contentText1.text = data.Content

        } else {
            checkIntent = true
            binding.saveLayout.isVisible = true
            binding.editLayout.isVisible = false

        }


        binding.back1.setOnClickListener {
            onBackPressed()
        }

        binding.delete.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch { // 다른애 한테 일 시키기
                db!!.userDao().deleteUserByName(data.name)
                finish()
            }
        }

        binding.back.setOnClickListener {
            binding.editLayout.isVisible = true
            binding.saveLayout.isVisible = false

        }

        binding.edit.setOnClickListener {

            binding.saveLayout.isVisible = true
            binding.editLayout.isVisible = false


//            if (checkSave) {
//                binding.titleText.setText(binding.titleText.text.toString())
//            } else {
//                binding.titleText.setText(data.name)
//
//            }

            binding.titleText.setText(binding.title.text.toString())
            binding.contentText.setText(binding.contentText1.text.toString())


        }




        binding.save.setOnClickListener {

            if (binding.titleText.text.isEmpty() && binding.contentText.text.isEmpty()) {
                Toast.makeText(baseContext, "빈 문서는 저장 할 수 없습니다", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            } else if (!checkIntent) {
                var newUser =
                    User(
                        id.toInt(),
                        binding.titleText.text.toString(),
                        binding.contentText.text.toString()
                    )

                userViewModel.updateUser(newUser)

                binding.editLayout.isVisible = true
                binding.saveLayout.isVisible = false

                binding.title.text = binding.titleText.text.toString()
                binding.contentText1.text = binding.contentText.text.toString()
//                checkSave = true


            } else {
                var newUser =
                    User(0, binding.titleText.text.toString(), binding.contentText.text.toString())

                CoroutineScope(Dispatchers.IO).launch {
                    db!!.userDao().insert(newUser)
                }

                binding.editLayout.isVisible = true
                binding.saveLayout.isVisible = false

                binding.title.text = binding.titleText.text.toString()
                binding.contentText1.text = binding.contentText.text.toString()
//                checkSave = true

            }


        }

    }


    override fun onDestroy() {
        mBinding = null
        super.onDestroy()
    }

}



