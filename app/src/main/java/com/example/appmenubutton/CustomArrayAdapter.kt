import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Filter
import android.widget.TextView
import com.example.appmenubutton.R
import java.util.Locale

class CustomArrayAdapter(context: Context, private val items: List<String>) :
    ArrayAdapter<String>(context, R.layout.list_item, items) {

    private var filteredItems: List<String> = items

    override fun getCount(): Int {
        return filteredItems.size
    }

    override fun getItem(position: Int): String? {
        return filteredItems[position]
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view: View = convertView ?: LayoutInflater.from(context).inflate(R.layout.list_item, parent, false)
        val textViewItem = view.findViewById<TextView>(R.id.textViewItem)
        textViewItem.text = getItem(position)
        return view
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val filterResults = FilterResults()
                val query = constraint?.toString()?.lowercase(Locale.getDefault()) ?: ""

                filteredItems = if (query.isEmpty()) {
                    items
                } else {
                    items.filter {
                        it.lowercase(Locale.getDefault()).contains(query)
                    }
                }

                filterResults.values = filteredItems
                filterResults.count = filteredItems.size
                return filterResults
            }

            @Suppress("UNCHECKED_CAST")
            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                filteredItems = results?.values as? List<String> ?: items
                notifyDataSetChanged()
            }
        }
    }
}
