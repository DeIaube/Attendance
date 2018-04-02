package deu.hlju.dawn.studentattendance.ui.camera;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.hardware.Camera;
import android.os.Handler;
import android.os.Message;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.List;

import deu.hlju.dawn.studentattendance.R;
import deu.hlju.dawn.studentattendance.base.BaseActivity;


public class CameraActivity extends BaseActivity implements SurfaceHolder.Callback, CameraContract.View {


    private SurfaceView surfaceView;
    private SurfaceHolder surfaceHolder;
    private Button button;
    private Camera camera;
    private Camera.AutoFocusCallback myAutoFocusCallback1 = null;//只对焦不拍照
    private CameraContract.Presenter mPresenter;
    private ProgressDialog mProgressDialog;
    public static final int only_auto_focus = 110;
    int issuccessfocus = 0;

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case only_auto_focus:
                    if (camera != null)
                        camera.autoFocus(myAutoFocusCallback1);
                    break;
            }
        }
    };

    @Override
    protected int getLayoutId() {
        return R.layout.activity_camera;
    }

    @Override
    protected void init() {
        initView();
        initData();
        initListener();
    }

    private void initView() {
        surfaceView = findViewById(R.id.preview_fv);
        button = findViewById(R.id.picture_btn);
    }

    private void initData() {
        surfaceHolder = surfaceView.getHolder();
        surfaceHolder.addCallback(this);
        mPresenter = new CameraPresenter(this, this);
        myAutoFocusCallback1 = new Camera.AutoFocusCallback() {
            public void onAutoFocus(boolean success, Camera camera) {
                //success表示对焦成功
                if (success){
                    issuccessfocus++;
                    if (issuccessfocus <= 1)
                        mHandler.sendEmptyMessage(only_auto_focus);
                } else {
                    mHandler.sendEmptyMessage(only_auto_focus);
                }
            }
        };
    }

    private void initListener() {
        surfaceView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (camera != null) {
                    camera.autoFocus(myAutoFocusCallback1);
                }
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                camera.takePicture(null, null, new Camera.PictureCallback() {
                    @Override
                    public void onPictureTaken(byte[] bytes, Camera camera) {
                        mPresenter.startSearchFace(bytes);
                        camera.startPreview();
                    }
                });
            }
        });
    }

    private void initCamera() {
        Camera.Parameters parameters = camera.getParameters();//获取camera的parameter实例
        List<Camera.Size> sizeList = parameters.getSupportedPreviewSizes();//获取所有支持的camera尺寸
        Camera.Size optionSize = getOptimalPreviewSize(sizeList, surfaceView.getWidth(), surfaceView.getHeight());//获取一个最为适配的屏幕尺寸
        parameters.setPreviewSize(optionSize.width, optionSize.height);//把只存设置给parameters
        parameters.setPictureSize(640, 480);
        camera.setParameters(parameters);//把parameters设置给camera上
        camera.startPreview();//开始预览
        camera.setDisplayOrientation(270);//将预览旋转
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        try {
            camera = Camera.open(1);
            camera.setPreviewDisplay(surfaceHolder);
        } catch (Exception e) {
            if (null != camera) {
                camera.release();
                camera = null;
            }
            e.printStackTrace();
            Toast.makeText(CameraActivity.this, "启动摄像头失败,请开启摄像头权限", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        initCamera();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        if (null != camera) {
            camera.setPreviewCallback(null);
            camera.stopPreview();
            camera.release();
            camera = null;
        }
    }

    private Camera.Size getOptimalPreviewSize(List<Camera.Size> sizes, int w, int h) {
        final double ASPECT_TOLERANCE = 0.1;
        double targetRatio = (double) w / h;
        if (sizes == null) return null;

        Camera.Size optimalSize = null;
        double minDiff = Double.MAX_VALUE;

        // Try to find an size match aspect ratio and size
        for (Camera.Size size : sizes) {
            double ratio = (double) size.width / size.height;
            if (Math.abs(ratio - targetRatio) > ASPECT_TOLERANCE) continue;
            if (Math.abs(size.height - h) < minDiff) {
                optimalSize = size;
                minDiff = Math.abs(size.height - h);
            }
        }

        // Cannot find the one match the aspect ratio, ignore the requirement
        if (optimalSize == null) {
            minDiff = Double.MAX_VALUE;
            for (Camera.Size size : sizes) {
                if (Math.abs(size.height - h) < minDiff) {
                    optimalSize = size;
                    minDiff = Math.abs(size.height - h);
                }
            }
        }
        return optimalSize;
    }

    public static void start(Context context) {
        context.startActivity(new Intent(context, CameraActivity.class));
    }

    @Override
    public void showMsg(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showProgress() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setCancelable(false);
            mProgressDialog.setTitle(R.string.alert);
            mProgressDialog.setMessage(getString(R.string.loading));
        }
    }

    @Override
    public void hideProgress() {
        if (mProgressDialog != null) {
            mProgressDialog.dismiss();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mProgressDialog != null) {
            mProgressDialog.dismiss();
        }
    }
}
