package com.example.myapplication_study01

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.Nullable
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders

private const val TAG = "MainActivity"
//新增键值对的键
private const val KEY_INDEX = "index"

class MainActivity : AppCompatActivity() {

    //声明以下变量为button或textview格式
    private lateinit var trueButton: Button
    private lateinit var falseButton: Button
    private lateinit var nextButton: Button
    private lateinit var questionTextView: TextView


    //惰性初始化QuizViewModel
    private val quizViewModel: QuizViewModel by lazy {
        ViewModelProviders.of(this).get(QuizViewModel::class.java)
    }

    //三个button的逻辑
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "OnCreate(Bundle?) called")
        setContentView(R.layout.activity_main)

        //在onCreate(Bundle?)函数中检查存储的bundle信息
        val currentIndex = savedInstanceState?.getInt(KEY_INDEX, 0) ?: 0
        quizViewModel.currentIndex = currentIndex

        //访问ViewModel
        /*
        val provider: ViewModelProvider = ViewModelProviders.of(this)
        val quizViewModel = provider.get(QuizViewModel::class.java)
        Log.d(TAG, "Got a QuizViewModel: $quizViewModel")
         */

        //通过资源ID访问视图对象（各个变量指向xml中的button）
        trueButton = findViewById(R.id.first_button)
        falseButton = findViewById(R.id.second_button)
        nextButton = findViewById(R.id.next_button)
        questionTextView = findViewById(R.id.question_text_view)

        //nextButton一开始是不可用的
        nextButton.isEnabled = false

        //为trueButton和falseButton设置监听器
        trueButton.setOnClickListener{ view: View ->
            /*
            Toast.makeText(
                this,
                R.string.correct_toast,
                Toast.LENGTH_SHORT)
                .show()
            */
            checkAnswer(true)
            trueButton.isEnabled=false
            falseButton.isEnabled=false
            nextButton.isEnabled=true
        }
        falseButton.setOnClickListener{ view: View ->
            /*
            Toast.makeText(
                this,
                R.string.incorrect_toast,
                Toast.LENGTH_SHORT)
                .show()
            */
            checkAnswer(false)
            trueButton.isEnabled=false
            falseButton.isEnabled=false
            nextButton.isEnabled=true
        }

        //给nextButton设置监听器（规定button点击后问题序号currentIndex+1）
        nextButton.setOnClickListener{
            quizViewModel.moveToNext()
            /*
            currentIndex = (currentIndex + 1)% questionBank.size
            val questionTextResId = questionBank[currentIndex].textResId
            questionTextView.setText(questionTextResId)
             */
            updateQuestion()
            trueButton.isEnabled=true
            falseButton.isEnabled=true
            nextButton.isEnabled=false
        }
        //初始化设置activity视图中的文本
        /*
        val questionTextResId = questionBank[currentIndex].textResId
        questionTextView.setText(questionTextResId)
         */
        updateQuestion()
    }

    //覆盖生命周期函数
    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart() called")
    }
    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume() called")
    }
    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause() called")
    }

    override fun onSaveInstanceState(savedInstanceState: Bundle) {
        super.onSaveInstanceState(savedInstanceState)
        Log.i(TAG, "onSaveInstanceState")
        savedInstanceState.putInt(KEY_INDEX, quizViewModel.currentIndex)
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop() called")
    }
    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy() called")
    }

    //显示当前question的函数
    private fun updateQuestion(){
        /*
        val questionTextResId = questionBank[currentIndex].textResId

         */
        val questionTextResId = quizViewModel.currentQuestionText
        questionTextView.setText(questionTextResId)
    }

    //检验答案是否与questionBank中的对应
    private fun checkAnswer(userAnswer: Boolean){
        /*
        val correctAnswer = questionBank[currentIndex].answer
         */
        val correctAnswer = quizViewModel.currentQuestionAnswer
        val messageResId = if (userAnswer == correctAnswer) {
            quizViewModel.trueNumber += 1
            R.string.correct_toast
        }else{
            R.string.incorrect_toast
        }

        //最后填写了会出现含正确率统计的toast
        val correctRate = ((quizViewModel.trueNumber*1.00)/quizViewModel.questionBank.size)
        if (quizViewModel.currentIndex<(quizViewModel.questionBank.size-1)){
            Toast.makeText(this, messageResId, Toast.LENGTH_SHORT)
                .show()
        }else {
            Toast.makeText(this, "你是个" + (correctRate * 100) + "%的福瑞控二次元呢~！", Toast.LENGTH_SHORT)
                .show()
        }
        /*
        Toast.makeText(this, messageResId, Toast.LENGTH_SHORT)
            .show()
         */
    }
}
