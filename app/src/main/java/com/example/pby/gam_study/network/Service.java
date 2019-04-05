package com.example.pby.gam_study.network;

import com.example.pby.gam_study.network.bean.Card;
import com.example.pby.gam_study.network.bean.Comment;
import com.example.pby.gam_study.network.bean.DailyTask;
import com.example.pby.gam_study.network.bean.Kind;
import com.example.pby.gam_study.network.bean.Message;
import com.example.pby.gam_study.network.bean.MessageItem;
import com.example.pby.gam_study.network.bean.Post;
import com.example.pby.gam_study.network.bean.User;
import com.example.pby.gam_study.network.response.body.MineResponseBody;
import com.example.pby.gam_study.network.response.body.UserProfileResponseBody;
import com.example.pby.gam_study.page.login.bean.CodeBean;
import com.example.pby.gam_study.page.newKind.NewKindItem;

import java.util.List;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;


public interface Service {
    Observable<CodeBean> getCode();

    @POST("/user/findUser")
    @FormUrlEncoded
    Observable<User> findUser(@Field("id") String id);

    @POST("/user/profile")
    @FormUrlEncoded
    Observable<UserProfileResponseBody> getUserProfile(@Field("fromUserId") String fromUserId, @Field("toUserId") String toUserId);

    @POST("/user/mine")
    @FormUrlEncoded
    Observable<MineResponseBody> findUserInfo(@Field("id") String id);

    @POST("/user/updateAvatar")
    Observable<String> updateAvatar(@Body RequestBody requestBody);

    @POST("/user/findFollowList")
    @FormUrlEncoded
    Observable<List<User>> findFollowList(@Field("userId") String userId);

    @POST("/user/findFansList")
    @FormUrlEncoded
    Observable<List<User>> findFansList(@Field("userId") String userId);

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

    @POST("/post/findPost")
    @FormUrlEncoded
    Observable<List<Post>> findPost(@Field("userId") String userId);

    @POST("/like/like")
    @FormUrlEncoded
    Observable<Boolean> like(@Field("userId") String userId, @Field("postId") String postId, @Field("isLike") boolean isLike);

    @POST("/comment/addComment")
    @FormUrlEncoded
    Observable<Comment> addComment(@Field("comment") String json);

    @POST("/follow/followUser")
    @FormUrlEncoded
    Observable<Boolean> followUser(@Field("fromUserId") String fromUserId, @Field("toUserId") String toUserId);

    @POST("/follow/unFollowUser")
    @FormUrlEncoded
    Observable<Boolean> unFollowUser(@Field("fromUserId") String fromUserId, @Field("toUserId") String toUserId);

    @POST("/im/findMessageItem")
    @FormUrlEncoded
    Observable<List<MessageItem>> findMessageItem(@Field("userId") String userId);

    @POST("/im/findSingleMessageItem")
    @FormUrlEncoded
    Observable<MessageItem> findSingleMessageItem(@Field("fromUserId") String fromUserId, @Field("toUserId") String toUserId);

    @POST("/im/findHistoryMessage")
    @FormUrlEncoded
    Observable<List<Message>> findHistoryMessage(@Field("fromUserId") String fromUserId, @Field("toUserId") String toUserId,
                                                 @Field("startTime") long startTime, @Field("endTime") long endTime);

    @POST("/im/sendMessage")
    Observable<Boolean> sendMessage(@Body RequestBody requestBody);

    @POST("/im/resetFromUserUnReadCount")
    @FormUrlEncoded
    Observable<Boolean> resetFromUserUnReadCount(@Field("id") String id);

    @POST("/im/resetToUserUnReadCount")
    @FormUrlEncoded
    Observable<Boolean> resetToUserUnReadCount(@Field("id") String id);

    @POST("/im/deleteMessageItemForFromUser")
    @FormUrlEncoded
    Observable<Boolean> deleteMessageItemForFromUser(@Field("id") String id);

    @POST("/im/deleteMessageItemForToUser")
    @FormUrlEncoded
    Observable<Boolean> deleteMessageItemForToUser(@Field("id") String id);

    @POST("/apk/downloadApk")
    @FormUrlEncoded
    Call<ResponseBody> downloadApk(@Field("url") String url);

    @POST("/apk/checkUpdate")
    @FormUrlEncoded
    Observable<String> checkUpdate(@Field("code") String code);
}
