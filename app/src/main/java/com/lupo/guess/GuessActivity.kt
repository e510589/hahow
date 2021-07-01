package com.lupo.guess

import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import com.lupo.guess.databinding.ActivityGuessBinding

class GuessActivity : AppCompatActivity() {

    private lateinit var viewBinding:ActivityGuessBinding

    private lateinit var secretNumber:SecretNumber

    private var secret = 0

    private var guessResult = 0

    private val TAG = "Keivn_GuessActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityGuessBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)
        secretNumber = SecretNumber()
        secret = secretNumber.getSecret()
        var msg = ""

        Log.d(TAG,"The secret number is: " + secret.toString())

        viewBinding.btnGuess.setOnClickListener {
            if(viewBinding.tieGuess.text.toString() != ""){
                val guess = viewBinding.tieGuess.text.toString().toInt()
                guessResult = secretNumber.validate(guess)
                viewBinding.tvCount.setText(secretNumber.getCount().toString())
                if (guessResult < 0){
                    msg = getString(R.string.bigger)
                }else if (guessResult > 0){
                    msg = getString(R.string.smaller)
                }else if (guessResult == 0){
                    if (secretNumber.getCount() <= 3){
                        msg = getString(R.string.godlike)
                    }else{
                        msg = getString(R.string.goodjob)
                    }
                }
                showDialog(msg)
            }else{
                msg = "Please enter a number!"
                showDialog(msg)
            }
        }
    }

    fun showDialog(msg:String){

        val builder =  AlertDialog.Builder(this);
        builder.setMessage(msg)
        builder.setPositiveButton("OK",object:DialogInterface.OnClickListener{
            override fun onClick(dialog: DialogInterface?, which: Int) {
                dialog?.cancel()
            }
        })
        builder.show()


    }





}

