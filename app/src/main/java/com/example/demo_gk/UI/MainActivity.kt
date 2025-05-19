package com.example.demo_gk.UI


import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.demo_gk.Adapter.BookAdapter
import com.example.demo_gk.CSDL.Book
import com.example.demo_gk.ViewModel.BookViewModel
import com.example.demo_gk.databinding.ActivityMainBinding
import com.example.demo_gk.util.DatePickerHelper
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val bookViewModel: BookViewModel by viewModels()
    private lateinit var adapter: BookAdapter
    private lateinit var binding: ActivityMainBinding
    private var selectedBook: Book? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Khoi tao DatePickerHelper
        val datePickerHelper = DatePickerHelper(
            context = this,
            editText =  binding.edtNgayxuatban
        )

        //Mo DatePicker khi click vao EditText
        //binding.edtNgayxuatban.setOnClickListener(
        //    datePickerHelper.showDatePicker()
        //)
        binding.edtNgayxuatban.setOnClickListener { view ->
            datePickerHelper.showDatePicker(view)
        }
        binding.btnDatePicker.setOnClickListener{
            view ->
            datePickerHelper.showDatePicker(view)
        }
        DatePickerHelper.disableKeyboardInput(binding.edtNgayxuatban)

        adapter = BookAdapter { bookClicked ->
            // Xử lý khi click vào một cuốn sách
            selectedBook = bookClicked
            Toast.makeText(this, "Bạn chọn sách: ${bookClicked.tenSach}", Toast.LENGTH_SHORT).show()
            
            // Hiển thị thông tin sách đã chọn lên form
            binding.edtTenSach.setText(bookClicked.tenSach)
            binding.edtTacgia.setText(bookClicked.tacGia)
            binding.edtNgayxuatban.setText(bookClicked.ngayXuatBan)
            binding.edtSoTrang.setText(bookClicked.soTrang.toString())

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
            clearInputFields()
        }

        // Xử lý sự kiện nút Sửa sách
        binding.btnSua.setOnClickListener {
            val ten = binding.edtTenSach.text.toString()
            val tacGia = binding.edtTacgia.text.toString()
            val ngay = binding.edtNgayxuatban.text.toString()
            val soTrang = binding.edtSoTrang.text.toString()

            if (selectedBook == null) {
                Toast.makeText(this, "Vui lòng chọn sách cần sửa", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (ten.isBlank() || tacGia.isBlank() || ngay.isBlank() || soTrang.isBlank()) {
                Toast.makeText(this, "Vui lòng nhập đủ thông tin", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val soTrangInt = soTrang.toIntOrNull()
            if (soTrangInt == null) {
                Toast.makeText(this, "Số trang phải là số", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val updatedBook = selectedBook!!.copy(
                tenSach = ten,
                tacGia = tacGia,
                ngayXuatBan = ngay,
                soTrang = soTrangInt
            )
            
            bookViewModel.update(updatedBook)
            Toast.makeText(this, "Cập nhật sách thành công", Toast.LENGTH_SHORT).show()
            
            // Reset sau khi sửa
            selectedBook = null
            clearInputFields()
        }

        // Xử lý sự kiện nút Xóa sách
        binding.btnXoa.setOnClickListener {
            if (selectedBook == null) {
                Toast.makeText(this, "Vui lòng chọn sách cần xóa", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Hiển thị hộp thoại xác nhận trước khi xóa
            AlertDialog.Builder(this)
                .setTitle("Xác nhận xóa")
                .setMessage("Bạn có chắc chắn muốn xóa sách '${selectedBook?.tenSach}' không?")
                .setPositiveButton("Đồng ý") { _, _ ->
                    bookViewModel.delete(selectedBook!!)
                    Toast.makeText(this, "Xóa sách thành công", Toast.LENGTH_SHORT).show()
                    
                    // Reset sau khi xóa
                    selectedBook = null
                    clearInputFields()
                }
                .setNegativeButton("Hủy") { _, _ ->
                    // Gọi hàm xóa các trường trên text
                    clearInputFields()
                }
                .show()
        }
    }

    // Hàm tiện ích để xóa các trường nhập liệu
    private fun clearInputFields() {
        binding.edtTenSach.text?.clear()
        binding.edtTacgia.text?.clear()
        binding.edtNgayxuatban.text?.clear()
        binding.edtSoTrang.text?.clear()
    }
}
