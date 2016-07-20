package Adapters;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import gcm.play.android.samples.com.gcmquickstart.R;

import Utils.webservices.UserSelection;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by Maimoona on 5/18/2016.
 */
public class SendToAdapter extends BaseAdapter {

    public List<UserSelection> _data;
    private ArrayList<UserSelection> arraylist;
    Context _c;
    ViewHolder v;
    //RoundImage roundedImage;
    String de;
    // public SharedPreferences pref = _c.getSharedPreferences("MyPref",_c.MODE_PRIVATE);
    // SharedPreferences.Editor editor = pref.edit();

    public SendToAdapter(List<UserSelection> selectUsers, Context context) {
        _data = selectUsers;
        _c = context;
        this.arraylist = new ArrayList<UserSelection>();
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

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public View getView(int i, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub

        LayoutInflater inflater=(LayoutInflater) _c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = convertView;
        final ViewHolder viewHolder;
        if(convertView==null)
        {
            view=inflater.inflate(R.layout.contacts,parent,false);
        }

        //listView=new View(context);
        v = new ViewHolder();

    /*public View getView(int i, View convertView, ViewGroup viewGroup) {
        View view = convertView;
        if (view == null) {
            LayoutInflater li = (LayoutInflater) _c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = li.inflate(R.layout.contacts, null);
            Log.e("Inside", "here--------------------------- In view1");
        } else {
            view = convertView;
            Log.e("Inside", "here--------------------------- In view2");
        }

        v = new ViewHolder();
*/
        v.title = (TextView) view.findViewById(R.id.name);
        v.check = (RadioButton) view.findViewById(R.id.check);
        v.phone = (TextView) view.findViewById(R.id.no);
        v.imageView = (ImageView) view.findViewById(R.id.pic);

        final UserSelection data = (UserSelection) _data.get(i);
        v.title.setText(data.getName());
        v.check.setChecked(data.getCheckedBox());
        v.phone.setText(data.getPhone());

        // Set image if exists
        try {
            if (data.getThumb() != null) {

                //roundedImage = new RoundImage(data.getThumb());
                // v.imageView.setImageDrawable(roundedImage);
                v.imageView.setImageBitmap(data.getThumb());

                // v.imageView.setImageDrawable(roundedImage);
                // Bitmap icon = BitmapFactory.decodeResource(view.getResources(), R.drawable.images);
            } else {
                Bitmap icon = BitmapFactory.decodeResource(_c.getResources(),
                        R.drawable.images);

                // roundedImage = new RoundImage(icon);
                v.imageView.setImageBitmap(icon);
            }
            //Seting round image
            // Bitmap bm = BitmapFactory.decodeResource(view.getResources(), R.drawable.images); // Load default image
            // roundedImage = new RoundImage(bm);
            //  v.imageView.setImageDrawable(roundedImage);
        } catch (OutOfMemoryError e) {
            // Add default picture
            // v.imageView.setImageDrawable(this._c.getResources().getDrawable(R.drawable.image));
            //  e.printStackTrace();
        }

        Log.e("Image Thumb", "--------------" + data.getThumb());

        // Set check box listener android
        v.check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RadioButton radioButton = (RadioButton) view;
               /* if (radioButton.isChecked()) {
                    data.setRadioButton(true);
                    Toast.makeText(_c, data.getName()+" is select for shar", Toast.LENGTH_LONG).show();

                }*/

                if (radioButton.isChecked()) {
                    if (v.check != null) {
                        v.check.setChecked(false);
                        de=data.getName();
                    }
                    radioButton.setChecked(true);
                    v.check = radioButton;

                    Toast.makeText(_c, de, Toast.LENGTH_LONG).show();

                    // editor.putString("key_name", "yes");
                    // editor.commit();
                }
                else {
                    data.setRadioButton(false);
                }
                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(_c);
                prefs.edit().putString("sendtoName", data.getName()).commit();
                prefs.edit().putString("sendtoPhone", data.getPhone()).commit();

                /*Intent intent = new Intent(this, ContactActivity.class);
                intent.putExtra("Selected_Item", selectedItemData);
                startActivity(intent)*/
            }
        });

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
            for (UserSelection wp : arraylist) {
                if (wp.getName().toLowerCase(Locale.getDefault())
                        .contains(charText)) {
                    _data.add(wp);
                }
            }
        }

        notifyDataSetChanged();

    }
    static class ViewHolder {
        ImageView imageView;
        TextView title, phone;
        RadioButton check;
    }
}
