package deu.hlju.dawn.studentattendance.ui.add_student;

import android.app.ProgressDialog;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import deu.hlju.dawn.studentattendance.R;
import deu.hlju.dawn.studentattendance.base.BaseActivity;

public class AddStudnetActivity extends BaseActivity implements AddStudentContract.View {

    private Button mSubmitBtn;
    private Button mChoicePortraotBtn;
    private EditText mNameEt;
    private EditText mIdEt;
    private ImageView mPortraitIv;
    private String mPortraitPath;
    private ProgressDialog mProgressDialog;
    private AddStudentContract.Presenter mPresenter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_add_studnet;
    }

    @Override
    protected void init() {
        mSubmitBtn = findViewById(R.id.submit_btn);
        mChoicePortraotBtn = findViewById(R.id.choice_portrait_btn);
        mNameEt = findViewById(R.id.name_et);
        mIdEt = findViewById(R.id.id_et);
        mPortraitIv = findViewById(R.id.portrait_iv);
        mChoicePortraotBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent("android.intent.action.GET_CONTENT");
                intent.setType("image/*");
                startActivityForResult(intent, 0);
            }
        });
        mSubmitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = mNameEt.getText().toString();
                String id = mIdEt.getText().toString();
                mPresenter.submit(name, id, mPortraitPath);
            }
        });
        mPresenter = new AddStudentPresenter(this, this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != RESULT_OK) {
            return;
        }
        String imagePath = null;
        Uri uri = data.getData();
        if (DocumentsContract.isDocumentUri(this, uri)) {
            String docId = DocumentsContract.getDocumentId(uri);
            if (uri != null && "com.android.providers.media.documents".equals(uri.getAuthority())) {
                String id = docId.split(":")[1];
                String selection = MediaStore.Images.Media._ID + "=" + id;
                imagePath = getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, selection);
            } else if (uri != null && "com.android.providers.downloads.documents".equals(uri.getAuthority())) {
                Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(docId));
                imagePath = getImagePath(contentUri, null);
            }
        } else if (uri != null && "content".equalsIgnoreCase(uri.getScheme())) {
            imagePath = getImagePath(uri, null);
        } else if (uri != null && "file".equalsIgnoreCase(uri.getScheme())) {
            imagePath = uri.getPath();
        }
        if (imagePath != null) {
            mPortraitPath = imagePath;
            mPortraitIv.setImageBitmap(BitmapFactory.decodeFile(mPortraitPath));
        }
    }

    private String getImagePath(Uri uri, String selection) {
        String path = null;
        Cursor cursor = getContentResolver().query(uri, null, selection, null, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            }
            cursor.close();
        }
        return path;
    }

    @Override
    public void showProgress() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
        }
        mProgressDialog.show();
    }

    @Override
    public void hideProgress() {
        if (mProgressDialog != null) {
            mProgressDialog.dismiss();
        }
    }

    @Override
    public void showMsg(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    public static void start(Context context) {
        context.startActivity(new Intent(context, AddStudnetActivity.class));
    }
}
