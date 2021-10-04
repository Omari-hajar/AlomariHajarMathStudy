package com.example.alomari_hajar_mathstudy

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import java.util.Collections.list
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    private lateinit var layout: LinearLayout
    private lateinit var rvMain: RecyclerView
    private lateinit var list: ArrayList<String>


    private var score = 0
    private var highScore = 0


        var firstNum = 0
        var secondNum =0
        var start = 0
        var end = 10

    private lateinit var tvHS: TextView
    private lateinit var tvS: TextView
    private lateinit var tvQ: TextView
    private lateinit var userInput: EditText
    private lateinit var btnSubmit: Button



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        alertDialogStart()

        //finding + declaring UI elements
        layout = findViewById(R.id.layoutMain)
        tvHS = findViewById(R.id.tvHS)
        tvS = findViewById(R.id.tvS)
        tvQ = findViewById(R.id.tvQ)
        userInput = findViewById(R.id.etInput)
        btnSubmit = findViewById(R.id.btnSubmit)
        rvMain = findViewById(R.id.rvMain)

        list = arrayListOf()


        rvMain.adapter = RVAdapter(list)
        rvMain.layoutManager = LinearLayoutManager(this)

        //load HighScore
        loadData()




        firstNum = Random.nextInt(start, end)
        secondNum = Random.nextInt(start, end)

        tvQ.text = "Problem"

        disableInput()



    }


    private fun add() {

            val sum = firstNum + secondNum
            val userAnswer = userInput.text.toString()
            userInput.text.clear()
        if(userAnswer.isNotEmpty()){
            if (userAnswer.toInt() == sum) {
                list.add("$firstNum + $secondNum = $sum")
                score++
                tvS.text = "Score:  $score"
                start += 5
                end += 5
            } else {
                list.add("the correct answer is $firstNum + $secondNum = $sum")
                disableInput()
                alertDialog()
            }

        firstNum = Random.nextInt(start, end)
         secondNum = Random.nextInt(start, end)
        tvQ.text = "$firstNum + $secondNum = "
        scrollEnd()

        highScore ++

    }else{
        Snackbar.make(layout, "please enter a number", Snackbar.LENGTH_LONG).show()
    }

    }

    //subtraction

    private fun sub() {



        val sub = Math.abs(firstNum - secondNum)
        val userAnswer = userInput.text.toString()
        userInput.text.clear()
        if(userAnswer.isNotEmpty()) {
            if (userAnswer.toInt() == sub) {
                list.add("$firstNum - $secondNum = $sub")
                score++
                tvS.text = "Score:  $score"
                start += 5
                end += 5
            } else {
                list.add(" the correct answer is $firstNum - $secondNum = $sub")
                disableInput()
                alertDialog()
            }

            firstNum = Random.nextInt(start, end)
            secondNum = Random.nextInt(start, end)
            tvQ.text = "$firstNum - $secondNum = "

            scrollEnd()
            highScore++
        }else{
            Snackbar.make(layout, "please enter a number", Snackbar.LENGTH_LONG).show()
        }

    }

    //disable input and submit button

    private fun disableInput(){
        userInput.isClickable= false
        userInput.isEnabled= false
        btnSubmit.isEnabled = false
        btnSubmit.isClickable = false
    }

    private fun enableInput(){
        userInput.isClickable= true
        userInput.isEnabled= true
        btnSubmit.isEnabled = true
        btnSubmit.isClickable = true
    }


    //alert dialog to ask if play again

    private fun alertDialog(){
        val dialogAlert = AlertDialog.Builder(this)
        dialogAlert.setMessage("PLay Again")
            .setPositiveButton("Yes", DialogInterface.OnClickListener{
                _,_ -> this.recreate()

            })
            .setNegativeButton("No", DialogInterface.OnClickListener{
                dialog, _-> dialog.cancel()
            })
        val alert = dialogAlert.create()
        alert.setTitle("Math Study app")
        alert.show()
    }


    //start dialog: displayed at the beginning of the app

    private fun alertDialogStart(){
        val dialogAlert = AlertDialog.Builder(this)
        dialogAlert.setMessage("Welcome to the Math Study App! \n How many questions can you solve?\n\n\n Please, Select addition or subtraction from the main menu to start the quiz")
            .setPositiveButton("Start", DialogInterface.OnClickListener{
                    _,_ -> this.onResume()

            })

        val alert = dialogAlert.create()
        alert.setTitle("Math Study app")
        alert.show()
    }

    ///fun to save data

    private fun saveData(){

        val sharedPreferences: SharedPreferences = getSharedPreferences("highScore", Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor = sharedPreferences.edit()
        editor.apply {
            putInt("INT_KEY", highScore)
        }.apply()
    }

    private fun loadData(){
        val sharedPreferences: SharedPreferences = getSharedPreferences("highScore", Context.MODE_PRIVATE)
        val savedScore = sharedPreferences.getInt("INT_KEY", 0)
        if (savedScore != null) {
            highScore = savedScore
            tvHS.text = "High Score: $savedScore"
        }else{
            highScore = 0
            tvHS.text = "HighScore: 0"
        }

    }


    ////MENU START//////
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.mi_add ->{
                enableInput()

                firstNum = Random.nextInt(start, end)
                secondNum = Random.nextInt(start, end)

                tvQ.text = "$firstNum + $secondNum = "
                btnSubmit.setOnClickListener {

                    add()

                    saveData()

                    rvMain.adapter!!.notifyDataSetChanged()
                }
            }
            R.id.mi_Sub->{
                enableInput()
                firstNum = Random.nextInt(start, end)
                secondNum = Random.nextInt(start, end)

                tvQ.text = "$firstNum - $secondNum = "
                btnSubmit.setOnClickListener {

                    sub()

                    saveData()

                    rvMain.adapter!!.notifyDataSetChanged()
                }
            }

        }
        return super.onOptionsItemSelected(item)
    }



    ///scroll to end
    private fun scrollEnd(){
        rvMain.scrollToPosition(list.size-1)
        rvMain.adapter!!.notifyDataSetChanged()
    }



}






