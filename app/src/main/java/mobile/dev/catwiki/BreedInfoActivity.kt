package mobile.dev.catwiki

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import kotlinx.android.synthetic.main.breed_info.*

class BreedInfoActivity : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.breed_info)

        name_view.text = getIntent().getStringExtra("BREEDNAME")
        size_view.text = getIntent().getStringExtra("BREEDSIZE")
        coat_view.text = getIntent().getStringExtra("BREEDCOAT")
        color_view.text = getIntent().getStringExtra("BREEDCOLOR")
        lifespan_view.text = getIntent().getStringExtra("BREEDLIFESPAN")
        //image_car.setImageResource(getIntent().getStringExtra("BREEDIMAGE").toInt())

    }
}


