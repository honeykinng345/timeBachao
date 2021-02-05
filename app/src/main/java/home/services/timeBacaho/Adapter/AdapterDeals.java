package home.services.timeBacaho.Adapter;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.blogspot.atifsoftwares.circularimageview.CircularImageView;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import home.services.timeBacaho.Activity.DealsActivity;
import home.services.timeBacaho.Activity.Sub_cat_Activity;
import home.services.timeBacaho.Models.ModelDeals;
import home.services.timeBacaho.Models.ModelOrderItem;
import home.services.timeBacaho.Models.categories;
import home.services.timeBacaho.R;
import home.services.timeBacaho.SQLiteHandler;


public class AdapterDeals extends RecyclerView.Adapter<AdapterDeals.dealsHolder>  {

    private SQLiteHandler sqLiteHandler;
    private Context context;

    private  ArrayList<ModelDeals> modelDeals;

    public AdapterDeals(Context context, ArrayList<ModelDeals> modelDeals) {
        this.context = context;
        this.modelDeals = modelDeals;
    }

    @NonNull
    @Override
    public dealsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.row_deals, null);
        return  new AdapterDeals.dealsHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull dealsHolder holder, int position) {

        final ModelDeals deals = modelDeals.get(position);
        int id = deals.getId();
        String name = deals.getDealName();
        String shortDescription = deals.getShortDescription();
        String longDescription = deals.getLongDescription();
        String cost = deals.getCost();
        String dealsTimeDuration = deals.getTimeDuration();
        String dealsImage = deals.getImage();


        holder.TitleTv.setText(""+name);
        holder.shortDes.setText(""+shortDescription);
        holder.priceTv.setText(""+"Rs:"+cost);
        holder.priceTv.setText(""+"Rs:"+cost);
        try {
            Picasso.get().load(dealsImage).fit().into(holder.image);

        }catch (Exception e){
            Picasso.get().load(R.drawable.ic_add_shopping_cart_primary);
        }


        holder.cartTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Show prodct Detail
                ShowDealsDialougs(deals);
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //holder item click ...show otem detail in bottom sheet

                detailBottomSheet(deals);//here model product contain detail of clicked product





            }
        });
    }

    @SuppressLint("SetTextI18n")
    private void detailBottomSheet(ModelDeals deals) {


        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(context);
        //infilate view For Bottom Sheet
        View view =  LayoutInflater.from(context).inflate(R.layout.deal_detail_bottom_sheet,null);
        bottomSheetDialog.setContentView(view);



        //init view of bottom sheet



        TextView titleTv,shortDes,duration,origonalPriceTv,description;
        ImageView iconIv;

        titleTv = view.findViewById(R.id.titleTv);
        origonalPriceTv= view.findViewById(R.id.origonalPriceTv);
        shortDes= view.findViewById(R.id.shortDes);
        duration= view.findViewById(R.id.duration);
        description= view.findViewById(R.id.description);
        iconIv= view.findViewById(R.id.iconIv);

String title = deals.getDealName();
String shortDescription = deals.getShortDescription();
String longDescription = deals.getLongDescription();
String timeDuration = deals.getTimeDuration();
String cost = deals.getCost();
String dealsImage = deals.getImage();


titleTv.setText(""+title);
shortDes.setText(""+shortDescription);
origonalPriceTv.setText(""+"Rs"+cost);
duration.setText(""+timeDuration);
description.setText(""+longDescription);
        try {
            Picasso.get().load(dealsImage).placeholder(R.drawable.ic_add_shopping_cart_primary).into(iconIv);

        }catch (Exception e){

            iconIv.setImageResource(R.drawable.ic_add_shopping_cart_primary);

        }



        //show dialoug
        bottomSheetDialog.show();

    }

    private double cost = 0;
    private double finalCost = 0;
    String price ;
    private  int quantity = 0;

    private void ShowDealsDialougs(ModelDeals deals) {

        View view = LayoutInflater.from(context).inflate(R.layout.dialoug_box_cart,null);
        CircularImageView productIv;
        final TextView titleTv,quantityTv,descriptionTv,discountNoteTv,
                origonalPriceTv,discountPriceTV,finalText,countTv;
        ImageButton decrmentBtn,incerementBtn;
        Button addCartBtn;

        productIv= view.findViewById(R.id.productIv);
        titleTv = view.findViewById(R.id.titleTv);
        origonalPriceTv = view.findViewById(R.id.origonalPriceTv);
        finalText = view.findViewById(R.id.finalText);
        countTv = view.findViewById(R.id.countTv);
        decrmentBtn = view.findViewById(R.id.decrmentBtn);
        incerementBtn = view.findViewById(R.id.incerementBtn);
        discountNoteTv = view.findViewById(R.id.discountNoteTv);
        addCartBtn = view.findViewById(R.id.addCartBtn);
        discountPriceTV = view.findViewById(R.id.discountPriceTV);


int id = deals.getId();
        String title = deals.getDealName();
        String origonalPrice = deals.getCost();
        String dealImage = deals.getImage();

        price = deals.getCost();
        final String tempId = String.valueOf(id);

        cost = Double.parseDouble(price.replaceAll("Rs", ""));
        finalCost= Double.parseDouble(price.replaceAll("Rs", ""));
        quantity = 1;
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setView(view);
        discountPriceTV.setVisibility(View.GONE);
        discountNoteTv.setVisibility(View.GONE);
        titleTv.setText(""+title);
        origonalPriceTv.setText(""+origonalPrice);
        finalText.setText(""+finalCost);
        try {
            Picasso.get().load(dealImage).fit().into(productIv);

        }catch (Exception e){
            Picasso.get().load(R.drawable.ic_add_shopping_cart_primary);
        }
        final AlertDialog dialog = builder.create();
        dialog.show();

        incerementBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (quantity>5){
                    Toast.makeText(context,"Please Select 5 sevicres At a Time ",Toast.LENGTH_SHORT).show();
                    return;
                }else{
                    finalCost = finalCost + cost;
                    quantity++;
                    finalText.setText(""+finalCost);
                    countTv.setText(""+quantity);
                }



            }
        });
        decrmentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (quantity>1){
                    finalCost = finalCost - cost;
                    quantity--;

                    finalText.setText(""+finalCost);
                    countTv.setText(""+quantity);

                }
            }
        });
        addCartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = titleTv.getText().toString().trim();

                String priceEach = price;
                String totalPrice  = finalText.getText().toString().trim().replace("Rs","");
                String count = countTv.getText().toString().trim();

                //add data into sqlite
                addToCart(tempId,title,priceEach,totalPrice,count);
                dialog.dismiss();



            }
        });


    }

    private void addToCart(String tempId, String title, String priceEach, String price, String count) {

        sqLiteHandler = new SQLiteHandler(context);

        sqLiteHandler.insertData(tempId,title,priceEach,price,count);

    }

    @Override
    public int getItemCount() {
        return modelDeals.size();
    }

    class dealsHolder extends RecyclerView.ViewHolder{

        TextView TitleTv,priceTv,shortDes,cartTv;
        ImageView image;
        public dealsHolder(@NonNull View itemView) {
            super(itemView);

            image = itemView.findViewById(R.id.image);
            TitleTv = itemView.findViewById(R.id.TitleTv);
            priceTv = itemView.findViewById(R.id.priceTv);
            shortDes = itemView.findViewById(R.id.shortDes);
            cartTv = itemView.findViewById(R.id.cartTv);


        }
    }

}
