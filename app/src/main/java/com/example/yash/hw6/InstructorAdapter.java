/*Assignment : Inclass08
Yash Ghia
Prabhakar Teja Seeda*/


package com.example.yash.hw6;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by teja on 10/30/17.
 */

public class InstructorAdapter extends RecyclerView.Adapter<InstructorAdapter.ViewHolder> {
    ArrayList<Instructor> instructorsResults;
    Context context;
    IselectedInstructor inst;
    public InstructorAdapter(ArrayList<Instructor> instructors, Context context,IselectedInstructor iselectedInstructor) {
        this.instructorsResults = instructors;
        this.context = context;
        this.inst = iselectedInstructor;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.instructordetails, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Instructor instructor = instructorsResults.get(position);
        Log.d("instadapter-pic",""+instructor.getPic());
        holder.instructorAdapter = instructor;
        holder.name.setText(instructor.getFname());
        holder.instImage.setImageBitmap(BitmapFactory.decodeByteArray(instructor.getPic(), 0, instructor.getPic().length));
        holder.selected.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                inst.selected(instructor);
            }
        });
        }

    @Override
    public int getItemCount() {
        return instructorsResults.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        ImageView instImage;
        Instructor instructorAdapter;
        RadioButton selected;

        public ViewHolder(final View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.instName);
            instImage = (ImageView) itemView.findViewById(R.id.instImage);
            selected = (RadioButton) itemView.findViewById(R.id.selected);
        }
    }

    interface IselectedInstructor{
        void selected(Instructor instructor);
    }
}
