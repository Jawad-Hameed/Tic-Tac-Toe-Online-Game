package com.jawadjk.tictactoe

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.firestore.FirebaseFirestore
import com.jawadjk.tictactoe.databinding.ActivityMainBinding
import com.jawadjk.tictactoe.databinding.GameEndDialogBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    lateinit var db: FirebaseFirestore

    lateinit var gameModel: GameModel
    lateinit var turn: String
    lateinit var userName: String
    lateinit var id: String
    private var counter = 0
    lateinit var one: String
    lateinit var two: String
    lateinit var three: String
    lateinit var four: String
    lateinit var five: String
    lateinit var six: String
    lateinit var seven: String
    lateinit var eight: String
    lateinit var nine: String

    lateinit var dialog: Dialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        id = intent.getStringExtra("id")!!
        userName = intent.getStringExtra("name")!!

        Toast.makeText(this@MainActivity, userName, Toast.LENGTH_SHORT).show()

        db = FirebaseFirestore.getInstance()

        db.collection("Game").document(id!!).get().addOnSuccessListener {
            val matchModel = it.toObject(MatchModel::class.java)

            if (matchModel != null) {
                binding.firstPlayer.text = matchModel.firstPlayer
                binding.secondPlayer.text = matchModel.secondPlayer

                setupGame(id, matchModel.firstPlayer)
            }
        }


        binding.one.setOnClickListener {
            if (userName == turn && one == ""){
                val gameModel = GameModel(++counter, changeTurn(), setValue(), two, three, four, five, six, seven, eight, nine)
                db.collection("Game").document(id).collection("Play").document("TicTacToe").set(gameModel)
                    .addOnSuccessListener {

                    }
            }
        }

        binding.two.setOnClickListener {
            if (userName == turn && two == ""){
                val gameModel = GameModel(++counter, changeTurn(), one, setValue(), three, four, five, six, seven, eight, nine)
                db.collection("Game").document(id).collection("Play").document("TicTacToe").set(gameModel)
                    .addOnSuccessListener {

                    }
            }
        }

        binding.three.setOnClickListener {
            if (userName == turn && three == ""){
                val gameModel = GameModel(++counter, changeTurn(), one, two, setValue(), four, five, six, seven, eight, nine)
                db.collection("Game").document(id).collection("Play").document("TicTacToe").set(gameModel)
                    .addOnSuccessListener {

                    }
            }
        }


        binding.four.setOnClickListener {
            if (userName == turn && four == ""){
                val gameModel = GameModel(++counter, changeTurn(), one, two, three, setValue(), five, six, seven, eight, nine)
                db.collection("Game").document(id).collection("Play").document("TicTacToe").set(gameModel)
                    .addOnSuccessListener {

                    }
            }
        }


        binding.five.setOnClickListener {
            if (userName == turn && five == ""){
                val gameModel = GameModel(++counter, changeTurn(), one, two, three, four, setValue(), six, seven, eight, nine)
                db.collection("Game").document(id).collection("Play").document("TicTacToe").set(gameModel)
                    .addOnSuccessListener {

                    }
            }
        }


        binding.six.setOnClickListener {
            if (userName == turn && six == ""){
                val gameModel = GameModel(++counter, changeTurn(), one, two, three, four, five, setValue(), seven, eight, nine)
                db.collection("Game").document(id).collection("Play").document("TicTacToe").set(gameModel)
                    .addOnSuccessListener {

                    }
            }
        }


        binding.seven.setOnClickListener {
            if (userName == turn && seven == ""){
                val gameModel = GameModel(++counter, changeTurn(), one, two, three, four, five, six, setValue(), eight, nine)
                db.collection("Game").document(id).collection("Play").document("TicTacToe").set(gameModel)
                    .addOnSuccessListener {

                    }
            }
        }

        binding.eight.setOnClickListener {
            if (userName == turn && eight == ""){
                val gameModel = GameModel(++counter, changeTurn(), one, two, three, four, five, six, seven, setValue(), nine)
                db.collection("Game").document(id).collection("Play").document("TicTacToe").set(gameModel)
                    .addOnSuccessListener {

                    }
            }
        }

        binding.nine.setOnClickListener {
            if (userName == turn && nine == ""){
                val gameModel = GameModel(++counter, changeTurn(), one, two, three, four, five, six, seven, eight, setValue())
                db.collection("Game").document(id).collection("Play").document("TicTacToe").set(gameModel)
                    .addOnSuccessListener {

                    }
            }
        }


    }

    private fun gameOverDialog(isWin: String){
        dialog = Dialog(this@MainActivity)
        var bindingD: GameEndDialogBinding = GameEndDialogBinding.inflate(layoutInflater)
        dialog.setContentView(bindingD.root)
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        dialog.setCancelable(false)
        dialog.show()

        var drawable: Int
        val text: String
        if (isWin == "Yes"){
            drawable = R.drawable.happy
            text = "You Win the Game"
        } else if(isWin == "Not") {
            drawable = R.drawable.sad
            text = "You Lose the Game"
        }else{
            drawable = R.drawable.draw
            text = "Game is Draw"
        }
        bindingD.imageView.setImageDrawable(getDrawable(drawable))
        bindingD.textView.text = text

        bindingD.okayButton.setOnClickListener {
                dialog.dismiss()
                val intent = Intent(this@MainActivity, HomeActivity::class.java)
                startActivity(intent)
                finish()

        }

    }
    private fun checkWinner(){
        val firstPlayer = binding.firstPlayer.text.toString()
        if (one == two && two == three && one != ""){
            if (one == "O" && userName == firstPlayer) gameOverDialog("Yes") else gameOverDialog("Not")
        }else if (four == five && five == six && four != ""){
            if (four == "O" && userName == firstPlayer) gameOverDialog("Yes") else gameOverDialog("Not")
        }else if (seven == eight && eight == nine && seven != ""){
            if (seven == "O" && userName == firstPlayer) gameOverDialog("Yes") else gameOverDialog("Not")
        }else if (one == four && four == seven && one != ""){
            if (one == "O" && userName == firstPlayer) gameOverDialog("Yes") else gameOverDialog("Not")
        }else if (two == five && five == eight && two != ""){
            if (two == "O" && userName == firstPlayer) gameOverDialog("Yes") else gameOverDialog("Not")
        }else if (three == six && six == nine && three != ""){
            if (three == "O" && userName == firstPlayer) gameOverDialog("Yes") else gameOverDialog("Not")
        }else if (one == five && five == nine && one != ""){
            if (one == "O" && userName == firstPlayer) gameOverDialog("Yes") else gameOverDialog("Not")
        }else if (three == five && five == seven && three != ""){
            if (three == "O" && userName == firstPlayer) gameOverDialog("Yes") else gameOverDialog("Not")
        }else if (counter == 9){
            gameOverDialog("Draw")
        }
    }

    private fun changeTurn(): String{
        if (turn == binding.firstPlayer.text.toString()){
            return binding.secondPlayer.text.toString()
        }else{
            return binding.firstPlayer.text.toString()
        }
    }

    private fun setValue(): String{
        if (turn == binding.firstPlayer.text.toString()){
            return "O"
        }else{
            return "X"
        }
    }
    private fun setupGame(id: String, firstPlayer: String) {
        val gameModel = GameModel(0, firstPlayer, "", "", "", "", "", "", "", "", "")

        db.collection("Game").document(id).collection("Play").document("TicTacToe").set(gameModel)
            .addOnSuccessListener {
                updateGame(id)
            }
    }

    private fun updateGame(id: String) {
        db.collection("Game").document(id).collection("Play").document("TicTacToe")
            .addSnapshotListener { value, error ->
                if (error != null) {
                    return@addSnapshotListener
                }

                gameModel = value?.toObject(GameModel::class.java)!!

                turn = gameModel.turn
                counter = gameModel.counter
                one = gameModel.one
                two = gameModel.two
                three = gameModel.three
                four = gameModel.four
                five = gameModel.five
                six = gameModel.six
                seven = gameModel.seven
                eight = gameModel.eight
                nine = gameModel.nine

                if (turn == binding.firstPlayer.text.toString()){
                    binding.playerO.background = getDrawable(R.drawable.selected_player)
                    binding.playerX.background = getDrawable(R.drawable.non_selected_player)
                }else{
                    binding.playerX.background = getDrawable(R.drawable.selected_player)
                    binding.playerO.background = getDrawable(R.drawable.non_selected_player)
                }

                if (gameModel?.one == "O"){
                    binding.one.setImageDrawable(getDrawable(R.drawable.o))
                }else if (gameModel?.one == "X"){
                    binding.one.setImageDrawable(getDrawable(R.drawable.x))
                }else{
                    binding.one.setImageDrawable(getDrawable(R.drawable.no_image))
                }

                if (gameModel?.two == "O"){
                    binding.two.setImageDrawable(getDrawable(R.drawable.o))
                }else if (gameModel?.two == "X"){
                    binding.two.setImageDrawable(getDrawable(R.drawable.x))
                }else{
                    binding.two.setImageDrawable(getDrawable(R.drawable.no_image))
                }


                if (gameModel?.three == "O"){
                    binding.three.setImageDrawable(getDrawable(R.drawable.o))
                }else if (gameModel?.three == "X"){
                    binding.three.setImageDrawable(getDrawable(R.drawable.x))
                }else{
                    binding.three.setImageDrawable(getDrawable(R.drawable.no_image))
                }

                if (gameModel?.four == "O"){
                    binding.four.setImageDrawable(getDrawable(R.drawable.o))
                }else if (gameModel?.four == "X"){
                    binding.four.setImageDrawable(getDrawable(R.drawable.x))
                }else{
                    binding.four.setImageDrawable(getDrawable(R.drawable.no_image))
                }

                if (gameModel?.five == "O"){
                    binding.five.setImageDrawable(getDrawable(R.drawable.o))
                }else if (gameModel?.five == "X"){
                    binding.five.setImageDrawable(getDrawable(R.drawable.x))
                }else{
                    binding.five.setImageDrawable(getDrawable(R.drawable.no_image))
                }

                if (gameModel?.six == "O"){
                    binding.six.setImageDrawable(getDrawable(R.drawable.o))
                }else if (gameModel?.six == "X"){
                    binding.six.setImageDrawable(getDrawable(R.drawable.x))
                }else{
                    binding.six.setImageDrawable(getDrawable(R.drawable.no_image))
                }

                if (gameModel?.seven == "O"){
                    binding.seven.setImageDrawable(getDrawable(R.drawable.o))
                }else if (gameModel?.seven == "X"){
                    binding.seven.setImageDrawable(getDrawable(R.drawable.x))
                }else{
                    binding.seven.setImageDrawable(getDrawable(R.drawable.no_image))
                }

                if (gameModel?.eight == "O"){
                    binding.eight.setImageDrawable(getDrawable(R.drawable.o))
                }else if (gameModel?.eight == "X"){
                    binding.eight.setImageDrawable(getDrawable(R.drawable.x))
                }else{
                    binding.eight.setImageDrawable(getDrawable(R.drawable.no_image))
                }

                if (gameModel?.nine == "O"){
                    binding.nine.setImageDrawable(getDrawable(R.drawable.o))
                }else if (gameModel?.nine == "X"){
                    binding.nine.setImageDrawable(getDrawable(R.drawable.x))
                }else{
                    binding.nine.setImageDrawable(getDrawable(R.drawable.no_image))
                }

                checkWinner()

            }
    }
}