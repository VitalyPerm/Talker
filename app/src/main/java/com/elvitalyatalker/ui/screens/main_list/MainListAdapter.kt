package com.elvitalyatalker.ui.screens.main_list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.elvitalyatalker.R
import com.elvitalyatalker.models.CommonModel
import com.elvitalyatalker.ui.screens.single_chat.SingleChatFragment
import com.elvitalyatalker.utilits.downloadAndSetImage
import com.elvitalyatalker.utilits.replaceFragment
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.main_list_item.view.*

class MainListAdapter : RecyclerView.Adapter<MainListAdapter.MainListHolder>() {
       private var listItems = mutableListOf<CommonModel>()

    class MainListHolder(view: View): RecyclerView.ViewHolder(view){
        val itemName:TextView = view.main_list_item_name
        val itemLastMessage:TextView = view.main_list_last_message
        val itemPhoto:CircleImageView = view.main_list_item_photo
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainListHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.main_list_item, parent, false)
        val holder = MainListHolder(view)
        holder.itemView.setOnClickListener {
            replaceFragment(SingleChatFragment(listItems[holder.adapterPosition]))
        }

        return holder
    }

    override fun onBindViewHolder(holder: MainListHolder, position: Int) {
       holder.itemName.text = listItems[position].fullname
        holder.itemLastMessage.text = listItems[position].lastMessage
        holder.itemPhoto.downloadAndSetImage(listItems[position].photoUrl)
    }

    override fun getItemCount(): Int = listItems.size

    fun updateListItems(item:CommonModel){
        listItems.add(item)
        notifyItemInserted(listItems.size)
    }
}