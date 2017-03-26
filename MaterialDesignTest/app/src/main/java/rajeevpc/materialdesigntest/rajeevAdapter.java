package rajeevpc.materialdesigntest;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Collections;
import java.util.List;

import static java.security.AccessController.getContext;

/**
 * Created by RajeevPC on 1/30/2017.
 */

public class rajeevAdapter extends RecyclerView.Adapter<rajeevAdapter.MyViewHolder> {
    private LayoutInflater inflator;
    private Context context;
    private ClickListner clickListner;
    int position;
    List<Information > data = Collections.emptyList();
    public rajeevAdapter(Context context,List<Information> data) {
        this.context=context;
        inflator = LayoutInflater.from(context);
        this.data  = data;

    }
    public void delete(int position){
        data.remove(position);
        notifyItemRemoved(position);

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflator.inflate(R.layout.custom_row, parent ,false);
        MyViewHolder holder = new MyViewHolder(view);
        Log.d("rajeev", "onCreateViewHolder: ");
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Information current = data.get(position);
        Log.d("rajeev", "onBindViewHolder: " + position);
        holder.title.setText(current.title);
        holder.icons.setImageResource(current.iconId);



    }
    public void setClickListner(ClickListner clickListner){
             this.clickListner = clickListner;
    }



    @Override
    public int getItemCount() {

        return data.size();
    }
    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView title;
        ImageView icons;

        public MyViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            title = (TextView) itemView.findViewById(R.id.listMessage);
            icons = (ImageView) itemView.findViewById(R.id.listIcon);
//            icons.setOnClickListener(this);


        }


        @Override
        public void onClick(View v) {

//            delete(getPosition());
            context.startActivity(new Intent(context,SubActivity.class));
            if(clickListner!=null){
                clickListner.itemClicked(v,getPosition());
            }

        }
    }
    public interface ClickListner{
        public void itemClicked(View view, int position);

    }
}
