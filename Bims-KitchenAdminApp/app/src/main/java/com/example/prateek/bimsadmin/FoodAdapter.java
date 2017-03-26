package com.example.prateek.bimsadmin;

import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import java.util.List;

/**
 * Created by prateek on 7/10/16.
 */
public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.MyViewHolder> {

    private List<Food> foodList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView food, price;
        public ImageView foodItemIcon;

        public MyViewHolder(View view) {
            super(view);
            food = (TextView) view.findViewById(R.id.food);
            price = (TextView) view.findViewById(R.id.price);
            foodItemIcon = (ImageView) view.findViewById(R.id.foodItemIcon);
        }
    }


    public FoodAdapter(List<Food> foodList) {
        this.foodList = foodList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.food_item, parent, false);

        return new MyViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Food food = foodList.get(position);
        holder.food.setText(food.getFood());
        holder.price.setText("Rs. " +food.getPrice());

        Picasso.with(holder.foodItemIcon.getContext())
                .load(food.getImageUrl())
                .transform(new CircleTransform())
                .into(holder.foodItemIcon);
    }


    @Override
    public int getItemCount() {
        return foodList.size();
    }
}

class CircleTransform implements Transformation {
    @Override
    public Bitmap transform(Bitmap source) {
        int size = Math.min(source.getWidth(), source.getHeight());

        int x = (source.getWidth() - size + 1) / 2;
        int y = (source.getHeight() - size + 1) / 2;

        Bitmap squaredBitmap = Bitmap.createBitmap(source, x, y, size, size);
        if (squaredBitmap != source) {
            source.recycle();
        }

        Bitmap bitmap = Bitmap.createBitmap(size, size, source.getConfig());

        Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint();
        BitmapShader shader = new BitmapShader(squaredBitmap,
                BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP);
        paint.setShader(shader);
        paint.setAntiAlias(true);

        float r = size / 2f;
        canvas.drawCircle(r, r, r, paint);

        squaredBitmap.recycle();
        return bitmap;
    }

    @Override
    public String key() {
        return "circle";
    }
}
