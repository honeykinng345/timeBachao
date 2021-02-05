package home.services.timeBacaho.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Paint;
import android.media.Image;
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

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.blogspot.atifsoftwares.circularimageview.CircularImageView;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import home.services.timeBacaho.Activity.RedeemActivity;
import home.services.timeBacaho.Activity.Sub_cat_Activity;
import home.services.timeBacaho.AppConfig;
import home.services.timeBacaho.AppController;
import home.services.timeBacaho.DatabaseHelper;
import home.services.timeBacaho.Models.subCategories;
import home.services.timeBacaho.R;
import home.services.timeBacaho.SQLiteHandler;
import p32929.androideasysql_library.EasyDB;


public class subCategoriesAdaper extends RecyclerView.Adapter<subCategoriesAdaper.subCategoriesViewHolder> {
    private SQLiteHandler sqLiteHandler;
    private Context mCtx;
    private List<subCategories> subList;
private  DatabaseHelper db1;
private  String email;
private  String redeem;
    public subCategoriesAdaper(Context mCtx, List<subCategories> subList) {
        this.mCtx = mCtx;
        this.subList = subList;

    }

    @NonNull
    @Override
    public subCategoriesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.row_sub_cat, null);
        return new subCategoriesAdaper.subCategoriesViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull subCategoriesViewHolder holder, int position) {


        final subCategories modelProduct = subList.get(position);

        String discountAvaliabel= modelProduct.getDiscountAvaliabel();
        String discountNotes= modelProduct.getDiscountNotes();
        String discountPrice= modelProduct.getDiscountprices();

        String productIcon= modelProduct.getSimage();
        String productDescription= modelProduct.getDescription();
        String origonalPrice= modelProduct.getOrigonalPrice();

        String productTitle= modelProduct.getTitle();

        int uid= modelProduct.getId();



        //set data

        //set data
        holder.titleTv.setText(""+productTitle);
        // holder.quantityTv.setText(""+productQuantity);

        holder.discountNoteTv.setText(""+discountNotes+"%"+""+"OFF");
        holder.discountPriceTV.setText("Rs"+discountPrice);
        holder.origonalPriceTv.setText("Rs"+origonalPrice);

        if (discountAvaliabel.equals("1")){
            holder.discountPriceTV.setVisibility(View.VISIBLE);
            holder.discountNoteTv.setVisibility(View.VISIBLE);
            holder.origonalPriceTv.setPaintFlags(holder.origonalPriceTv.getPaintFlags()| Paint.STRIKE_THRU_TEXT_FLAG);
        }else {
            holder.discountPriceTV.setVisibility(View.GONE);
            holder.discountNoteTv.setVisibility(View.GONE);
            holder.origonalPriceTv.setPaintFlags(0);
        }

        try {
            Picasso.get().load(productIcon).placeholder(R.drawable.ic_add_shopping_cart_primary).into(holder.productIconIv);

        }catch (Exception e){

            holder.productIconIv.setImageResource(R.drawable.ic_add_shopping_cart_primary);

        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                detailBottomSheet(modelProduct);
            }
        });

        holder.addcart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Show prodct Detail
                ShowProductQuantity(modelProduct);
            }
        });


