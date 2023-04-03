package my.tarc.mycontact

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database (entities = arrayOf(Contact::class), version = 1, exportSchema = false)  //Insert new data class
abstract class ContactDatabase: RoomDatabase() {
    abstract fun contactDao(): ContactDao

    companion object{
        //Singleton prevents multiple instances of database opening at the same time

        //Only refer to one that already exists (Database)

        @Volatile //Volatile: It can be destroyed, after the database store into non-permanently storage
        private var INSTANCE: ContactDatabase? = null

        fun getDatabase(context: Context) : ContactDatabase{ //As long the context is still running, then it will still run the instance
            val tempInstance = INSTANCE
            if(tempInstance != null){
                return tempInstance
            }

            synchronized(this){ //synchronized: if didnt have database, it wont proceed
                val instance = Room.databaseBuilder( //Group of the data call the database
                    context.applicationContext, //All activity/fragment can access to the database
                    ContactDatabase::class.java, //Database Name
                    "contact_db"
                ).build()

                INSTANCE = instance //Pass the database to Global variable instance
                return instance
            }
        }
    }
}