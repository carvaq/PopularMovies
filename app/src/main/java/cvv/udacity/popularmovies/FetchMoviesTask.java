package cvv.udacity.popularmovies;

import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Caro Vaquero
 * Date: 12.06.2016
 * Project: PopularMovies
 */
public class FetchMoviesTask extends AsyncTask<String, Void, List<Movie>> {
    private final String TAG = FetchMoviesTask.class.getSimpleName();
    private Context mContext;
    private OnMovieFetchListener mOnMovieFetchListener;

    public FetchMoviesTask(Context context, OnMovieFetchListener onMovieFetchListener) {
        mContext = context;
        mOnMovieFetchListener = onMovieFetchListener;
    }

    @Nullable
    @Override
    protected List<Movie> doInBackground(String... params) {
        if (params != null) {
            return fetchMovies(mContext.getString(R.string.pref_sorting_popular).equals(params[0]));
        } else {
            return null;
        }
    }

    @Override
    protected void onPostExecute(List<Movie> movies) {
        super.onPostExecute(movies);
        mOnMovieFetchListener.onMoviesFetched(movies);
    }

    @Nullable
    private List<Movie> fetchMovies(boolean sortOrderPopular) {
        BufferedReader reader = null;
        HttpURLConnection urlConnection = null;
        String movieJsonStr = "{\"page\":1,\"results\":[{\"poster_path\":\"\\/sM33SANp9z6rXW8Itn7NnG1GOEs.jpg\",\"adult\":false,\"overview\":\"In the animal city of Zootopia, a fast-talking fox who's trying to make it big goes on the run when he's framed for a crime he didn't commit. Zootopia's top cop, a self-righteous rabbit, is hot on his tail, but when both become targets of a conspiracy, they're forced to team up and discover even natural enemies can become best friends.\",\"release_date\":\"2016-02-11\",\"genre_ids\":[16,12,10751,35],\"id\":269149,\"original_title\":\"Zootopia\",\"original_language\":\"en\",\"title\":\"Zootopia\",\"backdrop_path\":\"\\/mhdeE1yShHTaDbJVdWyTlzFvNkr.jpg\",\"popularity\":90.776684,\"vote_count\":1099,\"video\":false,\"vote_average\":7.51},{\"poster_path\":\"\\/inVq3FRqcYIRl2la8iZikYYxFNR.jpg\",\"adult\":false,\"overview\":\"Based upon Marvel Comics’ most unconventional anti-hero, DEADPOOL tells the origin story of former Special Forces operative turned mercenary Wade Wilson, who after being subjected to a rogue experiment that leaves him with accelerated healing powers, adopts the alter ego Deadpool. Armed with his new abilities and a dark, twisted sense of humor, Deadpool hunts down the man who nearly destroyed his life.\",\"release_date\":\"2016-02-09\",\"genre_ids\":[28,12,35,10749],\"id\":293660,\"original_title\":\"Deadpool\",\"original_language\":\"en\",\"title\":\"Deadpool\",\"backdrop_path\":\"\\/nbIrDhOtUpdD9HKDBRy02a8VhpV.jpg\",\"popularity\":76.854126,\"vote_count\":3765,\"video\":false,\"vote_average\":7.15},{\"poster_path\":\"\\/pYzHflb8QgQszkR4Ku8mWrJAYfA.jpg\",\"adult\":false,\"overview\":\"The life of a bank manager is turned upside down when a friend from his past manipulates him into faking his own death and taking off on an adventure.\",\"release_date\":\"2016-05-27\",\"genre_ids\":[35],\"id\":389053,\"original_title\":\"The Do-Over\",\"original_language\":\"en\",\"title\":\"The Do-Over\",\"backdrop_path\":\"\\/9DL2HHVZV8SWUhFslIjKwfpi2uV.jpg\",\"popularity\":40.380919,\"vote_count\":102,\"video\":false,\"vote_average\":5.7},{\"poster_path\":\"\\/zSouWWrySXshPCT4t3UKCQGayyo.jpg\",\"adult\":false,\"overview\":\"Since the dawn of civilization, he was worshipped as a god. Apocalypse, the first and most powerful mutant from Marvel’s X-Men universe, amassed the powers of many other mutants, becoming immortal and invincible. Upon awakening after thousands of years, he is disillusioned with the world as he finds it and recruits a team of powerful mutants, including a disheartened Magneto, to cleanse mankind and create a new world order, over which he will reign. As the fate of the Earth hangs in the balance, Raven with the help of Professor X must lead a team of young X-Men to stop their greatest nemesis and save mankind from complete destruction.\",\"release_date\":\"2016-05-18\",\"genre_ids\":[28,12,878,14],\"id\":246655,\"original_title\":\"X-Men: Apocalypse\",\"original_language\":\"en\",\"title\":\"X-Men: Apocalypse\",\"backdrop_path\":\"\\/oQWWth5AOtbWG9o8SCAviGcADed.jpg\",\"popularity\":35.122916,\"vote_count\":972,\"video\":false,\"vote_average\":6.06},{\"poster_path\":\"\\/weUSwMdQIa3NaXVzwUoIIcAi85d.jpg\",\"adult\":false,\"overview\":\"Thirty years after defeating the Galactic Empire, Han Solo and his allies face a new threat from the evil Kylo Ren and his army of Stormtroopers.\",\"release_date\":\"2015-12-15\",\"genre_ids\":[28,12,878,14],\"id\":140607,\"original_title\":\"Star Wars: The Force Awakens\",\"original_language\":\"en\",\"title\":\"Star Wars: The Force Awakens\",\"backdrop_path\":\"\\/c2Ax8Rox5g6CneChwy1gmu4UbSb.jpg\",\"popularity\":28.180468,\"vote_count\":4290,\"video\":false,\"vote_average\":7.56},{\"poster_path\":\"\\/m4ZfuML89Rq8gbRGdm9ncSibjlx.jpg\",\"adult\":false,\"overview\":\"An American Ambassador is killed during an attack at a U.S. compound in Libya as a security team struggles to make sense out of the chaos.\",\"release_date\":\"2016-01-13\",\"genre_ids\":[18,28,53,10752],\"id\":300671,\"original_title\":\"13 Hours: The Secret Soldiers of Benghazi\",\"original_language\":\"en\",\"title\":\"13 Hours: The Secret Soldiers of Benghazi\",\"backdrop_path\":\"\\/hY8myWhjSOUeeiPskhOfwwgaCwc.jpg\",\"popularity\":27.745184,\"vote_count\":202,\"video\":false,\"vote_average\":6.17},{\"poster_path\":\"\\/uPqAW07bGoljf3cmT5gecdOvVol.jpg\",\"adult\":false,\"overview\":\"A common thief joins a mythical god on a quest through Egypt.\",\"release_date\":\"2016-02-25\",\"genre_ids\":[28,12,14],\"id\":205584,\"original_title\":\"Gods of Egypt\",\"original_language\":\"en\",\"title\":\"Gods of Egypt\",\"backdrop_path\":\"\\/xy74anT8ZGhUIGVoalnHrmRDHbQ.jpg\",\"popularity\":24.42853,\"vote_count\":390,\"video\":false,\"vote_average\":4.96},{\"poster_path\":\"\\/5vfRMplGxOzMiJu0FGFCA1ic44Q.jpg\",\"adult\":false,\"overview\":\"The Coast Guard makes a daring rescue attempt off the coast of Cape Cod after a pair of oil tankers are destroyed during a blizzard in 1952.\",\"release_date\":\"2016-01-25\",\"genre_ids\":[28,18,36,53],\"id\":300673,\"original_title\":\"The Finest Hours\",\"original_language\":\"en\",\"title\":\"The Finest Hours\",\"backdrop_path\":\"\\/a6VvlnIRTJTPUnyf1tIV9kEJO27.jpg\",\"popularity\":23.648444,\"vote_count\":164,\"video\":false,\"vote_average\":5.68},{\"poster_path\":\"\\/s7OVVDszWUw79clca0durAIa6mw.jpg\",\"adult\":false,\"overview\":\"16-year-old Cassie Sullivan tries to survive in a world devastated by the waves of an alien invasion that has already decimated the population and knocked mankind back to the Stone Age.\",\"release_date\":\"2016-01-14\",\"genre_ids\":[28,12,878],\"id\":299687,\"original_title\":\"The 5th Wave\",\"original_language\":\"en\",\"title\":\"The 5th Wave\",\"backdrop_path\":\"\\/4obYMyIZALl7svc83fn7KelTZDp.jpg\",\"popularity\":23.178402,\"vote_count\":738,\"video\":false,\"vote_average\":5.19},{\"poster_path\":\"\\/ckrTPz6FZ35L5ybjqvkLWzzSLO7.jpg\",\"adult\":false,\"overview\":\"The peaceful realm of Azeroth stands on the brink of war as its civilization faces a fearsome race of invaders: orc warriors fleeing their dying home to colonize another. As a portal opens to connect the two worlds, one army faces destruction and the other faces extinction. From opposing sides, two heroes are set on a collision course that will decide the fate of their family, their people, and their home.\",\"release_date\":\"2016-05-25\",\"genre_ids\":[28,12,14],\"id\":68735,\"original_title\":\"Warcraft\",\"original_language\":\"en\",\"title\":\"Warcraft\",\"backdrop_path\":\"\\/5SX2rgKXZ7NVmAJR5z5LprqSXKa.jpg\",\"popularity\":23.115489,\"vote_count\":194,\"video\":false,\"vote_average\":6.7},{\"poster_path\":\"\\/tcYN5N1XdLzdWY1BANkXqOshHOf.jpg\",\"adult\":false,\"overview\":\"Set in a near future, technology-reliant society that pits man against killing machines. Against this backdrop an elite army unit is helicoptered to a remote, off-the-grid island training facility. What starts out as a simple training exercise for Captain Bukes and his tight-knit unit, descends into a terrifying battle to the death, as the marines discover the island is overrun by an enemy that transcends the human concept of evil.\",\"release_date\":\"2016-05-13\",\"genre_ids\":[28,878,27],\"id\":384798,\"original_title\":\"Kill Command\",\"original_language\":\"en\",\"title\":\"Kill Command\",\"backdrop_path\":\"\\/oBdlXkesUPJvAvVemVjMx5ZbJ7Q.jpg\",\"popularity\":22.562682,\"vote_count\":54,\"video\":false,\"vote_average\":5.14},{\"poster_path\":\"\\/pigMBBhYWIr9RZECHgMNmaOO0TS.jpg\",\"adult\":false,\"overview\":\"Oooh Hollywood! The glitz, the glam...the Headless Horseman?! Join your favorite K-9 sleuth, Scooby-Doo, along with Shaggy and the Mystery Inc. gang as they find themselves in a tinsel-town twist! While on a VIP tour of the legendary Brickton Studios, Scooby and friends get a first-hand experience of the rumored hauntings when classic movie monsters drop in for a creepy casting call. There's only one way to solve this thriller and save the aging studio - it's Mystery Inc's movie magic time! Grab your Scooby Snacks and hop in the director's chair for this all-new LEGO® movie!\",\"release_date\":\"2016-01-28\",\"genre_ids\":[16,10751],\"id\":392536,\"original_title\":\"Lego Scooby-Doo!: Haunted Hollywood\",\"original_language\":\"en\",\"title\":\"Lego Scooby-Doo!: Haunted Hollywood\",\"backdrop_path\":\"\\/t9h9ryrSD12qokh1trcZzOQNAI9.jpg\",\"popularity\":21.068729,\"vote_count\":16,\"video\":false,\"vote_average\":4.97},{\"poster_path\":\"\\/foEAe9AlPfQmaVngziK4vbFBnP3.jpg\",\"adult\":false,\"overview\":\"Wrongfully accused and on the run, a top MI6 assassin joins forces with his long-lost, football hooligan brother to save the world from a sinister plot.\",\"release_date\":\"2016-02-24\",\"genre_ids\":[28,35],\"id\":267193,\"original_title\":\"Grimsby\",\"original_language\":\"en\",\"title\":\"Grimsby\",\"backdrop_path\":\"\\/s04T6oIdglrbsZ1WPK2p20J9jag.jpg\",\"popularity\":20.643742,\"vote_count\":170,\"video\":false,\"vote_average\":5.17},{\"poster_path\":\"\\/fqe8JxDNO8B8QfOGTdjh6sPCdSC.jpg\",\"adult\":false,\"overview\":\"Bounty hunters seek shelter from a raging blizzard and get caught up in a plot of betrayal and deception.\",\"release_date\":\"2015-12-25\",\"genre_ids\":[80,18,9648,37],\"id\":273248,\"original_title\":\"The Hateful Eight\",\"original_language\":\"en\",\"title\":\"The Hateful Eight\",\"backdrop_path\":\"\\/sSvgNBeBNzAuKl8U8sP50ETJPgx.jpg\",\"popularity\":19.244678,\"vote_count\":1677,\"video\":false,\"vote_average\":7.3},{\"poster_path\":\"\\/vdK1f9kpY5QEwrAiXs9R7PlerNC.jpg\",\"adult\":false,\"overview\":\"A dramatic thriller based on the incredible true David vs. Goliath story of American immigrant Dr. Bennet Omalu, the brilliant forensic neuropathologist who made the first discovery of CTE, a football-related brain trauma, in a pro player and fought for the truth to be known. Omalu's emotional quest puts him at dangerous odds with one of the most powerful institutions in the world.\",\"release_date\":\"2015-11-12\",\"genre_ids\":[18],\"id\":321741,\"original_title\":\"Concussion\",\"original_language\":\"en\",\"title\":\"Concussion\",\"backdrop_path\":\"\\/vu82PVUjEaAmrxBdeH8hbx2ZBxy.jpg\",\"popularity\":19.144738,\"vote_count\":305,\"video\":false,\"vote_average\":6.84},{\"poster_path\":\"\\/5N20rQURev5CNDcMjHVUZhpoCNC.jpg\",\"adult\":false,\"overview\":\"Following the events of Age of Ultron, the collective governments of the world pass an act designed to regulate all superhuman activity. This polarizes opinion amongst the Avengers, causing two factions to side with Iron Man or Captain America, which causes an epic battle between former allies.\",\"release_date\":\"2016-04-27\",\"genre_ids\":[28,53,878],\"id\":271110,\"original_title\":\"Captain America: Civil War\",\"original_language\":\"en\",\"title\":\"Captain America: Civil War\",\"backdrop_path\":\"\\/m5O3SZvQ6EgD5XXXLPIP1wLppeW.jpg\",\"popularity\":19.012779,\"vote_count\":1867,\"video\":false,\"vote_average\":6.91},{\"poster_path\":\"\\/f1AultcaeQnzMUVaqSTVOhRtwOj.jpg\",\"adult\":false,\"overview\":\"\",\"release_date\":\"2015-12-31\",\"genre_ids\":[35],\"id\":324465,\"original_title\":\"Até Que A Sorte Nos Separe 3 - A Falência Final\",\"original_language\":\"pt\",\"title\":\"Até Que A Sorte Nos Separe 3 - A Falência Final\",\"backdrop_path\":\"\\/6h0rQiXPPCrn8iYvVoZnKAdrHkp.jpg\",\"popularity\":18.326951,\"vote_count\":11,\"video\":false,\"vote_average\":7.05},{\"poster_path\":\"\\/is6QqgiPQlI3Wmk0bovqUFKM56B.jpg\",\"adult\":false,\"overview\":\"A young boxer is taken under the wing of a mob boss after his mother dies and his father is run out of town for being an abusive alcoholic.\",\"release_date\":\"2016-05-20\",\"genre_ids\":[18],\"id\":368596,\"original_title\":\"Back in the Day\",\"original_language\":\"en\",\"title\":\"Back in the Day\",\"backdrop_path\":\"\\/yySmUG29VgDdCROb9eer9L2kkKX.jpg\",\"popularity\":17.874564,\"vote_count\":18,\"video\":false,\"vote_average\":4.5},{\"poster_path\":\"\\/jjBgi2r5cRt36xF6iNUEhzscEcb.jpg\",\"adult\":false,\"overview\":\"Twenty-two years after the events of Jurassic Park, Isla Nublar now features a fully functioning dinosaur theme park, Jurassic World, as originally envisioned by John Hammond.\",\"release_date\":\"2015-06-09\",\"genre_ids\":[28,12,878,53],\"id\":135397,\"original_title\":\"Jurassic World\",\"original_language\":\"en\",\"title\":\"Jurassic World\",\"backdrop_path\":\"\\/dkMD5qlogeRMiEixC4YNPUvax2T.jpg\",\"popularity\":17.450506,\"vote_count\":4537,\"video\":false,\"vote_average\":6.63},{\"poster_path\":\"\\/aeiVxTSTeGJ2ICf1iSDXkF3ivZp.jpg\",\"adult\":false,\"overview\":\"Waking up from a car accident, a young woman finds herself in the basement of a man who says he's saved her life from a chemical attack that has left the outside uninhabitable.\",\"release_date\":\"2016-03-10\",\"genre_ids\":[18,27,9648,53],\"id\":333371,\"original_title\":\"10 Cloverfield Lane\",\"original_language\":\"en\",\"title\":\"10 Cloverfield Lane\",\"backdrop_path\":\"\\/qEu2EJBQUNFx5mSKOCItwZm74ZE.jpg\",\"popularity\":17.196804,\"vote_count\":503,\"video\":false,\"vote_average\":6.49}],\"total_results\":19704,\"total_pages\":986}";

        try {
            String path = sortOrderPopular ? ApiHelper.PATH_POPULAR : ApiHelper.PATH_TOP_RATED;
            URL url = new URL(String.format(ApiHelper.API_BASE_URL, path, ApiHelper.APP_KEY));
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");

            urlConnection.connect();

            //From sunshine project
            int responseCode = urlConnection.getResponseCode();
            if (urlConnection.getResponseCode() >= 300 || responseCode < 200) {
                Log.e(TAG, "Request was unsuccessful. Error " + responseCode);
                return null;
            }

            InputStream inputStream = urlConnection.getInputStream();
            StringBuilder builder = new StringBuilder();
            if (inputStream == null) {
                return null;
            }
            reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null) {
                builder.append(line + "\n");
            }

            if (builder.length() == 0) {
                return null;
            }

            movieJsonStr = builder.toString();
            Log.d(TAG, "fetchMovies: " + movieJsonStr);

        } catch (MalformedURLException e) {
            Log.e(TAG, "doInBackground: ", e);
        } catch (IOException e) {
            Log.e(TAG, "doInBackground: IOException", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException ignore) {
            }

        }

        try {
            return parseMoviesJson(movieJsonStr);
        } catch (JSONException e) {
            Log.e(TAG, "fetchMovies: Could not parse JSON", e);
            return null;
        }
    }

    @Nullable
    private List<Movie> parseMoviesJson(String json) throws JSONException {
        if (TextUtils.isEmpty(json)) {
            return null;
        }
        final String MOVIE_LIST_KEY = "results";
        List<Movie> movies = new ArrayList<>();
        JSONObject responseJson = new JSONObject(json);
        JSONArray jsonArray = responseJson.getJSONArray(MOVIE_LIST_KEY);

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            movies.add(new Movie(jsonObject));
        }

        return movies;
    }
}
