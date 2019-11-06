package pick.com.app.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import java.util.*

class MapListingAdpter<T> : ArrayViewPagerAdapter<T> {

    internal var layout: Int = 0

    constructor(data: Array<T>) : super(*data) {}
    constructor(data: ArrayList<T>, layout: Int) : super(data) {
        this.layout = layout
    }

    override fun getView(inflater: LayoutInflater, container: ViewGroup, item: T, position: Int): View {



        return inflater.inflate(layout, container, false)

    }




}
