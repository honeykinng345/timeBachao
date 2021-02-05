package home.services.timeBacaho.Adapter;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import home.services.timeBacaho.Activity.Sub_cat_Activity;
import home.services.timeBacaho.Models.categories;
import home.services.timeBacaho.R;


public class CategoriesAdapter extends RecyclerView.Adapter<CategoriesAdapter.CategoriesViewHolder>  {
    private Context mCtx;
    private List<categories> catList;

    public CategoriesAdapter(Context mCtx, List<categories> catList) {
        this.mCtx = mCtx;
        this.catList = catList;
    }

    @NonNull
    @Override
    public CategoriesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.row_catgeories, null);
        return new CategoriesViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull CategoriesViewHolder holder, int position) {

        final int catid = catList.get(position).getCid();
        final String catTitile = catList.get(position).getName();

        String catImage = catList.get(position).getImage();
        holder.textViewTitle.setText(catTitile);


        try {
            Picasso.get().load(catImage).fit().into(holder.catImageView);



        } catch ( Exception e){
            Toast.makeText(mCtx,"error"+e,Toast.LENGTH_SHORT).show();


        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String tempId = String.valueOf(catid);
                Intent intent = new Intent(mCtx, Sub_cat_Activity.class);
                intent.putExtra("cid",tempId);
                intent.putExtra("title",catTitile);
                mCtx.startActivity(intent);
            }
        });



    }

    @Override
    public int getItemCount() {
        return catList.size();
    }

    public void filterList(ArrayList<categories> filterdNames) {
        this.catList = filterdNames;
        notifyDataSetChanged();
    }

    class CategoriesViewHolder extends RecyclerView.ViewHolder {
        TextView textViewTitle, textViewShortDesc;
        ImageView catImageView;

        public CategoriesViewHolder(@NonNull View itemView) {

            super(itemView);
            catImageView = itemView.findViewById(R.id.iv);
            textViewTitle = itemView.findViewById(R.id.titlTv);


        }
    }
}
