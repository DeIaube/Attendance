package deu.hlju.dawn.studentattendance.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import deu.hlju.dawn.studentattendance.bean.FaceDetectResult;
import deu.hlju.dawn.studentattendance.bean.FaceSearchResult;
import deu.hlju.dawn.studentattendance.bean.FaceSetCreateResult;
import deu.hlju.dawn.studentattendance.bean.FaceSetDetailResult;
import deu.hlju.dawn.studentattendance.bean.FaceSetRemoveResult;
import deu.hlju.dawn.studentattendance.network.Request;
import deu.hlju.dawn.studentattendance.ui.add_student.AddStudnetActivity;
import deu.hlju.dawn.studentattendance.ui.camera.CameraActivity;
import deu.hlju.dawn.studentattendance.ui.show_student.ShowStudentActivity;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LinearLayout ll = new LinearLayout(this);
        setContentView(ll);
        ll.setOrientation(LinearLayout.VERTICAL);

        Button btn1 = new Button(this);
        btn1.setText("添加学生");
        ll.addView(btn1);

        Button btn2 = new Button(this);
        btn2.setText("展示学生");
        ll.addView(btn2);

        Button btn3 = new Button(this);
        btn3.setText("上传照片");
        ll.addView(btn3);

        Button btn4 = new Button(this);
        btn4.setText("创建照片集合");
        ll.addView(btn4);

        Button btn5 = new Button(this);
        btn5.setText("返回照片集合");
        ll.addView(btn5);

        Button btn6 = new Button(this);
        btn6.setText("删除照片集合");
        ll.addView(btn6);

        Button btn7 = new Button(this);
        btn7.setText("人脸识别");
        ll.addView(btn7);

        Button btn8 = new Button(this);
        btn8.setText("摄像头预览");
        ll.addView(btn8);

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddStudnetActivity.start(MainActivity.this);
            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShowStudentActivity.start(MainActivity.this);
            }
        });

        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String path = "/storage/emulated/0/DCIM/Camera/timg (4).jpg";
                String url = "http://lc-toFql8Gr.cn-n1.lcfile.com/sTceAzcs7pROxborzFnN0VFs2ELd7QCfrzHaHy8R.jpg";

                Request.getSingle().getApi().getFaceDetect(Request.getSingle().getDetactPatames(url))
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Consumer<FaceDetectResult>() {
                            @Override
                            public void accept(FaceDetectResult faceDetectResult) throws Exception {
                                Log.i("test", faceDetectResult.toString());
                            }
                        }, new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable throwable) throws Exception {
                                Log.i("test", throwable.toString());
                            }
                        });

//                OkHttpClient client = new OkHttpClient.Builder().build();
//                RequestBody body = new FormBody.Builder().add("api_key", Constants.FACE_API_KEY)
//                        .add("api_secret", Constants.FACE_API_SECRET)
//                        .add("image_url", url)
//                        .build();
//                okhttp3.Request request = new okhttp3.Request.Builder()
//                        .url("https://api-cn.faceplusplus.com/facepp/v3/detect")
//                        .post(body)
//                        .build();
//                client.newCall(request).enqueue(new Callback() {
//                    @Override
//                    public void onFailure(Call call, IOException e) {
//                        Log.e("test", e.toString());
//                    }
//
//                    @Override
//                    public void onResponse(Call call, Response response) throws IOException {
//                        Log.e("test", response.toString());
//                        Log.e("test", response.message());
//                        Log.e("test", response.body().string());
//                    }


                
//                });

            }
        });

        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Request.getSingle().getApi().createFaceset(Request.getSingle().getCreateFacesetPatames())
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Consumer<FaceSetCreateResult>() {
                            @Override
                            public void accept(FaceSetCreateResult faceSetCreateResult) throws Exception {
                                Log.i("test", faceSetCreateResult.toString());
                            }
                        }, new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable throwable) throws Exception {
                                Log.i("test", throwable.toString());
                            }
                        });
            }
        });

        btn5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Request.getSingle().getApi().detailFaceset(Request.getSingle().getDetailFacesetPatames())
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Consumer<FaceSetDetailResult>() {
                            @Override
                            public void accept(FaceSetDetailResult faceSetDetailResult) throws Exception {
                                Log.i("test", faceSetDetailResult.toString());
                            }
                        }, new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable throwable) throws Exception {
                                Log.i("test", throwable.toString());
                            }
                        });
            }
        });

        btn6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Request.getSingle().getApi().removeFaceset(Request.getSingle().getRemoveFacesetPatames("RemoveAllFaceTokens"))
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Consumer<FaceSetRemoveResult>() {
                            @Override
                            public void accept(FaceSetRemoveResult faceSetRemoveResult) throws Exception {
                                Log.i("test", faceSetRemoveResult.toString());
                            }
                        }, new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable throwable) throws Exception {
                                Log.i("test", throwable.toString());
                            }
                        });
            }
        });

        btn7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Request.getSingle().getApi().searchFace(Request.getSingle().getSearchPatames("http://lc-tofql8gr.cn-n1.lcfile.com/s1UZLCmCTqQxBzp3CLEabki79k8O4nURRuVj7cCf.jpg"))
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Consumer<FaceSearchResult>() {
                            @Override
                            public void accept(FaceSearchResult faceSearchResult) throws Exception {
                                Log.i("test", faceSearchResult.toString());
                            }
                        }, new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable throwable) throws Exception {
                                Log.i("test", throwable.toString());
                            }
                        });
            }
        });

        btn8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CameraActivity.start(MainActivity.this);
            }
        });

    }

}
