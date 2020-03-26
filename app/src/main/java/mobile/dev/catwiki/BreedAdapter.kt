package mobile.dev.catwiki

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.grid_layout_list_item.view.*


class BreedAdapter( var breedList: ArrayList<CatBreed>, var clickListener: OnItemClickListener) :
    RecyclerView.Adapter<BreedAdapter.ViewHolder>(), Filterable {

    var breedListFull = ArrayList<CatBreed>(breedList)
    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder.
    // Each data item is just a string in this case that is shown in a TextView.
    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var name = view.breed_name
        var image = view.breed_image

        fun initialize(item: CatBreed, action:OnItemClickListener){
            name.text = item.name
            image.setImageResource(item.image)

            itemView.setOnClickListener{
                action.onItemClick(item,adapterPosition)
            }
        }
    }


    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // create a new view
        return ViewHolder( LayoutInflater.from(parent.context).inflate(R.layout.grid_layout_list_item, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        //var breed = myDataset[position]
        //holder.name.text = breed.name
        holder.initialize(breedList.get(position),clickListener)
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = breedList.size

    override fun getFilter(): Filter {
        return breedFilter
    }

    private val breedFilter = object: Filter(){
        override fun performFiltering(constraint: CharSequence?): FilterResults {
            val filteredList: MutableList<CatBreed> = ArrayList()

            if (constraint == null || constraint.length === 0) {
                filteredList.addAll(breedListFull)
            } else {
                val filterPattern = constraint.toString().toLowerCase().trim()
                for (item in breedListFull) {
                    if (item.name.toLowerCase().contains(filterPattern)) {
                        filteredList.add(item)
                    }
                }
            }

            val results = FilterResults()
            results.values = filteredList

            return results
        }

        override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
            breedList.clear()
            breedList.addAll(results!!.values as List<CatBreed>)
            notifyDataSetChanged()
        }
    }
}

interface OnItemClickListener {
    fun onItemClick(item: CatBreed, position: Int)
}