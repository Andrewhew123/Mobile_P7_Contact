package my.tarc.mycontact

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class ContactViewModel (application: Application): AndroidViewModel(application) {
    //LiveData gives us updated contacts when they change

    //Live Data and Repository connected

    var contactList : LiveData<List<Contact>> //Storing the data that is representing in the recycle view
    private val repository: ContactRepository
    var selectedIndex: Int = -1


    //ViewModel, LifeCycle, Repository

    init {
        //Get an instance of the DB and return the DAO
        val contactDao = ContactDatabase.getDatabase(application).contactDao()
        //Connect DAO with the Repository
        repository = ContactRepository(contactDao)
        //Pass a copy of data to the View Model
        contactList = repository.allContacts
    }

    fun addContact(contact: Contact) = viewModelScope.launch{
         repository.add(contact)
    }

    fun updateContact(contact: Contact) = viewModelScope.launch {
        repository.update(contact)
    }

    fun uploadContact(id: String) = viewModelScope.launch {
        repository.uploadContacts(id)
    }

    fun deleteContact(contact: Contact) = viewModelScope.launch {
        repository.delete(contact)
    }
}
