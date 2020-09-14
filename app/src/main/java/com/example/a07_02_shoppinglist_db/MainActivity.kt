package com.example.a07_02_shoppinglist_db

import android.app.Activity
import android.app.Application
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.room.*
import kotlinx.android.synthetic.main.activity_main.*

class App : Application() {
    val db by lazy {
        Room.databaseBuilder(this, ShoppingDatabase::class.java, "shopping-db").allowMainThreadQueries().build()
    }
}

@Database(version = 1, entities = [ShoppingItem::class])
abstract class ShoppingDatabase : RoomDatabase() {
    abstract fun shoppingItemDao(): ShoppingItemDao
}
@Entity(tableName="shopping_item")
data class
ShoppingItem(
    var name: String,
    val quantity: Int,
    val unit: String,
    @PrimaryKey(autoGenerate = true) var uid: Long = 0)
@Dao
interface ShoppingItemDao {
    @Insert
    fun insertAll(vararg items: ShoppingItem): List<Long>
    @Query("SELECT * FROM shopping_item")
    fun getAll(): List<ShoppingItem>
    @Query("SELECT * FROM shopping_item WHERE uid == :itemId")
    fun getItemById(itemId: Long): ShoppingItem
    @Update
    fun update(item: ShoppingItem)
    @Delete
    fun delete(item: ShoppingItem)
}
object Singleton

class MainActivity : AppCompatActivity() {
    private val shoppingItems = mutableListOf<ShoppingItem>()
//            ShoppingItem ("pirmais", 1, "kg")
    private val db get() = (application as App).db //Get database instance

    override fun onCreate(savedInstanceState: Bundle?) {
        shoppingItems.addAll(db.shoppingItemDao().getAll())
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val adapter = ShoppingItemAdapter(this, shoppingItems)
        main_list.adapter = adapter

//        deleteSelectedItem()
        fun addNewItem(){
            if (main_add_name.text.toString() == ""){
                Toast.makeText(this, "Enter new item", Toast.LENGTH_SHORT).show()
            }else {
                val mainMessage = ""
                val intent = Intent(this,fill_quantity_and_unit::class.java).apply {
                    putExtra(EXTRA_MESSAGE, mainMessage)
                }
                startActivityForResult(intent, DETAIL_REQUEST)
                adapter.notifyDataSetChanged()
            }
        }
//add new item
        main_add_button.setOnClickListener {
            addNewItem()
        }
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == DETAIL_REQUEST && resultCode == Activity.RESULT_OK) {
            data?.let {
                val shoppingItemQuantity= data.getStringExtra(EXTRA_REPLAY_COUNT)
                val shoppingItemUnit= data.getStringExtra(EXTRA_REPLAY_UNIT)
                if (shoppingItemQuantity == null||shoppingItemUnit == null) {
//                    kaut ko darīs
                } else {
                    val item = ShoppingItem(main_add_name.text.toString(), shoppingItemQuantity.toInt(), shoppingItemUnit)
                    item.uid = db.shoppingItemDao().insertAll(item).first()
                    shoppingItems.add(item)
                    main_add_name.text.clear()
                }
            }
        }
    }
    companion object {
        const val EXTRA_MESSAGE = "mobile.practice.message"
        const val DETAIL_REQUEST = 1234
        const val EXTRA_REPLAY_UNIT = "quantity.string.REPLAY"
        const val EXTRA_REPLAY_COUNT= "unit.string.REPLAY"
    }
}