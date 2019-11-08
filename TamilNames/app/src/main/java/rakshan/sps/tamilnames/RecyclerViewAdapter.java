package rakshan.sps.tamilnames;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.zip.Inflater;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {

    private Context mContext;
    private List<DataModel> mAlphaList;
    private String NameCatagory;


    public RecyclerViewAdapter(Context mContext, List<DataModel> mAlphaList, String NameCatagory) {
        this.mContext = mContext;
        this.mAlphaList = mAlphaList;
        this.NameCatagory = NameCatagory;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View mview;
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        mview = mInflater.inflate(R.layout.abcd_cardview, viewGroup, false);
        return new MyViewHolder(mview);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        myViewHolder.alpha_textView.setText(mAlphaList.get(i).getAlphabet());
        myViewHolder.alpha_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView clickedTextView = (TextView) v.findViewById(R.id.element_textView);
                String clicked = clickedTextView.getText().toString();
                Log.d("Clicked Card :::: " + NameCatagory + "", clicked);
                Intent redirectToMain = new Intent(v.getContext(), MainActivity.class);
                redirectToMain.putExtra("CatagoryAndAlphabetLetter", NameCatagory + ":::" + clicked);
                v.getContext().startActivity(redirectToMain);

            }
        });
    }

    @Override
    public int getItemCount() {
        return mAlphaList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView alpha_textView;
        CardView alpha_card;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            alpha_textView = (TextView) itemView.findViewById(R.id.element_textView);
            alpha_card = (CardView) itemView.findViewById(R.id.alphabrtCardViewID);
        }
    }
}
