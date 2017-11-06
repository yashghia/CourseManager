package com.example.yash.hw6;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by yash_ on 11/5/2017.
 */

public class InstructorDescriptionAdaptor extends RecyclerView.Adapter<InstructorDescriptionAdaptor.ViewHolder> {

    ArrayList<Instructor> instructorsArrayList;
    static Realm realm;
    static Fragment fragment;
    static AlertDialog.Builder builder;
    IdeletedInstructor ideletedInstructor;
    static int pos;

    public InstructorDescriptionAdaptor(ArrayList<Instructor> instructorsArrayList, Fragment fragment, IdeletedInstructor ideletedInstructor){
        this.instructorsArrayList = instructorsArrayList;
        this.ideletedInstructor = ideletedInstructor;
        this.fragment = fragment;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.instructor_details, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Instructor instructor = instructorsArrayList.get(position);
        holder.instructor = instructor;
        holder.nameTextView.setText(instructor.getFname());
        holder.emailTextView.setText(instructor.getEmail());
        holder.websiteTextView.setText(instructor.getWebsite());
        holder.instructorImageView.setImageBitmap(BitmapFactory.decodeByteArray(instructor.getPic(), 0, instructor.getPic().length));
    }

    @Override
    public int getItemCount() {
        return instructorsArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView nameTextView, emailTextView, websiteTextView;
        ImageView instructorImageView;
        Instructor instructor;
        public ViewHolder(View itemView) {
            super(itemView);
            builder = new AlertDialog.Builder(itemView.getContext());
            nameTextView = (TextView) itemView.findViewById(R.id.idetailname);
            emailTextView = (TextView) itemView.findViewById(R.id.iemail);
            websiteTextView = (TextView) itemView.findViewById(R.id.iwebsite);
            instructorImageView = (ImageView) itemView.findViewById(R.id.instProfileImage);
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    builder.setTitle("Do you really want to delete this course?")
                            .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    realm = Realm.getDefaultInstance();
                                    pos = getAdapterPosition();
                                    Instructor delInst = instructorsArrayList.get(pos);
                                    Log.d("deletecourse",""+delInst.getFname());
                                    instructorsArrayList.remove(delInst);
                                    final RealmResults<Instructor> instructors = realm.where(Instructor.class).findAll();
                                    Instructor instructor = instructors.where().equalTo("email",delInst.getEmail()).findFirst();

                                    if(instructor!=null){
                                        if (!realm.isInTransaction()) {
                                            realm.beginTransaction();
                                        }
                                        instructor.deleteFromRealm();
                                        realm.commitTransaction();
                                    }
                                    ideletedInstructor.deletedInstructorRefresh(instructorsArrayList);
                                }
                            }).setNegativeButton("NO", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Log.d("delete","donot delete");
                        }
                    }).setCancelable(false);
                    final AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                    return false;
                }
            });
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int i = getAdapterPosition();
                    Instructor instructordetails = instructorsArrayList.get(i);
                    fragment.getFragmentManager().beginTransaction()
                            .replace(R.id.container,new InstructorDetailsFragment(instructordetails),"coursedetails")
                            .commit();
                }
            });
        }
    }
    interface IdeletedInstructor{
        void deletedInstructorRefresh(ArrayList<Instructor> instructors);
    }
}
