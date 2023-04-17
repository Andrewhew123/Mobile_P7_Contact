package my.tarc.mycontact

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import com.google.firebase.ktx.Firebase
import com.google.firebase.database.ktx.database

class ContactRepository(private val contactDao: ContactDao){

    //Middle man between viewModel and Database (Can get from local database or cloud storage)
    //Cloud Storage: The repository take the data from cloud storage and then store the data into the database

    //Room execute all queries on a separate thread
    val allContacts: LiveData<List<Contact>> = contactDao.getAllContact()

    //Suspend: Do in the background / A Simple Function Which Can Be Paused And Resumed Later In Your Code

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun add(contact: Contact){
        contactDao.insert(contact)
    }

    @WorkerThread
    suspend fun delete(contact: Contact){
        contactDao.delete(contact)
    }

    @WorkerThread
    suspend fun update(contact: Contact){
        contactDao.update(contact)
    }

    @WorkerThread
    suspend fun uploadContacts(id: String){
        //TODO: Sync local contact to the Cloud Database
        if (allContacts.isInitialized) {
            val database = Firebase.database("https://contact-c7ad7-default-rtdb.asia-southeast1.firebasedatabase.app/").reference

            if (!allContacts.value!!.isEmpty()) {
                allContacts.value!!.forEach {
                    database.child(id).child(it.phone).setValue(it)
                }
            }
        }
    }
}