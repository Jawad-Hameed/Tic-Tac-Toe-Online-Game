package com.jawadjk.tictactoe

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.firestore.FirebaseFirestore
import com.jawadjk.tictactoe.databinding.ActivityHomeBinding
import java.security.ProtectionDomain

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding
    private lateinit var db: FirebaseFirestore
    private lateinit var matchList: ArrayList<MatchModel>
    private lateinit var progressDialog: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = FirebaseFirestore.getInstance()
        matchList = ArrayList<MatchModel>()

        binding.continueButton.setOnClickListener {

            val name = binding.editTextPlayerName.text.toString()

            if (name.isNotEmpty()){
                getFirestoreData(name)
            }else{
                Toast.makeText(this@HomeActivity, "Enter Player Name", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun createNewMatch(name: String) {
        val id = db.collection("Game").document().id
        val matchModel = MatchModel(id, false, name, "")

        db.collection("Game").document(id).set(matchModel)
            .addOnSuccessListener {
                progressDialog()

                // Listen for changes in the document for real-time updates
                db.collection("Game").document(id)
                    .addSnapshotListener { value, error ->
                        if (error != null) {
                            // Handle errors
                            return@addSnapshotListener
                        }
                        val match = value?.toObject(MatchModel::class.java)

                        if (match != null && match.matched) {
                            // The opponent is found
                            progressDialog.dismiss()
                            // Perform further actions
                            val intent = Intent(this@HomeActivity, MainActivity::class.java)
                            intent.putExtra("id", id)
                            intent.putExtra("name", name)
                            startActivity(intent)
                            finish()
                        }
                    }
            }
            .addOnFailureListener {
                // Handle failure scenario
            }
    }

    private fun findPlayer(name: String) {
        var matchFound = false

        for (matchDocument in matchList) {
            if (!matchDocument.matched && matchDocument.firstPlayer != name) {
                matchFound = true
                val matchModel = MatchModel(matchDocument.id, true, matchDocument.firstPlayer, name)
                db.collection("Game").document(matchDocument.id).set(matchModel)
                    .addOnSuccessListener {
                        progressDialog.dismiss()
                        val intent = Intent(this@HomeActivity, MainActivity::class.java)
                        intent.putExtra("id", matchDocument.id)
                        intent.putExtra("name", name)
                        startActivity(intent)
                        finish()
                    }
                    .addOnFailureListener {
                        // Handle failure scenario
                    }

                break
            }
        }

        if (!matchFound) {
            createNewMatch(name)
        }
    }


    private fun getFirestoreData(name: String) {
        progressDialog()
        db.collection("Game").get().addOnSuccessListener {
            val matchModel = it.toObjects(MatchModel::class.java)
            matchList.addAll(matchModel)
            findPlayer(name)
        }
    }
    private fun progressDialog(){
        progressDialog = ProgressDialog(this)
        progressDialog.setMessage("Loading...")
        progressDialog.setCancelable(false)
        progressDialog.show()
    }
}

