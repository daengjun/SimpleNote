package fastcampus.aop.part4.roomtest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import fastcampus.aop.part4.roomtest.databinding.ActivityMainBinding
import fastcampus.aop.part4.roomtest.databinding.ActivityWritingBinding

class WritingActivity : AppCompatActivity() {

    private var mBinding: ActivityWritingBinding? = null

    private val binding get() = mBinding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityWritingBinding.inflate(layoutInflater)
        setContentView(binding.root)

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