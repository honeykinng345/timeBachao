package home.services.timeBacaho.Adapter;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import home.services.timeBacaho.Models.ModelOrderItem;
import home.services.timeBacaho.R;


public class AdapterOrderItems extends RecyclerView.Adapter<AdapterOrderItems.myHolder>{

    Context context;
    ArrayList<ModelOrderItem> cartItemArrayList;

    public AdapterOrderItems(Context context, ArrayList<ModelOrderItem> cartItemArrayList) {
        this.context = context;
        this.cartItemArrayList = cartItemArrayList;
    }

    @NonNull
    @Override
    public myHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.row_order_summary_cart,parent,false);

        return  new myHolder(view);



    }

    @Override
    public void onBindViewHolder(@NonNull myHolder holder, int position) {

        ModelOrderItem modelOrderItem = cartItemArrayList.get(position);
        int id = modelOrderItem.getId();
        String cost = modelOrderItem.getCost();
        int pid = modelOrderItem.getPid();
        String price = modelOrderItem.getPrice();
        int quantity = modelOrderItem.getQuantity();
        String  itemName = modelOrderItem.getItemname();


        holder.itemTitleTv.setText(""+itemName);
        holder.itemQuantity.setText(""+"["+quantity+"]");
        holder.itemPriceTv.setText(""+cost);
        holder.itemPriceEachTv.setText(""+price);

    }

    @Override
    public int getItemCount() {
        return cartItemArrayList.size();
    }

    public class myHolder extends RecyclerView.ViewHolder {


        private TextView itemTitleTv,itemPriceTv,itemPriceEachTv
                ,itemQuantity;
        public myHolder(@NonNull View itemView) {

            super(itemView);



            itemTitleTv= itemView.findViewById(R.id.itemTitleTv);
            itemPriceTv = itemView.findViewById(R.id.itemPriceTv);
            itemPriceEachTv = itemView.findViewById(R.id.itemPriceEachTv);
            itemQuantity = itemView.findViewById(R.id.itemQuantity);

        }
    }
}
