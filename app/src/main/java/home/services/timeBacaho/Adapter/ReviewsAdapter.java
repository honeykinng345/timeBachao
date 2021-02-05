package home.services.timeBacaho.Adapter;

import android.content.Context;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.blogspot.atifsoftwares.circularimageview.CircularImageView;

import java.util.ArrayList;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import home.services.timeBacaho.Models.Review;
import home.services.timeBacaho.R;

public class ReviewsAdapter extends RecyclerView.Adapter<ReviewsAdapter.ViewHolder> {
    Context context;
    ArrayList<Review> reviewArrayList;


    public ReviewsAdapter(Context context, ArrayList<Review> reviewArrayList) {
        this.context = context;
        this.reviewArrayList = reviewArrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_show_reviews, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Review review = reviewArrayList.get(position);


        holder.name.setText(""+review.getUserName());
        holder.rateBar.setRating(Float.parseFloat(""+review.getRating()));

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(Long.parseLong(review.getTimeday()));
        final String formatTime = DateFormat.format("dd/MM/yyyy",calendar).toString();

        holder.dateTv.setText(formatTime);

        if (review.getComment().equals("No Message")){
            holder.comments.setVisibility(View.GONE);
        }
        holder.comments.setText(""+review.getComment());



    }

    @Override
    public int getItemCount() {
        return reviewArrayList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.iv)
        CircularImageView iv;
        @BindView(R.id.name)
        TextView name;
        @BindView(R.id.rateBar)
        RatingBar rateBar;
        @BindView(R.id.dateTv)
        TextView dateTv;
        @BindView(R.id.relavtive2)
        RelativeLayout relavtive2;
        @BindView(R.id.comments)
        TextView comments;
        @BindView(R.id.relavtive1)
        RelativeLayout relavtive1;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
