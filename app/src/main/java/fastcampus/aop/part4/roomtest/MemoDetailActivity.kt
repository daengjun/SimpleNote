package fastcampus.aop.part4.roomtest

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.annotation.RequiresApi
import fastcampus.aop.part4.roomtest.databinding.ActivityMemoDetailBinding

class MemoDetailActivity : AppCompatActivity() {

    lateinit var datas : User

    private var mBinding: ActivityMemoDetailBinding? = null

    private val binding get() = mBinding!!

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityMemoDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val datas = intent.getSerializableExtra("data") as User


        Log.d("dsds", "onCreate: pathData + $datas")

        binding.contentText.text = datas.Content

        binding.back.setOnClickListener {
            onBackPressed()
        }

    }


    override fun onDestroy() {
        // onDestroy 에서 binding class 인스턴스 참조를 정리해주어야 한다.
        mBinding = null
        super.onDestroy()
    }

}



