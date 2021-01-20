//package com.superpromo.superpromo.ui.shopping.list_archived
//
//import android.view.LayoutInflater
//import android.view.ViewGroup
//import androidx.recyclerview.widget.DiffUtil
//import androidx.recyclerview.widget.ListAdapter
//import androidx.recyclerview.widget.RecyclerView
//import com.artf.shoppinglist.database.ShoppingList
//import com.artf.shoppinglist.databinding.ItemArchivedShoppingListBinding
//
//class ShoppingArchivedListAdapter(
//    private val clickListenerInt: ClickListenerInt
//) : ListAdapter<ShoppingList,
//        RecyclerView.ViewHolder>(GridViewDiffCallback) {
//
//    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
//        val product = getItem(position)
//        (holder as MsgViewHolder).bind(clickListenerInt, product)
//    }
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MsgViewHolder {
//        return MsgViewHolder(
//            ItemArchivedShoppingListBinding.inflate(
//                LayoutInflater.from(parent.context), parent, false
//            )
//        )
//    }
//
//    class MsgViewHolder constructor(private val binding: ItemArchivedShoppingListBinding) :
//        RecyclerView.ViewHolder(binding.root) {
//
//        fun bind(clickListenerInt: ClickListenerInt, item: ShoppingList) {
//            binding.item = item
//            binding.clickListenerInt = clickListenerInt
//            binding.executePendingBindings()
//        }
//    }
//
//    companion object GridViewDiffCallback : DiffUtil.ItemCallback<ShoppingList>() {
//        override fun areItemsTheSame(oldItem: ShoppingList, newItem: ShoppingList): Boolean {
//            return oldItem.id == newItem.id
//        }
//
//        override fun areContentsTheSame(oldItem: ShoppingList, newItem: ShoppingList): Boolean {
//            return oldItem == newItem
//        }
//    }
//
//    interface ClickListenerInt {
//        fun onClickListenerRow(shoppingList: ShoppingList)
//        fun onClickListenerButton(shoppingList: ShoppingList)
//    }
//}