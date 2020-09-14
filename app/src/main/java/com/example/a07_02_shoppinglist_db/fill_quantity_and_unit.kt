package com.example.a07_02_shoppinglist_db

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.a07_02_shoppinglist_db.MainActivity.Companion.EXTRA_REPLAY_COUNT
import com.example.a07_02_shoppinglist_db.MainActivity.Companion.EXTRA_REPLAY_UNIT
import kotlinx.android.synthetic.main.quantity_and_unit_input.*

class fill_quantity_and_unit : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        val inputMessage = intent.getStringExtra(EXTRA_MESSAGE)

        setContentView(R.layout.quantity_and_unit_input)

        quantity_input_button.setOnClickListener {
            val quantity = input_integer.text.toString()
            val unit = input_unit_text.text.toString()
            val result = Intent().apply {
                putExtra(EXTRA_REPLAY_COUNT,quantity)
                putExtra(EXTRA_REPLAY_UNIT, unit)
            }
            setResult(Activity.RESULT_OK, result)
            finish()
        }

    }
}