package rakshan.sps.tamilnames;

import android.content.Context;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

public class ViewHolder extends RecyclerView.ViewHolder {

    View mView;

    public ViewHolder(View itemView) {
        super(itemView);
        mView = itemView;
    }

    public void setTitleAndDescription(Context con, String title, String desc, String gender) {

        TextView postTitle = mView.findViewById(R.id.xml_title_post);
        TextView description = mView.findViewById(R.id.xml_meaning);
        ImageView imagePost = mView.findViewById(R.id.list_image);
        LinearLayout layout_color = mView.findViewById(R.id.thumbnail);

        postTitle.setText(title);
        description.setText(desc);

        if (gender.equals("Boy")) {
            Picasso.get().load(R.drawable.boy_black).into(imagePost);
            layout_color.setBackgroundResource(R.drawable.background_oval);

        } else if (gender.equals("King")) {
            Picasso.get().load(R.drawable.tamil_kings).into(imagePost);
            layout_color.setBackgroundResource(R.drawable.tamil_king_oval);

        } else if (gender.equals("Sangam")) {
            Picasso.get().load(R.drawable.sangamicon).into(imagePost);
            layout_color.setBackgroundResource(R.drawable.sangam_oval);

        } else {
            Picasso.get().load(R.drawable.girl_black).into(imagePost);
            layout_color.setBackgroundResource(R.drawable.baby_girl_oval);
        }


    }

    public void setBirthDayDetails(Context conObj,String title,String shortDescri, String ImageURL){
        TextView postTitle = mView.findViewById(R.id.birthday_person_name);
        TextView descTitle = mView.findViewById(R.id.birthday_person_desp);
        ImageView imagePost = mView.findViewById(R.id.birthday_person);

        Log.d("DeBug",ImageURL);
        Log.d("DeBug Text",title);

        postTitle.setText(title);
        descTitle.setText(shortDescri);
        Picasso.get().load(ImageURL).into(imagePost);
    }


    public void setDetails(Context con, String title, String ImageUrl) {

        TextView postTitle = mView.findViewById(R.id.xml_title_post);
        //ImageView imagePost = mView.findViewById(R.id.xml_image);

        postTitle.setText(title);
        //Picasso.get().load("@drawable/girl_black").into(imagePost);

    }


}
