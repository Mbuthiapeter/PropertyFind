package com.friki.mbuthia.propertyfindapp.database;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.arch.persistence.room.migration.Migration;
import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

@Database(entities = {LocationEntry.class, ListingEntry.class}, version = 2, exportSchema = false)
public abstract class LocationDatabase extends RoomDatabase {
    private static final String LOG_TAG = LocationDatabase.class.getSimpleName();
    private static final Object LOCK = new Object();
    private static final String DATABASE_NAME = "locations";
    private static LocationDatabase sInstance;

    static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            database.execSQL("CREATE TABLE 'listing'('id' INTEGER NOT NULL, 'listingId' INTEGER NOT NULL, 'listing' TEXT, PRIMARY KEY ('id'))");
        }
    };

    //This method grants the access to the database object using a singleton pattern
    public static LocationDatabase getInstance(Context context) {
        if (sInstance == null) {
            synchronized (LOCK) {
               // Log.d(LOG_TAG, "Creating new database instance");
                sInstance = Room.databaseBuilder(context.getApplicationContext(),
                        LocationDatabase.class, LocationDatabase.DATABASE_NAME)
                        .addMigrations(MIGRATION_1_2)
                        .build();
            }
        }
       // Log.d(LOG_TAG, "Getting the database instance");
        return sInstance;
    }

    public abstract LocationDao locationDao();

    public abstract ListingDao listingDao();
}
