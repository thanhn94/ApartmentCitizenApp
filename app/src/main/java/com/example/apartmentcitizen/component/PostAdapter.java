package com.example.apartmentcitizen.component;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.apartmentcitizen.R;
import com.example.apartmentcitizen.home.dashboard.newsfeed.PostDTO;
import com.example.apartmentcitizen.home.dashboard.newsfeed.PostImageDTO;
import com.example.apartmentcitizen.network.PostImageService;
import com.example.apartmentcitizen.network.RetrofitInstance;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.ViewHolder> {

    Context context;
    List<PostDTO> listPost;
    boolean isPress = false;
    Retrofit retrofit;

    public PostAdapter(Context context, List<PostDTO> listPost) {
        this.context = context;
        this.listPost = listPost;
        retrofit = RetrofitInstance.getRetrofitInstance();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_post, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        if (listPost.get(position).getUser().getProfileImage() == null || listPost.get(position).getUser().getProfileImage().isEmpty()) {
            holder.imgAvatar.setImageResource(R.drawable.image_avatar_default);
        } else {
            Glide.with(context)
                    .load(RetrofitInstance.BASE_URL + RetrofitInstance.VERSION_API
                            + RetrofitInstance.GET_USER_IMAGE + listPost.get(position).getUser().getProfileImage())
                    .into(holder.imgAvatar);
        }
        holder.txtFullname.setText(listPost.get(position).getUser().getLastName() + " " + listPost.get(position).getUser().getFirstName());
        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS+SSSS");
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy").withLocale(Locale.getDefault()).withZone(ZoneId.systemDefault());
        LocalDate date = LocalDate.parse(listPost.get(position).getCreatedDate(), inputFormatter);
        String formattedDate = outputFormatter.format(date);
        holder.txtTime.setText(formattedDate);
        if (listPost.get(position).getDesc() == null || listPost.get(position).getDesc().isEmpty()) {
            holder.txtDesc.setVisibility(View.GONE);
        } else {
            holder.txtDesc.setVisibility(View.VISIBLE);
            holder.txtDesc.setText(listPost.get(position).getDesc());
        }
        holder.btnLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isPress) {
                    v.setBackgroundResource(R.drawable.button_unlike);
                    isPress = false;
                } else {
                    v.setBackgroundResource(R.drawable.button_liked);
                    isPress = true;
                }
            }
        });
        //get post image list
        final int postId = listPost.get(position).getPostId();
        PostImageService postImageService = retrofit.create(PostImageService.class);
        Call<List<PostImageDTO>> callPostImage = postImageService.getAllPostImage();
        callPostImage.enqueue(new Callback<List<PostImageDTO>>() {
            @Override
            public void onResponse(Call<List<PostImageDTO>> call, Response<List<PostImageDTO>> response) {
                List<PostImageDTO> imageDTOList = response.body();
                for (PostImageDTO postImageDTO : imageDTOList) {
                    if (postImageDTO.getPost().getPostId() == postId) {
                        if (postImageDTO.getImageUrl() == null || postImageDTO.getImageUrl().isEmpty()) {
                            holder.imgPost.setVisibility(View.INVISIBLE);
                        } else {
                            Glide.with(context)
                                    .load(RetrofitInstance.BASE_URL + RetrofitInstance.VERSION_API
                                            + RetrofitInstance.GET_POSTIMAGE_IMAGE + postImageDTO.getImageUrl())
                                    .into(holder.imgPost);
                        }
                    }
                }
            }
            @Override
            public void onFailure(Call<List<PostImageDTO>> call, Throwable t) {
                Toast.makeText(context, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
        
    }

    @Override
    public int getItemCount() {
        return listPost.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgAvatar, imgPost;
        TextView txtFullname, txtTime, txtDesc, txtCountLike, txtCountComment;
        ConstraintLayout parent;
        CircleImageView btnLike;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgAvatar = itemView.findViewById(R.id.post_avatar);
            imgPost = itemView.findViewById(R.id.post_image);
            txtFullname = itemView.findViewById(R.id.post_fullname);
            txtTime = itemView.findViewById(R.id.post_time);
            txtDesc = itemView.findViewById(R.id.post_desc);
            txtCountLike = itemView.findViewById(R.id.post_count_like);
            txtCountComment = itemView.findViewById(R.id.post_count_comment);
            btnLike = itemView.findViewById(R.id.post_button_like);
            parent = itemView.findViewById(R.id.list_newsfeed);
        }

    }
}
