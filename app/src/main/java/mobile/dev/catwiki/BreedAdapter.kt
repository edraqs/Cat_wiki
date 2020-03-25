package mobile.dev.catwiki

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.list_item.view.*

class BreedAdapter( var breedList: ArrayList<CatBreed>, var clickListener: OnItemClickListener) :
    RecyclerView.Adapter<BreedAdapter.ViewHolder>() {

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder.
    // Each data item is just a string in this case that is shown in a TextView.
    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var name = view.breed_name
        var image = view.breed_image

        fun initialize(item: CatBreed, action:OnItemClickListener){
            name.text = item.name
            //image.setImageResource(item.image)

            itemView.setOnClickListener{
                action.onItemClick(item,adapterPosition)
            }
        }
    }


    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // create a new view
        return ViewHolder( LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        //var breed = myDataset[position]
        //holder.name.text = breed.name
        holder.initialize(breedList.get(position),clickListener)
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = breedList.size
}

interface OnItemClickListener {
    fun onItemClick(item: CatBreed, position: Int)
}