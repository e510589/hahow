package com.lupo.guess

import android.app.Activity
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AlertDialog
import com.lupo.guess.databinding.ActivityRecordBinding

class RecordActivity : AppCompatActivity() {

    private var count = 0

    private lateinit var viewBinding:ActivityRecordBinding

    private val TAG = "Kevin_RecordActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityRecordBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)
        count =intent.getIntExtra("COUNT",0)
        viewBinding.tvCountResult.setText(count.toString())
        Log.d(TAG, "onCreate: count get!")

        viewBinding.button.setOnClickListener(object: View.OnClickListener{
            override fun onClick(v: View?) {
                val name = viewBinding.tieRecord.text.toString()

                if (!name.equals("")){
                    getSharedPreferences("Record", MODE_PRIVATE)
                        .edit()
                        .putInt("RECORD",count)
                        .putString("NAME",name)
                        .apply()
                    var intent = Intent()
                    intent.putExtra("RESULT",name)
                    setResult(Activity.RESULT_OK,intent)
                    finish()
                }else{
                    AlertDialog.Builder(this@RecordActivity).setMessage(getString(R.string.yournameplease)).setPositiveButton(getString(R.string.OK),null).show()
                }
            }
        })

    }
}