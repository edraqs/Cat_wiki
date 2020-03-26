package mobile.dev.catwiki

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import kotlinx.android.synthetic.main.breed_info.*

class BreedInfoActivity : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.breed_info)

        name_view.text = intent.getStringExtra("BREEDNAME")
        size_view.text = intent.getStringExtra("BREEDSIZE")
        coat_view.text = intent.getStringExtra("BREEDCOAT")
        color_view.text = intent.getStringExtra("BREEDCOLOR")
        val lifespan = intent.getStringExtra("BREEDLIFESPAN")
        if (lifespan != ""){
            lifespan_view.text = lifespan
        }
        image_view.setImageResource(getIntent().getStringExtra("BREEDIMAGE").toInt())
    }
}


