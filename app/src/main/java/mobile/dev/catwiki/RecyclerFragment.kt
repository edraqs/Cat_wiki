package mobile.dev.catwiki

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.content_main.*


class RecyclerFragment : Fragment(),OnItemClickListener{

    lateinit var recyclerView: RecyclerView
    lateinit var gridLayoutManager: GridLayoutManager
    lateinit var viewAdapter: BreedAdapter
    lateinit var viewManager: RecyclerView.LayoutManager
    lateinit var listCatBreeds: ArrayList<CatBreed>
    lateinit var thisView: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.recycler_fragment, container, false)
        //val toolbar = view.findViewById<Toolbar>(R.id.toolbar)
        //(requireActivity() as AppCompatActivity).setSupportActionBar(toolbar)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        listCatBreeds = arguments?.getSerializable("LIST") as ArrayList<CatBreed>
        viewManager = LinearLayoutManager(context)
        viewAdapter = BreedAdapter(listCatBreeds, this)

        recyclerView = view.findViewById(R.id.cats_recycler_view)
        gridLayoutManager = GridLayoutManager(context, 2, LinearLayoutManager.VERTICAL, false)
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
        val intent = Intent(context, BreedInfoActivity::class.java)
        intent.putExtra("BREEDNAME", item.name)
        intent.putExtra("BREEDSIZE", item.size)
        intent.putExtra("BREEDCOAT", item.coat)
        intent.putExtra("BREEDCOLOR", item.color)
        intent.putExtra("BREEDLIFESPAN", item.lifespan)
        intent.putExtra("BREEDIMAGE", item.image.toString())
        startActivity(intent)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu, menu)

        val searchItem = menu.findItem(R.id.action_search)
        val searchView = searchItem?.actionView as SearchView
        searchView.setQueryHint("Search View Hint")

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextChange(newText: String): Boolean {
                //If you want to filter each time a char is typed
                viewAdapter.filter.filter(newText)
                return false
            }

            override fun onQueryTextSubmit(query: String): Boolean {
                //If you want to filter by tapping enter
                return false
            }
        })
    }

}