package mobile.dev.catwiki

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity(), OnItemClickListener {

    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager
    private lateinit var listCatBreeds: ArrayList<CatBreed>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var breed1 = CatBreed("Abyssinian cat","Small to medium, with males weighing 7 to 10 pounds and females weighing 6 to 8 pounds","Medium","Ruddy, red, blue, fawn","9 to 15 years")
        var breed2 = CatBreed("American Bobtail","Medium to large, with males weighing 12 to 16 pounds and females weighing 7 to 11 pounds ","Medium (shorthair) and long (longhair) ","White, black, blue, red, cream, chocolate, lavender, cinnamon, fawn, plus various patterns and shadings","")
        var breed3 = CatBreed("Bengal","","","Balinese - seal point, chocolate point, blue point and lilac point, plus various patterns and shadings; Javanese – red point, cream point and seal point, plus various patterns and shadings ","")
        var listCatBreeds = ArrayList<CatBreed>()
        listCatBreeds.add(breed1)
        listCatBreeds.add(breed2)
        listCatBreeds.add(breed3)
        viewManager = LinearLayoutManager(this)
        viewAdapter = BreedAdapter(listCatBreeds, this)

        recyclerView = findViewById<RecyclerView>(R.id.cats_recycler_view).apply {
            // use this setting to improve performance if you know that changes
            // in content do not change the layout size of the RecyclerView
            setHasFixedSize(true)

            // use a linear layout manager
            layoutManager = viewManager

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
        //intent.putExtra("BREEDIMAGE", item.image.toString())
        startActivity(intent)
    }

}
