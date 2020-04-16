package com.learn.model;

import java.io.Serializable;

/**
 * @author dshuyou
 * @date 2019/11/24 16:48
 */
public class Comment_copy implements Serializable {
    //INSERT INTO comment_copy (comment_id, music_id, content, liked_count,time,user_id,nickname,user_img ) VALUES (1,1,1,'1',1,1,'1','1')
    private long comment_id;
    private long music_id;
    private String content;
    private long liked_count;
    private long time;
    private long user_id;
    private String nickname;
    private String user_img;

    public long getComment_id() {
        return comment_id;
    }

    public void setComment_id(long comment_id) {
        this.comment_id = comment_id;
    }

    public long getMusic_id() {
        return music_id;
    }

    public void setMusic_id(int music_id) {
        this.music_id = music_id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public long getLiked_count() {
        return liked_count;
    }

    public void setLiked_count(long liked_count) {
        this.liked_count = liked_count;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public long getUser_id() {
        return user_id;
    }

    public void setUser_id(long user_id) {
        this.user_id = user_id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getUser_img() {
        return user_img;
    }

    public void setUser_img(String user_img) {
        this.user_img = user_img;
    }

    @Override
    public String toString() {
        return "Comment_copy{" +
                "comment_id=" + comment_id +
                ", music_id=" + music_id +
                ", content='" + content + '\'' +
                ", liked_count=" + liked_count +
                ", time=" + time +
                ", user_id=" + user_id +
                ", nickname='" + nickname + '\'' +
                ", user_img='" + user_img + '\'' +
                '}';
    }
}
