package com.example.demo_gk.util

import android.app.DatePickerDialog
import android.content.Context
import android.icu.text.SimpleDateFormat
import android.view.View
import android.widget.EditText
import java.util.Calendar
import java.util.Locale

class DatePickerHelper (
    private val context: Context,
    private val editText: EditText,
    private val dateFormat: SimpleDateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
) : View.OnClickListener {
    fun showDatePicker(view: View) {
        val calendar = Calendar.getInstance()

        // Nếu mà đã có ngày , parse sẽ để hiển thị
        editText.text?.toString()?.let { currentDate ->
            try {
                val parsedDate = dateFormat.parse(currentDate);
                parsedDate?.let { calendar.time = it }
            }catch (e: Exception){
                e.printStackTrace()
            }
        }

        DatePickerDialog(
            context,
            { _, year,month,dayOfMonth ->
                calendar.set(year,month,dayOfMonth)
                editText.setText(dateFormat.format(calendar.time))
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        ).show()
    }



    companion object{
        fun disableKeyboardInput(editText: EditText){
            editText.keyListener = null
            editText.isFocusable = false
        }
    }

    override fun onClick(v: View?) {
        TODO("Not yet implemented")
    }
}