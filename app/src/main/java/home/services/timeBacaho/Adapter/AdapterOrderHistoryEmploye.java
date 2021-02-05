package home.services.timeBacaho.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import home.services.timeBacaho.Models.ModelNewOrderEmploye;
import home.services.timeBacaho.R;

public class AdapterOrderHistoryEmploye extends RecyclerView.Adapter<AdapterOrderHistoryEmploye.ViewHolder> {
    Context context;
    ArrayList<ModelNewOrderEmploye>  orderHistoryEmploye;

    public AdapterOrderHistoryEmploye(Context context, ArrayList<ModelNewOrderEmploye> orderHistoryEmploye) {
        this.context = context;
        this.orderHistoryEmploye = orderHistoryEmploye;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.row_order_user, null);
        return new AdapterOrderHistoryEmploye.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        ModelNewOrderEmploye employee =  orderHistoryEmploye.get(position);
        String status = employee.getStatus();
        String cost = employee.getCost();
        String oderId =  employee.getOrderId();
        String time = employee.getCreated_us();



        holder.amountTv.setText(""+cost);
        holder.statusTV.setText(status);
        holder.orderIDTv.setText(""+oderId);
        holder.dateTv.setText(""+time);

        if (status.equals("In Progress")){
            holder.statusTV.setTextColor(context.getResources().getColor(R.color.colorPrimary));
        }

        else if (status.equals("Cancelled")){
            holder.statusTV.setTextColor(context.getResources().getColor(R.color.colorred));
        }
        else if (status.equals("Completed")){
            holder.statusTV.setTextColor(context.getResources().getColor(R.color.colorGreen));
        }
        else if (status.equals("Pending")){
            holder.statusTV.setTextColor(context.getResources().getColor(R.color.colorPink));
        }


    }

    @Override
    public int getItemCount() {
        return orderHistoryEmploye.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {



        TextView orderIDTv,dateTv,amountTv,statusTV;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);


            orderIDTv = itemView.findViewById(R.id.orderIDTv);
            dateTv = itemView.findViewById(R.id.dateTv);

            amountTv = itemView.findViewById(R.id.amountTv);
            statusTV = itemView.findViewById(R.id.status);


        }
    }
}
