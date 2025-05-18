package com.example.demo_gk.UI


import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.demo_gk.CSDL.Book
import com.example.demo_gk.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val bookViewModel: BookViewModel by viewModels()
    private lateinit var adapter: BookAdapter
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = BookAdapter { bookClicked ->
            // Xử lý khi click vào một cuốn sách
            Toast.makeText(this, "Bạn chọn sách: ${bookClicked.tenSach}", Toast.LENGTH_SHORT).show()
            // TODO: Hiển thị view chi tiết hoặc mở Activity mới ở đây
        }

        binding.rvBooks.layoutManager = LinearLayoutManager(this)
        binding.rvBooks.adapter = adapter

        // Quan sát LiveData cập nhật danh sách sách
        bookViewModel.allBooks.observe(this, Observer { books ->
            adapter.submitList(books)
        })

        binding.btnThem.setOnClickListener {
            val ten = binding.edtTenSach.text.toString()
            val tacGia = binding.edtTacgia.text.toString()
            val ngay = binding.edtNgayxuatban.text.toString()
            val soTrang = binding.edtSoTrang.text.toString()

            if (ten.isBlank() || tacGia.isBlank() || ngay.isBlank() || soTrang.isBlank()) {
                Toast.makeText(this, "Vui lòng nhập đủ thông tin", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val soTrangInt = soTrang.toIntOrNull()
            if (soTrangInt == null) {
                Toast.makeText(this, "Số trang phải là số", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val newBook = Book(
                tenSach = ten,
                tacGia = tacGia,
                ngayXuatBan = ngay,
                soTrang = soTrangInt
            )
            bookViewModel.insert(newBook)
            Toast.makeText(this, "Thêm sách thành công", Toast.LENGTH_SHORT).show()

            // Xóa nội dung nhập liệu sau khi thêm
            binding.edtTenSach.text?.clear()
            binding.edtTacgia.text?.clear()
            binding.edtNgayxuatban.text?.clear()
            binding.edtSoTrang.text?.clear()
        }
    }
}
