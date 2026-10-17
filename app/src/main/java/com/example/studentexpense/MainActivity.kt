package com.example.studentexpense

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    // متغیر نگهداری موجودی کل (Long برای جلوگیری از سرریز اعداد بزرگ)
    private var balance: Long = 0L

    // لیست متنی برای نگهداری تاریخچه تراکنش‌ها در حافظه موقت
    private val historyList = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // اتصال این فایل کد به ظاهر صفحه (activity_main.xml)
        setContentView(R.layout.activity_main)

        // پیدا کردن اجزای صفحه با استفاده از شناسه (ID) آن‌ها
        val tvBalance = findViewById<TextView>(R.id.tvBalance)
        val etTitle = findViewById<EditText>(R.id.etTitle)
        val etAmount = findViewById<EditText>(R.id.etAmount)
        val btnAddIncome = findViewById<Button>(R.id.btnAddIncome)
        val btnAddExpense = findViewById<Button>(R.id.btnAddExpense)
        val tvHistory = findViewById<TextView>(R.id.tvHistory)

        // تابع کمکی برای ثبت تراکنش (چه درآمد چه هزینه)
        fun addTransaction(isIncome: Boolean) {
            val title = etTitle.text.toString().trim()
            val amountText = etAmount.text.toString().trim()

            // ۱. بررسی خالی نبودن فیلدها
            if (title.isEmpty() || amountText.isEmpty()) {
                Toast.makeText(this, "لطفاً عنوان و مبلغ را کامل وارد کنید", Toast.LENGTH_SHORT).show()
                return
            }

            // ۲. تبدیل متن به عدد و جلوگیری از ورود مقدار نامعتبر یا منفی/صفر
            val amount = amountText.toLongOrNull()
            if (amount == null || amount <= 0L) {
                Toast.makeText(this, "مبلغ باید یک عدد مثبت و معتبر باشد", Toast.LENGTH_SHORT).show()
                return
            }

            // ۳. اعمال تغییر روی موجودی و ساخت متن تراکنش
            if (isIncome) {
                balance += amount
                historyList.add(0, "✅ دریافتی: $title (+$amount تومان)")
            } else {
                balance -= amount
                historyList.add(0, "🔻 هزینه: $title (-$amount تومان)")
            }

            // ۴. به‌روزرسانی ظاهر صفحه با مقادیر جدید
            tvBalance.text = "موجودی کل: $balance تومان"
            tvHistory.text = historyList.joinToString("\n")

            // ۵. پاک کردن کادرهای ورودی برای تراکنش بعدی
            etTitle.text.clear()
            etAmount.text.clear()
        }

        // وقتی دکمه ثبت دریافتی زده شد
        btnAddIncome.setOnClickListener {
            addTransaction(isIncome = true)
        }

        // وقتی دکمه ثبت هزینه زده شد
        btnAddExpense.setOnClickListener {
            addTransaction(isIncome = false)
        }
    }
}
