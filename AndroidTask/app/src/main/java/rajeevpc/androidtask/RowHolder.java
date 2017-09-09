package rajeevpc.androidtask;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by RajeevPC on 5/4/2017.
 */
public class RowHolder extends RecyclerView.ViewHolder {

    public final ImageView imageview;
    public final TextView tvName, tvDOB, tvCountry, tvHeight;
    public Button btn;

    public RowHolder(View itemView) {
        super(itemView);
        btn = (Button) itemView.findViewById(R.id.details);
        imageview = (ImageView) itemView.findViewById(R.id.ivImage);
        tvName = (TextView) itemView.findViewById(R.id.tvName);
//        tvDescription = (TextView) itemView.findViewById(R.id.tvDescriptionn);
        tvDOB = (TextView) itemView.findViewById(R.id.tvDateOfBirth);
        tvCountry = (TextView) itemView.findViewById(R.id.tvCountry);
        tvHeight = (TextView) itemView.findViewById(R.id.tvHeight);
//        tvSpouse = (TextView) itemView.findViewById(R.id.tvSpouse);
//        tvChildren = (TextView) itemView.findViewById(R.id.tvChildren);

    }


}
