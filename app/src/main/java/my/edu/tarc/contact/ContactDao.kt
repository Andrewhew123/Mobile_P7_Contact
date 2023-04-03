package my.tarc.mycontact

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface ContactDao { //DAO is to create the query to provide CRUD for Database

    //Provides CRUD
    @Query("SELECT * FROM contact ORDER BY name ASC")
    fun getAllContact(): LiveData<List<Contact>>

    @Insert(onConflict = OnConflictStrategy.IGNORE) //If insert the existing data, it will ignore and overwrite the data
    suspend fun insert(contact: Contact)

    @Delete
    suspend fun delete(contact: Contact)

    @Update
    suspend fun update(contact: Contact)
}