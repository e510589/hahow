package com.lupo.guess

import android.app.Activity
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContract
import androidx.appcompat.app.AlertDialog
import com.lupo.guess.databinding.ActivityGuessBinding

class GuessActivity : AppCompatActivity() {

    private lateinit var viewBinding:ActivityGuessBinding

    private lateinit var secretNumber:SecretNumber

    private var secret = 0

    private val TAG = "Kevin_GuessActivity"

    private val PASS = 0

    private val NOT_PASS = 1

    private val NOT_VALID = 2;

    private val REFRESH = 3;

    private lateinit var myActivityLuncher:ActivityResultLauncher<Int>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityGuessBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)
        secretNumber = SecretNumber()
        secret = secretNumber.getSecret()
        var msg = ""
        myActivityLuncher = registerForActivityResult(GuessActivityResultContract()){
            refresh()
        }

        viewBinding.btnGuess.setOnClickListener {
            var type = -1
            hideKeyBoard()
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
                        msg = getString(R.string.godlike)+secretNumber.getSecret().toString()
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
        viewBinding.tvCount.setText("0")
    }

    fun showDialog(type:Int,msg:String){

        val builder =  AlertDialog.Builder(this);
        builder.setMessage(msg)
        builder.setPositiveButton(getString(R.string.OK),object:DialogInterface.OnClickListener{
            override fun onClick(dialog: DialogInterface?, which: Int) {

                when(type){

                    PASS ->{
//                        val intent = Intent(this@GuessActivity,RecordActivity::class.java)
//                        intent.putExtra("COUNT",secretNumber.getCount())
//                        startActivity(intent)

                        myActivityLuncher.launch(secretNumber.getCount())

                    }

                    NOT_PASS->{
                        viewBinding.tieGuess.setText("")
                        dialog?.dismiss()
                    }

                    NOT_VALID->{
                        viewBinding.tieGuess.setText("")
                        dialog?.cancel()
                    }

                    REFRESH->{
                        viewBinding.tieGuess.setText("")
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


    fun getSecretNumber():Int{
        return secret
    }

    fun getCount():Int{
        return secretNumber.getCount()
    }
    private fun hideKeyBoard(){
        val imm = this.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(currentFocus!!.windowToken, 0)
    }


}

class GuessActivityResultContract:ActivityResultContract<Int,String>(){
    override fun createIntent(context: Context, input: Int?): Intent {
        var intent = Intent(context,RecordActivity::class.java)
        intent.putExtra("COUNT",input)
        return intent
    }

    override fun parseResult(resultCode: Int, intent: Intent?): String? {
        val data = intent?.getStringExtra("RESULT")
        return if(resultCode == Activity.RESULT_OK && data != null) data
        else null
    }


}

