package fastcampus.aop.part4.roomtest

import android.service.autofill.UserData
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import fastcampus.aop.part4.roomtest.databinding.MemoListItemBinding


class RecyclerViewAdapter : RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder>() {


private var userList = emptyList<User>()

class MyViewHolder(val binding: MemoListItemBinding) : RecyclerView.ViewHolder(binding.root)

// 어떤 xml 으로 뷰 홀더를 생성할지 지정
override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
    val binding = MemoListItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
    return MyViewHolder(binding)
}

// 뷰 홀더에 데이터를 바인딩
override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
    val currentItem = userList[position]
    holder.binding.memoTitle.text = currentItem.name
}

// 뷰 홀더의 개수 리턴
override fun getItemCount(): Int {
    return userList.size
}

// 유저 리스트 갱신
 fun setData(user : List<User>){
    userList = user
    notifyDataSetChanged()
}
}