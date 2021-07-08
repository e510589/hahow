package com.lupo.guess

import android.util.Log
import kotlin.random.Random

class SecretNumber {

    private var secret = Random.nextInt(10)+1;

    private var count = 0

    private val TAG = "Kevin_SecretNumber"

    constructor(){
        Log.d(TAG, "The secret number is: " + secret.toString())
    }


    private fun get():Int{

        return 0
    }

    fun getSecret():Int{
        return secret
    }

    fun getCount():Int{
        return count
    }

    fun validate(guess:Int):Int{
        count++
        return guess-secret

    }

    fun cal(n1:Int,n2:Int,operator:(Int,Int) -> Int) :String{
        return "The answer is ${operator(n1,n2)}"
    }

}