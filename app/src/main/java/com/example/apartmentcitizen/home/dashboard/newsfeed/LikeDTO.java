package com.example.apartmentcitizen.home.dashboard.newsfeed;


import com.google.gson.annotations.SerializedName;

public class LikeDTO {

    @SerializedName("likeId")
    private long likeId;

    @SerializedName("user")
    private UserDTO user;

    @SerializedName("createdDate")
    private String createdDate;

    @SerializedName("post")
    private PostDTO post;

    public LikeDTO(long likeId, UserDTO user, String createdDate, PostDTO post) {
        this.likeId = likeId;
        this.user = user;
        this.createdDate = createdDate;
        this.post = post;
    }

    public long getLikeId() {
        return likeId;
    }

    public void setLikeId(long likeId) {
        this.likeId = likeId;
    }

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public PostDTO getPost() {
        return post;
    }

    public void setPost(PostDTO post) {
        this.post = post;
    }
}
