package com.frewen.android.demo.logic.samples.sqlite

import android.content.ContentValues
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.frewen.android.aura.annotations.ActivityDestination
import com.frewen.android.demo.R

@ActivityDestination(pageUrl = "main/tabs/publish", needLogin = true)
class SQLiteDemoActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sqlite_demo)
    }

    fun createDataBase(view: View) {
        var values = ContentValues()
        values.put(UserColumns.ID, 3)
        values.put(UserColumns.USER_NAME, "Frewen.Wang")
        values.put(UserColumns.USER_AGE, 30)
        var count = AuraSQLiteWrapper(this).insert(SQLiteConstant.DB_TABLE_NAME, null, values)

        if (count > 0) {
            Toast.makeText(this, "插入成功", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "插入失败", Toast.LENGTH_LONG).show();
        }
    }

    fun insertAPI(view: View) {
        var values = ContentValues()
        values.put(UserColumns.ID, 3)
        values.put(UserColumns.USER_NAME, "Frewen.Wang")
        values.put(UserColumns.USER_AGE, 30)
        var count = AuraSQLiteWrapper(this).insert(SQLiteConstant.DB_TABLE_NAME, null, values)

        if (count > 0) {
            Toast.makeText(this, "插入成功", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "插入失败", Toast.LENGTH_LONG).show();
        }
    }

    fun updateAPI(view: View) {
        var values = ContentValues()
        values.put(UserColumns.USER_NAME, "Frewen.WangModify")
        values.put(UserColumns.USER_AGE, 30)
        var count = AuraSQLiteWrapper(this).update(SQLiteConstant.DB_TABLE_NAME, values, "_id=?", null)

        if (count > 0) {
            Toast.makeText(this, "更新成功", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "更新失败", Toast.LENGTH_LONG).show();
        }
    }

    fun deleteAPI(view: View) {
        var count = AuraSQLiteWrapper(this).delete(SQLiteConstant.DB_TABLE_NAME, "_id=?", null)

        if (count > 0) {
            Toast.makeText(this, "删除成功", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "删除失败", Toast.LENGTH_LONG).show();
        }
    }
}