fetchRedeemPoints();
fetchUserInfo();

    }

    private void fetchRedeemPoints() {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, AppConfig.URL_FetchRedeemUserGiftPoints,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {


                                //getting the whole json object from the response
                                JSONObject obj = new JSONObject(response);
                                boolean error = obj.getBoolean("error");
                                //we have the array named hero inside the object
                                //so here we are getting that json array

                                JSONArray heroArray = obj.getJSONArray("result");
                                // User successfully stored in MySQL
                                // Now store the user in sqlite

                                for (int i = 0; i < heroArray.length(); i++) {

                                    //getting product object from json array
                                    JSONObject categories = heroArray.getJSONObject(i);

                                    redeem = categories.getString("redeem");




                                }








                            } catch (JSONException e) {

                                e.printStackTrace();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                            Toast.makeText(mCtx,
                                    error.getMessage(), Toast.LENGTH_LONG).show();


                        }

                    }) { @Override
            protected Map<String, String> getParams() {
                // Posting params to register url
                Map<String, String> params = new HashMap<>();
                params.put("email",email);


                return params;
            }

            };

            //adding our stringrequest to queue
            AppController.getInstance().addToRequestQueue(stringRequest);
        }

    private void fetchUserInfo() {
db1 = new DatabaseHelper(mCtx);

        Cursor res = db1.getAllData();
        while (res.moveToNext()) {

            email = res.getString(2);

        }

    }



    private void detailBottomSheet(subCategories modelProduct) {

        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(mCtx);
        //infilate view For Bottom Sheet
        View view =  LayoutInflater.from(mCtx).inflate(R.layout.item_detail_bottom,null);
        bottomSheetDialog.setContentView(view);

        TextView discountNoteTv,titleTv,descriptionTv,discountPriceTV,origonalPriceTv,duration;
          titleTv = view.findViewById(R.id.titleTv);
         discountNoteTv= view.findViewById(R.id.discountNoteTv);
        duration = view.findViewById(R.id.duration);
        origonalPriceTv = view.findViewById(R.id.origonalPriceTv);
          discountPriceTV = view.findViewById(R.id.discountPriceTV);
          descriptionTv = view.findViewById(R.id.description);

        final int id = modelProduct.getId();

        String discountAvaliabel= modelProduct.getDiscountAvaliabel();
        String discountNotes = modelProduct.getDiscountNotes();
        String discountPrice = modelProduct.getDiscountprices();
        String  productDescription = modelProduct.getDescription();
        String  productDuration = modelProduct.getDuration();



        final String productTitle = modelProduct.getTitle();
        String origonalPrice = modelProduct.getOrigonalPrice();

        titleTv.setText(productTitle);
        descriptionTv.setText(productDescription);

        discountPriceTV.setText("Rs:"+discountPrice);
        discountNoteTv.setText(discountNotes+"%-OFF");
        origonalPriceTv.setText("Rs:"+origonalPrice);
        duration.setText(productDuration);



        if (discountAvaliabel.equals("1")){
            discountPriceTV.setVisibility(View.VISIBLE);
            discountNoteTv.setVisibility(View.VISIBLE);
            origonalPriceTv.setPaintFlags(origonalPriceTv.getPaintFlags()| Paint.STRIKE_THRU_TEXT_FLAG);
        }
        else {
            discountPriceTV.setVisibility(View.GONE);
            discountNoteTv.setVisibility(View.GONE);

        }
        //show dialoug
        bottomSheetDialog.show();


    }

    private double cost = 0;
    private double finalCost = 0;
    String price ;
    private  int quantity = 0;

    private void ShowProductQuantity(subCategories modelProduct) {

        View view = LayoutInflater.from(mCtx).inflate(R.layout.dialoug_box_cart,null);
        CircularImageView productIv;
        final TextView titleTv,quantityTv,descriptionTv,discountNoteTv,
                origonalPriceTv,discountPriceTV,finalText,countTv;
        ImageButton decrmentBtn,incerementBtn;
        Button addCartBtn;
        productIv= view.findViewById(R.id.productIv);
        titleTv = view.findViewById(R.id.titleTv);
        discountNoteTv = view.findViewById(R.id.discountNoteTv);
        origonalPriceTv = view.findViewById(R.id.origonalPriceTv);
        discountPriceTV = view.findViewById(R.id.discountPriceTV);
        finalText = view.findViewById(R.id.finalText);
        countTv = view.findViewById(R.id.countTv);
        decrmentBtn = view.findViewById(R.id.decrmentBtn);
        incerementBtn = view.findViewById(R.id.incerementBtn);
        addCartBtn = view.findViewById(R.id.addCartBtn);
        //get Dataa

        String discountAvaliabel= modelProduct.getDiscountAvaliabel();
        String discountNotes= modelProduct.getDiscountNotes();
        String discountPrice= modelProduct.getDiscountprices();

        String productIcon= modelProduct.getSimage();

        String origonalPrice= modelProduct.getOrigonalPrice();

        String productTitle= modelProduct.getTitle();

        int uid = modelProduct.getId();
        final String tempId = String.valueOf(uid);



        if (discountAvaliabel.equals("1")){
            price = modelProduct.getDiscountprices();

            discountPriceTV.setVisibility(View.VISIBLE);

            discountNoteTv.setVisibility(View.VISIBLE);
            origonalPriceTv.setPaintFlags(origonalPriceTv.getPaintFlags()| Paint.STRIKE_THRU_TEXT_FLAG);

        }else {

            discountPriceTV.setVisibility(View.GONE);
            discountNoteTv.setVisibility(View.GONE);
            price = modelProduct.getOrigonalPrice();

        }

        cost = Double.parseDouble(price.replaceAll("Rs", ""));
        finalCost= Double.parseDouble(price.replaceAll("Rs", ""));
        quantity = 1;
        AlertDialog.Builder builder = new AlertDialog.Builder(mCtx);
        builder.setView(view);
        discountNoteTv.setText(""+discountNotes+"%-"+"OFF");
        discountPriceTV.setText("Rs"+discountPrice);
        origonalPriceTv.setText("Rs"+origonalPrice);


        titleTv.setText(""+productTitle);
        finalText.setText(""+finalCost);


        try {
            Picasso.get().load(productIcon).placeholder(R.drawable.ic_add_shopping_cart_primary).into(productIv);

        }catch (Exception e){

            productIv.setImageResource(R.drawable.ic_add_shopping_cart_primary);

        }
        final AlertDialog dialog = builder.create();
        dialog.show();

        incerementBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (quantity>5){
                    Toast.makeText(mCtx,"Please Select 5 sevicres At a Time ",Toast.LENGTH_SHORT).show();
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
    private  int item_id = 1;

    private void addToCart(String tempId, String title, String priceEach, String price, String count) {

sqLiteHandler = new SQLiteHandler(mCtx);

sqLiteHandler.insertData(tempId,title,priceEach,price,count);

        ((Sub_cat_Activity)mCtx).CartCount();







    }





    @Override
    public int getItemCount() {
        return subList.size();
    }

    public void filterList(ArrayList<subCategories> filterdNames) {



        this.subList = filterdNames;
        notifyDataSetChanged();
    }
//    public void filterList(ArrayList<subCategories> filterdNames) {
//
//
//
//        this.subList = filterdNames;
//
//
//        notifyDataSetChanged();
//    }

    class  subCategoriesViewHolder extends RecyclerView.ViewHolder {
        ImageView productIconIv;

        TextView discountNoteTv,titleTv,quantityTv,discountPriceTV,origonalPriceTv,addcart,descriptionTV;

        public subCategoriesViewHolder(@NonNull View itemView) {
            super(itemView);

            titleTv = itemView.findViewById(R.id.titleTv);
            discountNoteTv = itemView.findViewById(R.id.discountNoteTv);

            discountPriceTV = itemView.findViewById(R.id.discountPriceTV);
            origonalPriceTv = itemView.findViewById(R.id.origonalPriceTv);
            productIconIv = itemView.findViewById(R.id.productIconIv);

            addcart = itemView.findViewById(R.id.cartTv);




        }
    }

}
