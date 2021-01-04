package com.orane.icliniq.wellness

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.orane.icliniq.R

class MyListAdapter : RecyclerView.Adapter<MyListAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val listItem = layoutInflater.inflate(R.layout.list_item, parent, false)
        return ViewHolder(listItem)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//
//        holder.textView.setText(listdata[position].getDescription());
//        holder.imageView.setImageResource(listdata[position].getImgId());
//        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Toast.makeText(view.getContext(),"click on item: "+myListData.getDescription(),Toast.LENGTH_LONG).show();
//            }
//        });
    }

    override fun getItemCount(): Int {
        return 2
    }

    class ViewHolder  //        public ImageView imageView;
    //        public TextView textView;
    //        public RelativeLayout relativeLayout;
    (itemView: View?) : RecyclerView.ViewHolder(itemView!!)
}