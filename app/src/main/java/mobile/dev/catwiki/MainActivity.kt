package mobile.dev.catwiki

import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import java.io.IOException
import java.io.InputStream

class MainActivity : AppCompatActivity(), OnItemClickListener {

    private lateinit var recyclerView: RecyclerView
    private lateinit var gridLayoutManager: GridLayoutManager
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager
    private lateinit var listCatBreeds: ArrayList<CatBreed>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        listCatBreeds = ArrayList()
        listCatBreeds = setDataInList("")

        viewManager = LinearLayoutManager(this)
        viewAdapter = BreedAdapter(listCatBreeds, this)

        recyclerView = findViewById<RecyclerView>(R.id.cats_recycler_view)
        gridLayoutManager = GridLayoutManager(applicationContext, 2, LinearLayoutManager.VERTICAL, false)


        recyclerView.apply {
            // use this setting to improve performance if you know that changes
            // in content do not change the layout size of the RecyclerView
            setHasFixedSize(true)

            // use a linear layout manager
            //layoutManager = viewManager
            layoutManager = gridLayoutManager

            // specify an viewAdapter (see also next example)
            adapter = viewAdapter

        }


    }

    override fun onItemClick(item: CatBreed, position: Int) {
        val intent = Intent(this, BreedInfoActivity::class.java)
        intent.putExtra("BREEDNAME", item.name)
        intent.putExtra("BREEDSIZE", item.size)
        intent.putExtra("BREEDCOAT", item.coat)
        intent.putExtra("BREEDCOLOR", item.color)
        intent.putExtra("BREEDLIFESPAN", item.lifespan)
        intent.putExtra("BREEDIMAGE", item.image.toString())
        startActivity(intent)
    }

    private fun setDataInList(path: String): ArrayList<CatBreed> {
        val list: Array<String>?
        val listCatBreeds = ArrayList<CatBreed>()
        try {
            list = assets.list(path)
            if (list!!.size > 0) {
                // This is a folder
                for (file in list) {
                        //Parsing json
                        if(file.endsWith(".json")) {
                            val jsonFileString = getJsonDataFromAsset(applicationContext, file)
                            val gson = Gson()
                            val cat = gson.fromJson(jsonFileString, CatBreed::class.java)
                            val filename = file.split('.')[0]

                            val resID = resources.getIdentifier(filename, "drawable", packageName)

                            val breed = CatBreed(cat.name, cat.size, cat.coat, cat.color, cat.lifespan, resID)
                            listCatBreeds.add(breed)

                        }
                }
            }
        } catch (e: IOException) {}
        return listCatBreeds
    }
}
