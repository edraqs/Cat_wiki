package mobile.dev.catwiki

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.navigation.NavigationView
import com.google.gson.Gson
import java.io.IOException
import java.io.Serializable

class MainActivity : AppCompatActivity(), OnItemClickListener, NavigationView.OnNavigationItemSelectedListener {

    lateinit var recyclerView: RecyclerView
    lateinit var gridLayoutManager: GridLayoutManager
    lateinit var viewAdapter: BreedAdapter
    lateinit var viewManager: RecyclerView.LayoutManager
    lateinit var listCatBreeds: ArrayList<CatBreed>
    lateinit var toolbar: Toolbar
    lateinit var drawerLayout: DrawerLayout
    lateinit var navView: NavigationView
    private val recyclerFragment = RecyclerFragment()
    private val infoFragment = InfoFragment()
    private val fragmentManager = supportFragmentManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        drawerLayout = findViewById(R.id.drawer_layout)
        navView = findViewById(R.id.nav_view)

        val toggle = ActionBarDrawerToggle(
            this, drawerLayout, toolbar, 0, 0
        )
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        navView.setNavigationItemSelectedListener(this)


        listCatBreeds = ArrayList()
        listCatBreeds = setDataInList("")

        val args = Bundle()
        args.putSerializable("LIST", listCatBreeds as Serializable)
        recyclerFragment.arguments = args

        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.myFragment, recyclerFragment)
        fragmentTransaction.commit()

        viewManager = LinearLayoutManager(this)
        viewAdapter = BreedAdapter(listCatBreeds, this)

    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_list -> {
                val fragment = RecyclerFragment()
                val args = Bundle()
                args.putSerializable("LIST", listCatBreeds)
                fragment.arguments = args
                val fragmentTransaction = fragmentManager.beginTransaction()
                fragmentTransaction.replace(R.id.myFragment, recyclerFragment)
                fragmentTransaction.commit()
            }
            R.id.nav_info -> {
                val fragmentTransaction = fragmentManager.beginTransaction()
                fragmentTransaction.replace(R.id.myFragment, infoFragment)
                fragmentTransaction.commit()
            }
        }
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
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
