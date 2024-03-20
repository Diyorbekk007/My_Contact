package com.example.mycontact

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import com.example.mycontact.databinding.DialogContactBinding
import java.util.Calendar

class ContactDialog(private var data: ContactData?, context: Context) {
    private var _binding: DialogContactBinding? = DialogContactBinding.inflate(
        LayoutInflater.from(context),
        null, false
    )
    private val binding: DialogContactBinding get() = _binding!!
    private val dialog = AlertDialog.Builder(context).setView(binding.root).create()
    private var saveListener: (data: ContactData) -> Unit = {}

    init {
        binding.btnAdd.setOnClickListener {
            val contactId = data?.id ?: Calendar.getInstance().timeInMillis.toString()
            val name = binding.etName.text.toString()
            val phone ="+998"+binding.etPhone.text.toString()
            val type = when {
                binding.rbMen.isChecked -> Gender.MEN
                binding.rbWomen.isChecked -> Gender.WOMEN
                else -> Gender.UNKNOWN
            }
            saveListener(ContactData(id = contactId, name = name, phone = phone, gender = type))
            dialog.dismiss()
        }
        dialog.setOnDismissListener {
            _binding = null
        }
        binding.btnCancel.setOnClickListener {
            dialog.dismiss()
        }
        data?.let {
            binding.etName.setText(it.name)
            binding.etPhone.setText(it.phone.substring(4))
            binding.rbMen.isChecked = it.gender == Gender.MEN
            binding.rbWomen.isChecked = it.gender == Gender.WOMEN
        }

    }

    fun show() {
        dialog.show()
    }

    fun submitSaveListener(listener: (data: ContactData) -> Unit): ContactDialog {
        saveListener = listener
        return this
    }
}