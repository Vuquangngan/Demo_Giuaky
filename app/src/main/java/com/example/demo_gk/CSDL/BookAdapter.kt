package com.example.demo_gk.CSDL

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.demo_gk.CSDL.Book
import com.example.demo_gk.R

class BookAdapter(
    private val onItemClick: (Book) -> Unit // callback khi click item
) : ListAdapter<Book, BookAdapter.BookViewHolder>(DIFF_CALLBACK) {

    inner class BookViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvTenSach: TextView = itemView.findViewById(R.id.tvTenSach)
        private val tvTacGia: TextView = itemView.findViewById(R.id.tvTacGia)
        private val tvNgayXuatBan: TextView = itemView.findViewById(R.id.tvNgayXuatBan)
        private val tvSoTrang: TextView = itemView.findViewById(R.id.tvSotrang)

        fun bind(book: Book) {
            tvTenSach.text = book.tenSach
            tvTacGia.text = book.tacGia
            tvNgayXuatBan.text = book.ngayXuatBan
            tvSoTrang.text = book.soTrang.toString()

            itemView.setOnClickListener {
                onItemClick(book)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_book, parent, false) // Inflate đúng layout của bạn
        return BookViewHolder(view)
    }

    override fun onBindViewHolder(holder: BookViewHolder, position: Int) {
        val book = getItem(position)
        holder.bind(book)
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Book>() {
            override fun areItemsTheSame(oldItem: Book, newItem: Book): Boolean {
                return oldItem.id == newItem.id // giả sử Book có trường id
            }

            override fun areContentsTheSame(oldItem: Book, newItem: Book): Boolean {
                return oldItem == newItem
            }
        }
    }
}
