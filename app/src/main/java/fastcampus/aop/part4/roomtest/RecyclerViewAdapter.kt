package fastcampus.aop.part4.roomtest

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import fastcampus.aop.part4.roomtest.databinding.MemoListItemBinding

interface OnItemClickListener{
    fun onItemClick(data: ArrayList<String>)
}

class RecyclerViewAdapter(private val context: Context) :
    RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder>() , OnItemClickListener {


    private var listener : OnItemClickListener? = null

    fun setOnItemClickListener(listener : OnItemClickListener) {
        this.listener = listener
    }

    private var userList = emptyList<User>()
    private val array = arrayListOf<String>()
    var checkListOn = false

    class MyViewHolder(val binding: MemoListItemBinding) : RecyclerView.ViewHolder(binding.root)

    // 어떤 xml 으로 뷰 홀더를 생성할지 지정
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding =
            MemoListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    // 뷰 홀더에 데이터를 바인딩
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = userList[position]

        if(currentItem.name.equals("")){
            holder.binding.memoTitle.text = "제목없음"
        }
        else{
            holder.binding.memoTitle.text = currentItem.name
        }

            holder.itemView.setOnClickListener {

                if(checkListOn){
                    holder.binding.layout.isSelected = !holder.binding.layout.isSelected
                    if(holder.binding.layout.isSelected){
                        holder.binding.layout.setBackgroundResource(R.drawable.shape_drawable_select)
                        array.add(currentItem.name)

                    }
                    else{
                        holder.binding.layout.setBackgroundResource(R.drawable.shape_drawable)
                        array.remove(currentItem.name)
                    }

                    Log.d("main", "onBindViewHolder: $array")

                    Log.d("ddd", "onBindViewHolder: ${holder.itemView.isSelected}")
                }
                else{
                    Intent(context, MemoDetailActivity::class.java).apply {
                        putExtra("data", currentItem)
                        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    }.run { context.startActivity(this) }
                }

//                clickEvent(currentItem)

        }
        if(!checkListOn){
            holder.binding.layout.setBackgroundResource(R.drawable.shape_drawable)
            holder.binding.layout.isSelected = false
            array.removeAll(array)
        }

    }


// 뷰 홀더의 개수 리턴
override fun getItemCount(): Int {
    return userList.size
}

// 유저 리스트 갱신
fun setData(user: List<User>) {
    userList = user
    notifyDataSetChanged()
}

    fun clickEvent() {

        Log.d("dsdsd", "체크리스트 결과 : $checkListOn")

        notifyDataSetChanged()

    }

    fun delete(){
        listener?.onItemClick(array)

    }

    override fun onItemClick(data: ArrayList<String>) {

//        listener?.onItemClick(array)

    }

//    override fun onClick(v: View?) {
//        if(v !=null){
//            when(checkListOn){
//                true ->  Intent(context, MemoDetailActivity::class.java).apply {
//                    putExtra("data", userList)
//                    addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
//                }.run { context.startActivity(this) }
//
//                false -> Toast.makeText(context,"체크리스트 해제해주세요",Toast.LENGTH_SHORT).show()
//
//
//            }
//
//
//        }
//
//
//    }
}