package cvv.udacity.popularmovies;

import android.app.Application;

import com.raizlabs.android.dbflow.config.FlowConfig;
import com.raizlabs.android.dbflow.config.FlowManager;

/**
 * Created by Caro Vaquero
 * Date: 26.06.2016
 * Project: PopularMovies
 */
public class PopularMoviesApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        FlowManager.init(new FlowConfig.Builder(getApplicationContext())
                .openDatabasesOnInit(true)
                .build());
    }
}
