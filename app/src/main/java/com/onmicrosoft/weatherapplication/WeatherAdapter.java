package com.onmicrosoft.weatherapplication;

import android.app.Activity;
import android.content.Intent;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class WeatherAdapter extends RecyclerView.Adapter<WeatherAdapter.ActionListHolder> {
    private static final String TAG = WeatherAdapter.class.getSimpleName();
    private final Activity context;
    private List<ListModel> actionList;


    public class ActionListHolder extends RecyclerView.ViewHolder {

        public TextView name,temp;

        RelativeLayout rl;

        public ActionListHolder(View view) {
            super(view);
            try {
                name = (TextView) view.findViewById(R.id.name);
                temp = (TextView) view.findViewById(R.id.temp);
                rl = (RelativeLayout) view.findViewById(R.id.rl);

            } catch (Exception e) {

            }
        }
    }

    public WeatherAdapter(Activity context, List<ListModel> actionList) {
        this.context = context;
        this.actionList = actionList;
        //System.out.println("come to recycler");

    }

    @Override
    public WeatherAdapter.ActionListHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new WeatherAdapter.ActionListHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final WeatherAdapter.ActionListHolder actionListHolder, int position) {
        try {

            //actionListHolder.tvName

            final ListModel actionListModel = actionList.get(position);

            actionListHolder.name.setText(actionListModel.getName());
            actionListHolder.temp.setText("Temp: "+actionListModel.getTemp());

            actionListHolder.rl.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(context,ThirdActivity.class);
                    i.putExtra("Temp",actionListModel.getTemp());
                    i.putExtra("feels_like",actionListModel.getFeels_like());
                    i.putExtra("main",actionListModel.getMain());
                    i.putExtra("desc",actionListModel.getDesc());
                    context.startActivity(i);
                }
            });


        } catch (Exception e) {

            e.getMessage();
        }
    }

    @Override
    public int getItemCount() {
        return actionList.size();
    }


}

