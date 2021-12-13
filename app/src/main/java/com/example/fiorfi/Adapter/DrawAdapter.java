package com.example.fiorfi.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.fiorfi.CommentsActivity;
import com.example.fiorfi.FollowersActivity;
import com.example.fiorfi.Fragment.PostDetailFragment;
import com.example.fiorfi.Fragment.ProfileFragment;
import com.example.fiorfi.Model.Draw;
import com.example.fiorfi.Model.Post;
import com.example.fiorfi.Model.User;
import com.example.fiorfi.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.List;

public class DrawAdapter extends RecyclerView.Adapter<DrawAdapter.ViewHolder> {

    public Context mContext;
    public List<Draw> mDraw;

    private FirebaseUser firebaseuser;

public DrawAdapter(Context mContext, List<Draw> mDraw){
    this.mContext=mContext;
   this.mDraw=mDraw;
}

    @NonNull
    @Override
    public DrawAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(mContext).inflate(R.layout.draw2_item,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DrawAdapter.ViewHolder holder, int position) {

        firebaseuser = FirebaseAuth.getInstance().getCurrentUser();
        final Draw draw = mDraw.get(position);

        Glide.with(mContext).load(draw.getPostimage()).into(holder.post_image);


        if(draw.getDescription().equals("")){

    holder.description.setVisibility(View.GONE);
        }else {
            holder.description.setVisibility(View.VISIBLE);
            holder.description.setText(draw.getDescription());
        }

        publisherInfo(holder.image_profile,holder.username,holder.publisher,draw.getPublisher());

    }

    @Override
    public int getItemCount() {
        return mDraw.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public ImageView image_profile,post_image,like,comment;
        public TextView username,likes,publisher,description,comments;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            image_profile=itemView.findViewById(R.id.image_profile);
            post_image=itemView.findViewById(R.id.post_image);
            like=itemView.findViewById(R.id.like);
            comment=itemView.findViewById(R.id.comment);
            username=itemView.findViewById(R.id.username);
            likes=itemView.findViewById(R.id.likes);
            publisher=itemView.findViewById(R.id.publisher);
            description=itemView.findViewById(R.id.description);
            comments=itemView.findViewById(R.id.comments);

        }
    }
    private void publisherInfo(final ImageView image_profile, final TextView username, final TextView publisher, final String userid){
        DatabaseReference reference= FirebaseDatabase.getInstance().getReference("Users").child(userid);


        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);
                Glide.with(mContext).load(user.getImageurl()).into(image_profile);
                username.setText(user.getUsername());
                publisher.setText(user.getUsername());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
