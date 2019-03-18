package com.example.pby.gam_study.network;

import com.example.pby.gam_study.network.bean.Card;
import com.example.pby.gam_study.network.bean.Comment;
import com.example.pby.gam_study.network.bean.DailyTask;
import com.example.pby.gam_study.network.bean.Kind;
import com.example.pby.gam_study.network.bean.Post;
import com.example.pby.gam_study.network.bean.User;
import com.example.pby.gam_study.page.login.bean.CodeBean;
import com.example.pby.gam_study.page.newKind.NewKindItem;

import java.util.List;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;


public interface Service {
    Observable<CodeBean> getCode();

    @POST("/user/findUser")
    @FormUrlEncoded
    Observable<User> findUser(@Field("id") String id);

    @POST("/kind/findAllKind")
    @FormUrlEncoded
    Observable<List<Kind>> findAllKind(@Field("userId") String userId);

    @POST("/kind/findRecentBrowseKind")
    @FormUrlEncoded
    Observable<List<Kind>> findRecentBrowseKind(@Field("userId") String userId);

    @POST("/kind/insertKind")
    @FormUrlEncoded
    Observable<Integer> insertKind(@Field("userId") String userId, @Field("name") String kindName, @Field("cover") String cover);

    @POST("/kind/updateTime")
    @FormUrlEncoded
    Observable<Boolean> updateTime(@Field("kindId") String kindId);

    @POST("/cover/findAllCover")
    Observable<List<NewKindItem>> findKindCover();

    @POST("/card/findAllCard")
    @FormUrlEncoded
    Observable<List<Card>> findAllCard(@Field("kindId") String kindId, @Field("userId") String userId);

    @POST("/card/insertCard")
    Observable<Card> insertCard(@Body RequestBody requestBody);

    @POST("/card/editCard")
    Observable<Card> editCard(@Body RequestBody requestBody);

    @POST("/daily/findDailyTask")
    @FormUrlEncoded
    Observable<List<DailyTask>> findDailyCard(@Field("userId") String userId);

    @POST("/daily/sign")
    @FormUrlEncoded
    Observable<Boolean> sign(@Field("userId") String userId);

    @POST("/daily/updateDailyCard")
    @FormUrlEncoded
    Observable<Boolean> updateDailyCard(@Field("userId") String userId, @Field("cardId") String cardId);

    @POST("/post/insertPost")
    Observable<Post> insertPost(@Body RequestBody requestBody);

    @POST("/post/findRecommendPost")
    Observable<List<Post>> findRecommendPost();

    @POST("/post/findFollowPost")
    @FormUrlEncoded
    Observable<List<Post>> findFollowPost(@Field("userId") String userId);

    @POST("/like/like")
    @FormUrlEncoded
    Observable<Boolean> like(@Field("userId") String userId, @Field("postId") String postId, @Field("isLike") boolean isLike);

    @POST("/comment/addComment")
    @FormUrlEncoded
    Observable<Comment> addComment(@Field("comment") Comment comment);
}
