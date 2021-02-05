package home.services.timeBacaho.Adapter;
import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;


import home.services.timeBacaho.Activity.MainUserActivity;
import home.services.timeBacaho.Activity.Sub_cat_Activity;
import home.services.timeBacaho.Models.ModelCartItem;
import home.services.timeBacaho.R;
import home.services.timeBacaho.SQLiteHandler;
import p32929.androideasysql_library.EasyDB;

public class AdapterCartItem extends RecyclerView.Adapter<AdapterCartItem.HolderCartItem> {
private SQLiteHandler sqLiteHandler;
    private   Context context;
    private ArrayList<ModelCartItem> modelCartItems;

    public AdapterCartItem(Context context, ArrayList<ModelCartItem> modelCartItems) {
        this.context = context;
        this.modelCartItems = modelCartItems;
    }

    @NonNull
    @Override
    public HolderCartItem onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_cart_item,parent,false);
        return new HolderCartItem(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull HolderCartItem holder, final int position) {



        final ModelCartItem cartItem = modelCartItems.get(position);
        final String id = cartItem.getId();
        final String pid = cartItem.getPid();
        final String title = cartItem.getName();

        final String cost = cartItem.getCost();
        String price = cartItem.getPrice();
        String quantity = cartItem.getQuantity();

        holder.itemTitleTv.setText(""+title);
        holder.itemQuantity.setText(""+"["+quantity+"]");
        holder.itemPriceTv.setText(""+cost);
        holder.itemPriceEachTv.setText(""+price);


        holder.removeTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sqLiteHandler = new SQLiteHandler(context);
          sqLiteHandler.deleteData(id);

                Toast.makeText(context,"Remove From Cart "+title,Toast.LENGTH_SHORT).show();
                //refresh List
                modelCartItems.remove(position);
                notifyDataSetChanged();
                notifyItemChanged(position);

                double tx = Double.parseDouble((((MainUserActivity)context).alltotalPriceTV.getText().toString().trim().replace("Rs","")));
                double totalPrice = tx - Double.parseDouble(cost.replace("Rs",""));
                double dilveryFees = Double.parseDouble((((MainUserActivity)context).dilverFees.replace("Rs","")));
                double sTotaPrice = Double.parseDouble(String.format("%.2f",totalPrice)) - Double.parseDouble(String.format("%.2f",dilveryFees));
                ((MainUserActivity)context).allTotalPrice = 0.0;
                ((MainUserActivity)context).alltotalPriceTV.setText("Rs"+String.format("%.2f",Double.parseDouble(String.format("%.2f",totalPrice))));
                ((MainUserActivity)context).sTotalTv.setText("Rs"+String.format("%.2f",sTotaPrice));


                //after removing item from cart we update the countTv
               ((MainUserActivity)context).CartCount();




            }
        });


    }

    @Override
    public int getItemCount() {
        return modelCartItems.size();
    }

    class  HolderCartItem extends RecyclerView.ViewHolder{

        private TextView itemTitleTv,itemPriceTv,itemPriceEachTv
                ,itemQuantity,removeTv;

        public HolderCartItem(@NonNull View itemView) {
            super(itemView);
            itemTitleTv= itemView.findViewById(R.id.itemTitleTv);
            itemPriceTv = itemView.findViewById(R.id.itemPriceTv);
            itemPriceEachTv = itemView.findViewById(R.id.itemPriceEachTv);
            itemQuantity = itemView.findViewById(R.id.itemQuantity);
            removeTv = itemView.findViewById(R.id.removeTv);



        }
    }
}
