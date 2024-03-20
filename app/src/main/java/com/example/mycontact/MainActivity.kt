package com.example.mycontact

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mycontact.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private var _binding: ActivityMainBinding? = null
    private val binding: ActivityMainBinding get() = _binding!!
    private lateinit var repository: Repository
    private lateinit var presenter: Presenter
    private var contactAdapter = ContactAdapter()

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater, null, false)
        setContentView(binding.root)
        repository = Repository(LocalStorage(this))
        presenter = Presenter(this, repository)
        loadUiClickable()
        loadUi()
    }

    private fun loadUiClickable() {
        binding.fbAdd.setOnClickListener {
            presenter.openAddDialog()
        }
        contactAdapter.submitItemClickListener { data, position ->
            presenter.openUpdateDialog(data, position)
        }
        contactAdapter.submitItemLongClickListener { data ->
            presenter.openDeleteDialog(data)
        }
    }

    private fun loadUi() {
        binding.rw.adapter = contactAdapter
        binding.rw.layoutManager = LinearLayoutManager(this)
    }

    fun showUpdateDialog(data: ContactData, position: Int) {
        ContactDialog(data, this)
            .submitSaveListener {
                presenter.update(it, position)
            }.show()
    }

    fun showDeleteDialog(data: ContactData) {
        AlertDialog.Builder(this)
            .setTitle("Delete")
            .setMessage("${data.name} will delete")
            .setPositiveButton("delete") { d, _ ->
                presenter.delete(data)
                d.dismiss()
            }.setNegativeButton("close") { d, _ ->
                d.dismiss()
            }.create().show()
    }

    fun showAddDialog() {
        ContactDialog(null, this)
            .submitSaveListener {
                presenter.add(it)
            }.show()
    }

    fun submitList(ls: List<ContactData>) {
        contactAdapter.submitList(ls)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}