package com.lupo.guess

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.lupo.guess.databinding.ActivityRecordBinding

class RecordActivity : AppCompatActivity() {

    private var count = 0

    private lateinit var viewBinding:ActivityRecordBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityRecordBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)
        count =intent.getIntExtra("COUNT",0)
        viewBinding.tvCountResult.setText(count.toString())

        viewBinding.button.setOnClickListener(object: View.OnClickListener{
            override fun onClick(v: View?) {
                TODO("Not yet implemented")
            }
        })

    }
}