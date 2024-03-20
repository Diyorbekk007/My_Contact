package com.example.mycontact

class Presenter(
    private val view: MainActivity,
    private val repository: Repository
) {
    init {
        submitList()
    }

    fun openDeleteDialog(data: ContactData) {
        view.showDeleteDialog(data)
    }

    fun openUpdateDialog(data: ContactData, position: Int) {
        view.showUpdateDialog(data, position)
    }

    fun openAddDialog() {
        view.showAddDialog()
    }

    fun update(data: ContactData, position: Int) {
        repository.updateData(data, position)
        submitList()
    }

    fun add(data: ContactData) {
        repository.addContact(data)
        submitList()
    }


    fun delete(data: ContactData) {
        repository.deleteData(data)
        submitList()
    }

    private fun submitList() {
        view.submitList(repository.getList())
    }
}