package com.example.wishlistapp

import android.app.Dialog
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.wishlistapp.ui.state.UiState
import java.io.File

class MainActivity : AppCompatActivity() {
    private lateinit var searchButton: Button
    private lateinit var editButton: Button

    private var currentUserId: String? = null
    private var currentUserLogin: String? = null

    private var authorizationDialog: Dialog? = null

    private var currentUserMenuItem: MenuItem? = null

    private val authViewModel: AuthViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        observeAuthState()

        setContentView(R.layout.activity_main)

        loadCurrentUser()

        searchButton = findViewById(R.id.search_button)
        searchButton.setOnClickListener {

        }

        editButton = findViewById(R.id.edit_button)
        editButton.setOnClickListener {

        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.menu, menu)

        currentUserMenuItem = menu.findItem(R.id.current_user)
        updateCurrentUserMenuItem()

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.current_user -> {
                true
            }

            R.id.register_login -> {
                if (!currentUserId.isNullOrEmpty()) {
                    Toast.makeText(
                        this,
                        "FIRST LOG OUT OF YOUR ACCOUNT",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    showAuthorizationDialog()
                }
                true
            }

            R.id.logout -> {
                currentUserId = null
                currentUserLogin = null
                saveCurrentUser()

                Toast.makeText(
                    this,
                    "LOGOUT",
                    Toast.LENGTH_SHORT
                ).show()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onPrepareOptionsMenu(menu: Menu): Boolean {
        updateCurrentUserMenuItem()
        return super.onPrepareOptionsMenu(menu)
    }

    private fun loadCurrentUser() {
        currentUserId = null
        currentUserLogin = null

        val file = File(filesDir, "current_user.txt")
        if (file.exists()) {
            val lines = file.readLines()
            if (lines.size >= 2) {
                val id = lines[0]
                val login = lines[1]

                if (!id.isNullOrEmpty() && !login.isNullOrEmpty()) {
                    currentUserId = id
                    currentUserLogin = login
                }
            }
        }
    }

    private fun saveCurrentUser() {
        val file = File(filesDir, "current_user.txt")

        if (currentUserId.isNullOrEmpty() || currentUserLogin.isNullOrEmpty()) {
            if (file.exists()) file.delete()
            return
        }

        file.bufferedWriter().use { writer ->
            writer.write(currentUserId!!)
            writer.newLine()
            writer.write(currentUserLogin!!)
        }

        updateCurrentUserMenuItem()
    }

    private fun updateCurrentUserMenuItem() {
        currentUserMenuItem?.let { menuItem ->
            val title = if (!currentUserLogin.isNullOrEmpty()) {
                currentUserLogin
            } else {
                getString(R.string.current_user_text)
            }
            menuItem.title = title
        }
    }

    private fun showAuthorizationDialog() {
        authorizationDialog = Dialog(this)
        authorizationDialog!!.setContentView(R.layout.authorization_dialog)
        authorizationDialog!!.setCancelable(false)
        authorizationDialog!!.setTitle("Authorization")

        val loginInput = authorizationDialog!!.findViewById<EditText>(R.id.login_input)
        val passwordInput = authorizationDialog!!.findViewById<EditText>(R.id.password_input)

        val registerButton = authorizationDialog!!.findViewById<Button>(R.id.register_button)
        registerButton.setOnClickListener {
            authViewModel.register(
                loginInput.text.toString(),
                passwordInput.text.toString()
            )
        }

        val loginButton = authorizationDialog!!.findViewById<Button>(R.id.login_button)
        loginButton.setOnClickListener {
            authViewModel.login(
                loginInput.text.toString(),
                passwordInput.text.toString()
            )
        }

        val closeButton = authorizationDialog!!.findViewById<Button>(R.id.close_dialog_button)
        closeButton.setOnClickListener {
            authorizationDialog!!.dismiss()
        }

        authorizationDialog!!.show()

    }

    private fun observeAuthState() {
        lifecycleScope.launchWhenStarted {
            authViewModel.authState.collect { state ->
                when (state) {
                    is UiState.Success -> {
                        val userId = state.data

                        if (userId.isNullOrEmpty()) {
                            Toast.makeText(
                                this@MainActivity,
                                "Authorization failed",
                                Toast.LENGTH_SHORT
                            ).show()
                            return@collect
                        }

                        currentUserId = userId
                        currentUserLogin = authorizationDialog
                            ?.findViewById<EditText>(R.id.login_input)
                            ?.text
                            ?.toString()

                        saveCurrentUser()

                        Toast.makeText(
                            this@MainActivity,
                            "Authorization successful",
                            Toast.LENGTH_SHORT
                        ).show()

                        authorizationDialog?.dismiss()
                    }


                    is UiState.Error -> {
                        Toast.makeText(
                            this@MainActivity,
                            state.message,
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                    else -> Unit
                }
            }
        }
    }
}