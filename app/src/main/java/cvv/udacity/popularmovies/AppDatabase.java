package cvv.udacity.popularmovies;

import com.raizlabs.android.dbflow.annotation.Database;

@Database(name = AppDatabase.NAME, version = AppDatabase.VERSION, foreignKeysSupported = false)
public class AppDatabase {

    public static final String NAME = "PopularMovies";

    public static final int VERSION = 1;
}