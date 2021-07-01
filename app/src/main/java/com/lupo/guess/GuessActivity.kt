package com.lupo.guess

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import com.lupo.guess.databinding.ActivityGuessBinding

class GuessActivity : AppCompatActivity() {

    private lateinit var viewBinding:ActivityGuessBinding

    private lateinit var secretNumber:SecretNumber

    private var secret = 0

    private val TAG = "Keivn_GuessActivity"

    private val PASS = 0

    private val NOT_PASS = 1

    private val NOT_VALID = 2;

    private val REFRESH = 3;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityGuessBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)
        secretNumber = SecretNumber()
        secret = secretNumber.getSecret()
        var msg = ""

        viewBinding.btnGuess.setOnClickListener {
            var type = -1
            if(viewBinding.tieGuess.text.toString() != ""){
                val guess = viewBinding.tieGuess.text.toString().toInt()
                val guessResult = secretNumber.validate(guess)
                viewBinding.tvCount.setText(secretNumber.getCount().toString())
                if (guessResult < 0){
                    msg = getString(R.string.bigger)
                    type = NOT_PASS
                }else if (guessResult > 0){
                    msg = getString(R.string.smaller)
                    type = NOT_PASS
                }else if (guessResult == 0){
                    if (secretNumber.getCount() <= 3){
                        msg = getString(R.string.godlike)
                        type = PASS
                    }else{
                        msg = getString(R.string.goodjob)
                        type = PASS
                    }
                }
                showDialog(type,msg)
            }else{
                msg = getString(R.string.pleaseEnterANumber)
                type = NOT_VALID
                showDialog(type,msg)
            }
        }

        viewBinding.fabRefresh.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
                showDialog(REFRESH,"You want to refresh the game?")
            }

        })
    }

    fun showDialog(type:Int,msg:String){

        val builder =  AlertDialog.Builder(this);
        builder.setMessage(msg)
        builder.setPositiveButton(getString(R.string.OK),object:DialogInterface.OnClickListener{
            override fun onClick(dialog: DialogInterface?, which: Int) {

                when(type){

                    PASS ->{
                        val intent = Intent(this@GuessActivity,RecordActivity::class.java)
                        intent.putExtra("COUNT",secretNumber.getCount())
                        startActivity(intent)
                    }

                    NOT_PASS->{
                        dialog?.dismiss()
                    }

                    NOT_VALID->{
                        dialog?.cancel()
                    }

                    REFRESH->{
                        refresh()
                        dialog?.cancel()
                    }
                }

                dialog?.cancel()
            }
        })
        builder.show()
    }

    fun refresh(){
        secretNumber = SecretNumber()
        secret = secretNumber.getSecret()
        viewBinding.tvCount.setText("0")
    }





}

