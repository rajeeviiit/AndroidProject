package rajeevpc.phonebook;

import java.util.ArrayList;
import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;


import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by Rajeev on 4/14/2017.
 */

class SelectUserAdapter extends BaseAdapter{
    public List<SelectUser> _data;
    private ArrayList<SelectUser> arraylist;
    Context _c;
    ViewHolder v;
    //RoundImage roundedImage;

    public SelectUserAdapter(List<SelectUser> selectUsers, Context context) {
        _data = selectUsers;
        _c = context;
        this.arraylist = new ArrayList<SelectUser>();
        this.arraylist.addAll(_data);
    }

    @Override
    public int getCount() {
        return _data.size();
    }

    @Override
    public Object getItem(int i) {
        return _data.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        View view = convertView;
        if (view == null) {
            LayoutInflater li = (LayoutInflater) _c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = li.inflate(R.layout.listview, null);
            Log.e("Inside", "here--------------------------- In view1");
        } else {
            view = convertView;
            Log.e("Inside", "here--------------------------- In view2");
        }

        v = new ViewHolder();

        v.title = (TextView) view.findViewById(R.id.name);
        //v.check = (CheckBox) view.findViewById(R.id.check);
        v.phone = (TextView) view.findViewById(R.id.no);
        v.imageView = (ImageView) view.findViewById(R.id.pic);

        final SelectUser data = (SelectUser) _data.get(i);
        v.title.setText(data.getName());
//        v.check.setChecked(data.getCheckedBox());
        v.phone.setText(data.getPhone());

        // Set image if exists
        try {

            if (data.getThumb() != null) {
                v.imageView.setImageBitmap(data.getThumb());
            } else {
                v.imageView.setImageResource(R.drawable.ic_launcher_round);
            }
            // Seting round image
            Bitmap bm = BitmapFactory.decodeResource(view.getResources(), R.drawable.ic_launcher_round); // Load default image
            //roundedImage = new RoundImage(bm);
            //v.imageView.setImageDrawable(roundedImage);
        } catch (OutOfMemoryError e) {
            // Add default picture
            v.imageView.setImageDrawable(this._c.getDrawable(R.drawable.ic_launcher_round));
            e.printStackTrace();
        }

        Log.e("Image Thumb", "--------------" + data.getThumb());


        view.setTag(data);
        return view;
    }

    // Filter Class
    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        _data.clear();
        if (charText.length() == 0) {
            _data.addAll(arraylist);
        } else {
            for (SelectUser wp : arraylist) {
                if (wp.getName().toLowerCase(Locale.getDefault())
                        .startsWith(charText) || wp.getPhone().startsWith(charText)) {
                    _data.add(wp);
                }
            }
        }
        notifyDataSetChanged();
    }

    //    // Filter Class
//    public void filter2(int charText) {
//        int myNum = 0;
//
//        try {
//            myNum = Integer.parseInt(charText.getText().toString());
//        } catch(NumberFormatException nfe) {
//
//        }
//        _data.clear();
//        if (myNum.length() == 0) {
//            _data.addAll(arraylist);
//        } else {
//            for (SelectUser wp : arraylist) {
//                if (wp.getName().contains(myNum)) {
//                    _data.add(wp);
//                }
//            }
//        }
//        notifyDataSetChanged();
//    }
    static class ViewHolder {
        ImageView imageView;
        TextView title, phone;

    }
}