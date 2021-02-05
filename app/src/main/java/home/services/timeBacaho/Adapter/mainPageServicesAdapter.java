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

import java.util.List;

import home.services.timeBacaho.Activity.CatgoriesActivity;
import home.services.timeBacaho.Models.MainPageServicesModelClass;
import home.services.timeBacaho.R;

public class mainPageServicesAdapter  extends  RecyclerView.Adapter<mainPageServicesAdapter .MainServicesViewHolder>{
    private Context mCtx;
    private List<MainPageServicesModelClass> catList;

    public mainPageServicesAdapter(Context mCtx, List<MainPageServicesModelClass> catList) {
        this.mCtx = mCtx;
        this.catList = catList;
    }

    @NonNull
    @Override
    public MainServicesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.row_main_page_service, null);
        return new mainPageServicesAdapter.MainServicesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MainServicesViewHolder holder, int position) {

        final int catid = catList.get(position).getSid();
        final String ServiceTitile = catList.get(position).getSname();
        String ServicesImage = catList.get(position).getSImage();

        holder.serviceName.setText(ServiceTitile);
        try {
            Picasso.get().load(ServicesImage).fit().into(holder.MainPageImage);

        }catch (Exception e){
            Picasso.get().load(R.drawable.ic_add_shopping_cart_primary);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String tempId = String.valueOf(catid);

                Intent intent = new Intent(mCtx, CatgoriesActivity.class);
                intent.putExtra("cid",tempId);
                intent.putExtra("ServiceTitle",ServiceTitile);
                mCtx.startActivity(intent);
            }
        });
    }


    @Override
    public int getItemCount() {
        return catList.size();
    }

    class MainServicesViewHolder extends RecyclerView.ViewHolder {
        TextView serviceName;
        ImageView MainPageImage;
        MainServicesViewHolder(@NonNull View itemView) {
            super(itemView);

            MainPageImage = itemView.findViewById(R.id.serviceIv);
            serviceName = itemView.findViewById(R.id.titleServiceTv);
        }
    }
}
