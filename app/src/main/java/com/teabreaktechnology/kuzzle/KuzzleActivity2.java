package com.teabreaktechnology.kuzzle;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class KuzzleActivity2 extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kuzzle2);
        //findViewById(R.id.)

        int[] colors = new int[]{1,2,3};
        int[] imageIds = new int[]{R.id.img1,R.id.img2,R.id.img3};
        int[] row2colors = new int[]{0,4,5};
        int[] row2images = new int[]{R.id.img4,R.id.img5,R.id.img6};

        for(int i=0;i<colors.length;i++){
            int id = colors[i];
            ImageView imageView = (ImageView) findViewById(imageIds[i]);
            CharacterDrawable drawable = new CharacterDrawable(Constants.color_char[id],Constants.colorCodes[id]);
            imageView.setImageDrawable(drawable);
        }

        for(int i=0;i<colors.length;i++){
            int id = row2colors[i];
            ImageView imageView = (ImageView) findViewById(row2images[i]);
            CharacterDrawable drawable = new CharacterDrawable(Constants.color_char[id],Constants.colorCodes[id]);
            imageView.setImageDrawable(drawable);
        }

    }


}
