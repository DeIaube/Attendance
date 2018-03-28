package deu.hlju.dawn.studentattendance.network;


import java.util.Map;

import deu.hlju.dawn.studentattendance.bean.FaceDetectResult;
import deu.hlju.dawn.studentattendance.bean.FaceSearchResult;
import deu.hlju.dawn.studentattendance.bean.FaceSetAddResult;
import deu.hlju.dawn.studentattendance.bean.FaceSetCreateResult;
import deu.hlju.dawn.studentattendance.bean.FaceSetDetailResult;
import deu.hlju.dawn.studentattendance.bean.FaceSetRemoveResult;
import io.reactivex.Observable;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;

public interface Api {
    @POST("detect")
    Observable<FaceDetectResult> getFaceDetect(@QueryMap Map<String, String> fields);
    @POST("faceset/create")
    Observable<FaceSetCreateResult> createFaceset(@QueryMap Map<String, String> fields);
    @POST("faceset/removeface")
    Observable<FaceSetRemoveResult> removeFaceset(@QueryMap Map<String, String> fields);
    @POST("faceset/addface")
    Observable<FaceSetAddResult> addFaceset(@QueryMap Map<String, String> fields);
    @POST("faceset/getdetail")
    Observable<FaceSetDetailResult> detailFaceset(@QueryMap Map<String, String> fields);
    @POST("search")
    Observable<FaceSearchResult> searchFace(@QueryMap Map<String, String> fields);
}
