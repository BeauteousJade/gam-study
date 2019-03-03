package com.example.pby.gam_study.network;

import com.example.pby.gam_study.network.bean.Card;
import com.example.pby.gam_study.network.bean.Kind;
import com.example.pby.gam_study.network.bean.User;
import com.example.pby.gam_study.page.login.bean.CodeBean;
import com.example.pby.gam_study.page.newKind.NewKindItem;

import java.util.List;

import io.reactivex.Observable;
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

    @POST("/kind/insertKind")
    @FormUrlEncoded
    Observable<Integer> insertKind(@Field("userId") String userId, @Field("name") String kindName, @Field("cover") String cover);

    @POST("/cover/findAllCover")
    Observable<List<NewKindItem>> findKindCover();

    @POST("/card/findAllCard")
    @FormUrlEncoded
    Observable<List<Card>> findAllCard(@Field("kindId") String kindId);
}
