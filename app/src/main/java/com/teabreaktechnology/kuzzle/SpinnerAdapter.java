package com.teabreaktechnology.kuzzle;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by nikil on 25-Jul-16.
 */
public class SpinnerAdapter extends ArrayAdapter<ItemData> {
    int groupid;
    char letter =' ';
    int color;
    Activity context;
    ArrayList<ItemData> list;
    LayoutInflater inflater;

    public SpinnerAdapter(Activity context, int groupid, int id, ArrayList<ItemData>
            list) {
        super(context, id, list);
        this.list = list;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.groupid = groupid;
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        View itemView = inflater.inflate(groupid, parent, false);
        TextView textView = (TextView) itemView.findViewById(R.id.txt);
        textView.setText(list.get(position).getText());
        /**getting the text to string
         * retriving the first letter
         * getting the color code
         * retriving the color code
         * implement in CharacterDrawable
         **/
        String s = list.get(position).getText().toString();
        for (int i = 0; i < 1; i++)
        {
            // Get char at this index and display it.
            //letter = s.charAt(i);
            //System.out.println(letter);
        }
        ImageView imageView = (ImageView) itemView.findViewById(R.id.img);
        color = list.get(position).getImageId();
        System.out.println(color);
        CharacterDrawable drawable = new CharacterDrawable(letter,color);
        imageView.setImageDrawable(drawable);

        return itemView;
    }

    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return getView(position, convertView, parent);

    }
}
