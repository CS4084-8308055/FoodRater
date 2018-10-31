package ie.ul.davidbeck.foodrater;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.FoodViewHolder> {

    private List<Food> mFoods = new ArrayList<>();
    private RecyclerView mRecyclerView;

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        mRecyclerView = recyclerView;
    }

    public void addFood() {
        mFoods.add(0, new Food());
        //notifyDataSetChanged();
        notifyItemInserted(0);
        notifyItemRangeChanged(0, mFoods.size());
        mRecyclerView.scrollToPosition(0);

    }
    public void removeFood(int index){
        mFoods.remove(index);
        notifyItemRemoved(index);
        notifyItemRangeChanged(0, mFoods.size());
    }

    @NonNull
    @Override
    public FoodViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.food_item_view, parent, false);
        return new FoodViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull FoodViewHolder holder, int position) {
        final Food food = mFoods.get(position);
        holder.mName.setText(food.getName());
        holder.mImageView.setImageResource(food.getImageResourceId());
        holder.mRatingBar.setRating(food.getRating());
    }

    @Override
    public int getItemCount() {
        return mFoods.size();
    }

    class FoodViewHolder extends RecyclerView.ViewHolder {
        private ImageView mImageView;
        private TextView mName;
        private RatingBar mRatingBar;

        public FoodViewHolder(@NonNull View itemView) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.food_pic);
            mName = itemView.findViewById(R.id.name);
            mRatingBar = itemView.findViewById(R.id.rating_bar);

            //TODO: Delete this food on long press
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                                                @Override
                                                public boolean onLongClick(View v) {
                                                    removeFood(getAdapterPosition());
                                                    return true;
                                                }
                                            });

            mRatingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
                @Override
                public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                    if (fromUser) {
                        Food currentFood = mFoods.get(getAdapterPosition());
                        currentFood.setRating(rating);
                    }
                }
            });
        }
    }
}
