package rajeevpc.androidtask;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.InputStream;
import java.util.ArrayList;


public class ActorsAdapter extends RecyclerView.Adapter<RowHolder> {
    ArrayList<Actors> actorList;
    static Actors actors;
    Context vi;

    SharedPreferences sharedpreferences;
    SharedPreferences.Editor editor;

    OnDetailListener listener;

    public ActorsAdapter(Context context, ArrayList<Actors> objects, OnDetailListener listener) {
        this.listener = listener;
        vi = context;
        actorList = objects;
        sharedpreferences = vi.getSharedPreferences("mpref", Context.MODE_PRIVATE);
        editor = sharedpreferences.edit();
    }

    @Override
    public RowHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(vi).inflate(R.layout.row, null, false);
        return new RowHolder(v);
    }


    @Override
    public void onBindViewHolder(RowHolder holder, final int position) {

        new DownloadImageTask(holder.imageview).execute(actorList.get(position).getImage());
        holder.tvName.setText(actorList.get(position).getName());
//        holder.tvDescription.setText(actorList.get(position).getDescription());
        holder.tvDOB.setText("B'day: " + actorList.get(position).getDob());
        holder.tvCountry.setText(actorList.get(position).getCountry());
        holder.tvHeight.setText("Height: " + actorList.get(position).getHeight());
//        holder.tvSpouse.setText("Spouse: " + actorList.get(position).getSpouse());
//        holder.tvChildren.setText("Children: " + actorList.get(position).getChildren());
        holder.btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    actors = actorList.get(position);
                    editor.putString("des", actors.getDescription());
                    listener.onDetails(actors);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return actorList.size();
    }

    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }

    }

    public interface OnDetailListener {
        void onDetails(Actors actors);
    }

}