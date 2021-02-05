package home.services.timeBacaho.Adapter;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import home.services.timeBacaho.Activity.MainUserActivity;
import home.services.timeBacaho.Activity.OrderDetailActivity;
import home.services.timeBacaho.Models.ModelOrderUser;
import home.services.timeBacaho.Models.subCategories;
import home.services.timeBacaho.R;


public class AdapterOrderUser extends RecyclerView.Adapter<AdapterOrderUser.OrderViewHolder> {
    private Context context;
    private ArrayList<ModelOrderUser> modelOrderUsers;


    public AdapterOrderUser(Context context, ArrayList<ModelOrderUser> modelOrderUsers) {
        this.context = context;
        this.modelOrderUsers = modelOrderUsers;
    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.row_order_user, null);
        return new AdapterOrderUser.OrderViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder holder, int position) {
        ModelOrderUser modelOrderUser = modelOrderUsers.get(position);
        final String id = modelOrderUser.getOrderId();
        final String orderStatus = modelOrderUser.getOrderStatus();

        final String email = modelOrderUser.getUserEmail();

        final String orderCost = modelOrderUser.getOrderCost();
        String orderTime = modelOrderUser.getOrderTime();


        holder.orderIDTv.setText(id);
        holder.amountTv.setText("Total Amount:"+orderCost);
        holder.status.setText(orderStatus);
        switch (orderStatus) {
            case "In Progress":
                holder.status.setTextColor(context.getResources().getColor(R.color.colorPrimary));
                break;
            case "Completed":
                holder.status.setTextColor(context.getResources().getColor(R.color.colorGreen));
                break;
            case "Cancelled":
                holder.status.setTextColor(context.getResources().getColor(R.color.colorred));
                break;
        }
// timestamp to proper format
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(Long.parseLong(orderTime));
        final String formatTime = DateFormat.format("dd/MM/yyyy",calendar).toString();

        holder.dateTv.setText(formatTime);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, OrderDetailActivity.class);


                intent.putExtra("email",email);
                intent.putExtra("id",id);
                context.startActivity(intent);




            }
        });
    }

    @Override
    public int getItemCount() {
        return modelOrderUsers.size();
    }

    class  OrderViewHolder extends RecyclerView.ViewHolder {


        TextView orderIDTv,dateTv,shopnameTv,amountTv,status;

        public OrderViewHolder(@NonNull View itemView) {
            super(itemView);
            orderIDTv = itemView.findViewById(R.id.orderIDTv);
            dateTv = itemView.findViewById(R.id.dateTv);

            amountTv = itemView.findViewById(R.id.amountTv);
            status = itemView.findViewById(R.id.status);




        }
    }
}
