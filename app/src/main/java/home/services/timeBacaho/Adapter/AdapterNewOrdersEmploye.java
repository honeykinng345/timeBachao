package home.services.timeBacaho.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import home.services.timeBacaho.Activity.OrderDetailEmployeActivity;
import home.services.timeBacaho.Activity.ServiceProviderMain;
import home.services.timeBacaho.AppConfig;
import home.services.timeBacaho.AppController;
import home.services.timeBacaho.Models.ModelNewOrderEmploye;
import home.services.timeBacaho.R;

public class AdapterNewOrdersEmploye extends RecyclerView.Adapter<AdapterNewOrdersEmploye.ViewHolder> {
    Context context;
List<ModelNewOrderEmploye> newOrderEmploye;

    public AdapterNewOrdersEmploye(Context context, List<ModelNewOrderEmploye> newOrderEmploye) {
        this.context = context;
        this.newOrderEmploye = newOrderEmploye;
    }



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.row_new_orders_employe, null);

        return new  AdapterNewOrdersEmploye.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        ModelNewOrderEmploye employee =  newOrderEmploye.get(position);
        String status = employee.getStatus();
        String cost = employee.getCost();
        String time = employee.getCreated_us();
        final String orderId = employee.getOrderId();

        int id = employee.getId();
        final String tempid = String.valueOf(id);



        holder.statusTv.setTextColor(context.getResources().getColor(R.color.colorPrimary));
      holder.statusTv.setText(""+status);

      holder.orderIDTv.setText(""+"Order ID :"+orderId);
      holder.amountTv.setText(""+"Total Amount:"+cost);
      holder.dateTv.setText(""+time);


  holder.itemView.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
          Intent intent = new Intent(context, OrderDetailEmployeActivity.class);
          intent.putExtra("id",orderId);

          context.startActivity(intent);

      }
  });

  holder.acceptBtn.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
          acceptedRequest(tempid);
      }
  });
  holder.cancel_button.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
          CancelRequest(tempid);
      }
  });

    }

    private void CancelRequest(final String tempid) {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, AppConfig.URL_CanceledRequestOrderEmploye,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {


                        Toast.makeText(context,"Thanks For Accepted Requested",Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(context,ServiceProviderMain.class)
                                .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                                .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        context.startActivity(intent);





                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Toast.makeText(context,
                                error.getMessage(), Toast.LENGTH_LONG).show();


                    }

                }) { @Override
        protected Map<String, String> getParams() {
            // Posting params to register url
            Map<String, String> params = new HashMap<>();
            params.put("id",tempid);


            return params;
        }

        };

        //adding our stringrequest to queue
        AppController.getInstance().addToRequestQueue(stringRequest);
    }

    private void acceptedRequest(final String tempid) {




        StringRequest stringRequest = new StringRequest(Request.Method.POST, AppConfig.URL_AcceptedRequestOrderEmploye,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {


                        Toast.makeText(context,"Thanks For Accepted Requested",Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(context,ServiceProviderMain.class)
                        .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                        .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        context.startActivity(intent);





                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Toast.makeText(context,
                                error.getMessage(), Toast.LENGTH_LONG).show();


                    }

                }) { @Override
        protected Map<String, String> getParams() {
            // Posting params to register url
            Map<String, String> params = new HashMap<>();
            params.put("id",tempid);


            return params;
        }

        };

        //adding our stringrequest to queue
        AppController.getInstance().addToRequestQueue(stringRequest);
    }

    @Override
    public int getItemCount() {
        return newOrderEmploye.size();
    }

    class  ViewHolder extends RecyclerView.ViewHolder {



      TextView orderIDTv,dateTv,amountTv,statusTv;
      Button  cancel_button,acceptBtn;
      public ViewHolder(@NonNull View itemView) {
          super(itemView);

          dateTv = itemView.findViewById(R.id.dateTv);
          orderIDTv = itemView.findViewById(R.id.orderIDTv);
          amountTv = itemView.findViewById(R.id.amountTv);
          statusTv = itemView.findViewById(R.id.status);
          acceptBtn = itemView.findViewById(R.id.acceptBtn);
          cancel_button = itemView.findViewById(R.id.cancel_button);


      }
  }
}
