package com.testtask.windytestapp
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Looper
import android.util.Log
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
         initUI()
    }

    private fun initUI() {
        btn_calc_result.setOnClickListener {
            GlobalScope.launch(Dispatchers.Main) {

                if (et_input_it.text.toString()!=""){
                    numbersFlow(et_input_it.text.toString().toInt()).sum { it }
                } else {

                    Toast.makeText(applicationContext, "Пожалуйста, введите число!", Toast.LENGTH_SHORT).show()
                }

            }
        }
    }


    private fun numbersFlow(intCount:Int): Flow<Int> = flow {
        for (i in 1..intCount) {
            delay(100)
            emit(i)
        }
    }

    suspend fun <T> Flow<T>.sum(intFlow: suspend (T) -> Int): Int {
        var sum = 0
        onEach {
            sum += intFlow(it)
            tv_show_result.text = sum.toString()
        }.collect()
        return sum
    }
}