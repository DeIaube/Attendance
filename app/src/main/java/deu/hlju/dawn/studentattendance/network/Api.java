package deu.hlju.dawn.studentattendance.network;


import java.util.Map;

import deu.hlju.dawn.studentattendance.bean.FaceDetectResult;
import deu.hlju.dawn.studentattendance.bean.FaceSearchResult;
import deu.hlju.dawn.studentattendance.bean.FaceSetAddResult;
import deu.hlju.dawn.studentattendance.bean.FaceSetCreateResult;
import deu.hlju.dawn.studentattendance.bean.FaceSetDetailResult;
import deu.hlju.dawn.studentattendance.bean.FaceSetRemoveResult;
import io.reactivex.Observable;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;

public interface Api {

    @FormUrlEncoded
    @POST("detect")
    Observable<FaceDetectResult> getFaceDetect(@FieldMap Map<String, String> fields);
    @FormUrlEncoded
    @POST("faceset/create")
    Observable<FaceSetCreateResult> createFaceset(@FieldMap Map<String, String> fields);
    @FormUrlEncoded
    @POST("faceset/removeface")
    Observable<FaceSetRemoveResult> removeFaceset(@FieldMap Map<String, String> fields);
    @FormUrlEncoded
    @POST("faceset/addface")
    Observable<FaceSetAddResult> addFaceset(@FieldMap Map<String, String> fields);
    @FormUrlEncoded
    @POST("faceset/getdetail")
    Observable<FaceSetDetailResult> detailFaceset(@FieldMap Map<String, String> fields);
    @FormUrlEncoded
    @POST("search")
    Observable<FaceSearchResult> searchFace(@FieldMap Map<String, String> fields);
}
