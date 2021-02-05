package home.services.timeBacaho.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import home.services.timeBacaho.Models.ModelCartItem;
import home.services.timeBacaho.R;
import home.services.timeBacaho.SQLiteHandler;

public class OrderSummaryAdpater extends RecyclerView.Adapter<OrderSummaryAdpater.HolderItem> {
    private SQLiteHandler sqLiteHandler;
    private Context context;
    private ArrayList<ModelCartItem> modelCartItems;


    public OrderSummaryAdpater(Context context, ArrayList<ModelCartItem> modelCartItems) {
        this.context = context;
        this.modelCartItems = modelCartItems;
    }


    @NonNull
    @Override
    public HolderItem onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_order_summary_cart,parent,false);
        return new HolderItem(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HolderItem holder, int position) {
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

    }

    @Override
    public int getItemCount() {
        return modelCartItems.size();
    }

    class HolderItem extends RecyclerView.ViewHolder {


        private TextView itemTitleTv,itemPriceTv,itemPriceEachTv
                ,itemQuantity;
        public HolderItem(@NonNull View itemView) {
            super(itemView);

            itemTitleTv= itemView.findViewById(R.id.itemTitleTv);
            itemPriceTv = itemView.findViewById(R.id.itemPriceTv);
            itemPriceEachTv = itemView.findViewById(R.id.itemPriceEachTv);
            itemQuantity = itemView.findViewById(R.id.itemQuantity);
        }
    }
}
