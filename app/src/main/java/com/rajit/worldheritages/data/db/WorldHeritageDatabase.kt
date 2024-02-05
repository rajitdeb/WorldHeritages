package com.rajit.worldheritages.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.rajit.worldheritages.data.db.dao.HeritageDao
import com.rajit.worldheritages.data.model.HeritageEntity
import com.rajit.worldheritages.domain.repository.Repository
import com.rajit.worldheritages.util.Constants
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

@Database(entities = [HeritageEntity::class], version = 1, exportSchema = false)
abstract class WorldHeritageDatabase: RoomDatabase() {

    abstract fun heritageDao(): HeritageDao

    // Custom Implementation of Singleton DB Instance Creation
    companion object: KoinComponent {

        // repository instance injected by koin
        val repository: Repository by inject()

        @Volatile
        private var INSTANCE: WorldHeritageDatabase? = null

        @OptIn(DelicateCoroutinesApi::class)
        fun getDatabase(context: Context): WorldHeritageDatabase {

            val tempInstance = INSTANCE

            if(tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context,
                    WorldHeritageDatabase::class.java,
                    Constants.DB_NAME
                ).addCallback(object: Callback() {
                    override fun onCreate(db: SupportSQLiteDatabase) {
                        super.onCreate(db)

                        // On Successful DB Creation, Insert data into DB from JSON file
                        GlobalScope.launch {
                            withContext(Dispatchers.IO) {
                                repository.insertAllHeritages()
                            }
                        }
                    }
                }).build()

                INSTANCE = instance

                return instance
            }

        }

    }

}