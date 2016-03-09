package com.study.shenxing.caesar.commonintent;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.study.shenxing.caesar.R;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 来自csdn孙群博客
 */
public class IntentCommonUseActivity extends Activity implements Button.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intent_common_use);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnSendSMS:
                //发送短信
                sendSMS();
                break;
            case R.id.btnSendMail:
                //发送邮件
                sendEmail();
                break;
            case R.id.btnCallPhone:
                //打电话
                callPhone();
                break;
            case R.id.btnCaptureImage:
                //拍照
                captureImage();
                break;
            case R.id.btnCaptureVideo:
                //摄像
                captureVideo();
                break;
            default:
                break;
        }
    }

    //--------------------------------------------------------------------------------

    //发短信
    private void sendSMS(){
        //使用ACTION_SENDTO而不是ACTION_SEND
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        //指定URI使用smsto:协议，协议后面是接收短信的对象
        Uri uri = Uri.parse("smsto:10086");
        intent.setData(uri);
        //设置消息体
        intent.putExtra("sms_body", "手头有点紧，借点钱吧~~");

        ComponentName componentName = intent.resolveActivity(getPackageManager());
        if(componentName != null){
            startActivity(intent);
        }
    }

    //--------------------------------------------------------------------------------

    //发邮件
    private void sendEmail(){
        //使用ACTION_SENDTO而不是ACTION_SEND
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        //指定URI使用mailto:协议,确保只有邮件应用能接收到此intent对象
        Uri uri = Uri.parse("mailto:");
        intent.setData(uri);
        String[] addresses = {"zhangsan@126.com", "lisi@126.com"};
        String[] cc = {"boss@126.com"};
        String[] bcc = {"girlfriend@126.com"};
        String subject = "加班";
        String content = "国庆正常上班~~";
        //设置邮件的接收方
        intent.putExtra(Intent.EXTRA_EMAIL, addresses);
        //设置邮件的抄送方
        intent.putExtra(Intent.EXTRA_CC, cc);
        //设置邮件的密送方
        intent.putExtra(Intent.EXTRA_BCC, bcc);
        //设置邮件标题
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        //设置邮件内容
        intent.putExtra(Intent.EXTRA_TEXT, content);
        //设置邮件附件
        // 说明：如果附件是图片， 那么Uri所指定的路径下图片必须是完整的图片，如png或jpg格式等，如果是二进制流，那么会导致提取附件失败。
        //intent.putExtra(Intent.EXTRA_STREAM, Uri.parse(...));
        ComponentName componentName = intent.resolveActivity(getPackageManager());
        if(componentName != null){
            startActivity(intent);
        }
    }

    //--------------------------------------------------------------------------------

    //打电话
    private void callPhone(){
        //Intent.ACTION_DIAL只拨号，不打电话
        //Intent intent = new Intent(Intent.ACTION_DIAL);
        //Intent.ACTION_CALL直接拨打指定电话，需要android.permission.CALL_PHONE权限
        Intent intent = new Intent(Intent.ACTION_CALL);
        Uri uri = Uri.parse("tel:10086");
        intent.setData(uri);
        ComponentName componentName = intent.resolveActivity(getPackageManager());
        if(componentName != null){
            startActivity(intent);
        }
    }

    //--------------------------------------------------------------------------------

    //表示用于拍照的requestCode
    private final int REQUEST_CODE_IMAGE_CAPTURE = 1;
    //我们存储照片的输出路径，以便后续使用
    private Uri imageOutputUri = null;

    //拍照
    private void captureImage(){
        PackageManager pm = getPackageManager();

        //先判断本机是否在硬件上有摄像能力
        if(pm.hasSystemFeature(PackageManager.FEATURE_CAMERA)){
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            ComponentName componentName = intent.resolveActivity(pm);
            //判断手机上有无摄像机应用
            if(componentName != null){
                //创建图片文件，以便于通过Uri.fromFile()生成对应的Uri
                File imageFile = createImageFile();
                if(imageFile != null){
                    //根据imageFile生成对应的Uri
                    imageOutputUri = Uri.fromFile(imageFile);
                    //利用该Uri作为拍照完成后照片的存储路径，注意，一旦设置了存储路径，我们就不能获取缩略图了
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, imageOutputUri);
                    //调用startActivityForResult()方法，以便在onActivityResult()方法中进行相应处理
                    startActivityForResult(intent, REQUEST_CODE_IMAGE_CAPTURE);
                }else{
                    Toast.makeText(this, "无法创建图像文件！", Toast.LENGTH_LONG).show();
                }
            }else{
                Toast.makeText(this, "未在本机找到Camera应用，无法拍照！", Toast.LENGTH_LONG).show();
            }
        }else{
            Toast.makeText(this, "本机没有摄像头,无法拍照!", Toast.LENGTH_LONG).show();
        }
    }

    //创建图片文件，以便于通过Uri.fromFile()生成对应的Uri
    private File createImageFile(){
        File imageFile = null;

        //用时间戳拼接文件名称，防止文件重名
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);

        try{
            imageFile = File.createTempFile(
                    imageFileName,  //前缀
                    ".jpg",         //后缀
                    storageDir      //文件夹
            );
        }catch (IOException e){
            imageFile = null;
            e.printStackTrace();
            Log.e("DemoLog", e.getMessage());
        }

        return imageFile;
    }

    //--------------------------------------------------------------------------------

    //表示用于录视频的requestCode
    private final int REQUEST_CODE_VIDEO_CAPTURE = 2;

    //我们存储视频的输出路径，以便后续使用
    private Uri videoOutputUri = null;

    //摄像
    private void captureVideo(){
        PackageManager pm = getPackageManager();

        //先判断本机是否在硬件上有摄像能力
        if(pm.hasSystemFeature(PackageManager.FEATURE_CAMERA)){
            //将intent的action设置为MediaStore.ACTION_VIDEO_CAPTURE
            Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
            ComponentName componentName = intent.resolveActivity(pm);
            //判断手机上有无摄像机应用
            if(componentName != null){
                //创建视频文件，以便于通过Uri.fromFile()生成对应的Uri
                File videoFile = createVideoFile();
                if(videoFile != null){
                    //根据videoFile生成对应的Uri
                    videoOutputUri = Uri.fromFile(videoFile);
                    //利用该Uri作为摄像完成后视频的存储路径
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, videoOutputUri);
                    //调用startActivityForResult()方法，以便在onActivityResult()方法中进行相应处理
                    startActivityForResult(intent, REQUEST_CODE_VIDEO_CAPTURE);
                }else{
                    Toast.makeText(this, "无法创建视频文件！", Toast.LENGTH_LONG).show();
                }
            }else{
                Toast.makeText(this, "未在本机找到Camera应用，无法摄像！", Toast.LENGTH_LONG).show();
            }
        }else{
            Toast.makeText(this, "本机没有摄像头,无法摄像!", Toast.LENGTH_LONG).show();
        }
    }

    //创建视频文件，以便于通过Uri.fromFile()生成对应的Uri
    private File createVideoFile(){
        File videoFile = null;

        //用时间戳拼接文件名称，防止文件重名
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "MP4" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_MOVIES);

        try{
            videoFile = File.createTempFile(
                    imageFileName,  //前缀
                    ".mp4",         //后缀
                    storageDir      //文件夹
            );
        }catch (IOException e){
            videoFile = null;
            e.printStackTrace();
            Log.e("DemoLog", e.getMessage());
        }

        return videoFile;
    }

    //--------------------------------------------------------------------------------

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        //首先判断是否正确完成
        if(resultCode == RESULT_OK){
            switch (requestCode){
                case REQUEST_CODE_IMAGE_CAPTURE:
                    //拍照完成
                    //此处，我们可以通过imageOutputUri获取到我们想要的图片
                    String imagePath = imageOutputUri.toString();
                    Log.i("DemoLog", "照片路径是: " + imagePath);
                    Toast.makeText(this, "照片路径是: " + imagePath, Toast.LENGTH_LONG).show();

                    //以下代码尝试获取缩略图
                    //如果设置MediaStore.EXTRA_OUTPUT作为extra的时候，那么此处的intent为null，需要判断
                    if(intent != null){
                        Bitmap thumbnail = intent.getParcelableExtra("data");
                        //有的手机并不会给拍照的图片生成缩略图，所以此处也要判断
                        if(thumbnail != null){
                            Log.i("DemoLog", "得到缩略图");
                        }
                    }
                    break;
                case REQUEST_CODE_VIDEO_CAPTURE:
                    //摄像完成
                    //如果设置MediaStore.EXTRA_OUTPUT作为extra的时候，
                    //在有的手机上，此处的intent为不为null，但是在有的手机上却为null，
                    //所以不建议从intent.getData()中获取视频路径
                    //我们应该自己记录videoOutputUri来得知视频路径，下面注释的代码不建议使用
                    /*if(intent != null){
                        Uri videoUri = intent.getData();
                        if(videoUri != null){
                            //路径格式如content://media/external/video/media/130025
                            Log.i("DemoLog", "视频路径是: " + videoUri.toString());
                        }
                    }*/

                    String videoPath = videoOutputUri.toString();
                    //1.如果没有设置MediaStore.EXTRA_OUTPUT作为视频文件存储路径，那么路径格式如下所示:
                    //  路径格式如content://media/external/video/media/130025
                    //2.如果设置了MediaStore.EXTRA_OUTPUT作为视频文件存储路径，那么路径格式如下所示:
                    //  路径格式如file:///storage/sdcard0/Android/data/com.ispring.commonintents/files/Movies/MP420150919_184132_533002075.mp4
                    Log.i("DemoLog", "视频路径是: " + videoPath);
                    Toast.makeText(this, "视频路径是: " + videoPath, Toast.LENGTH_LONG).show();
                    break;
                default:
                    break;
            }
        }
    }

    /**
     * 跳转到浏览器
     * @param uriString
     */
    private void gotoBrowser(String uriString) {
        if (uriString == null) {
            return;
        }

        Uri browserUri = Uri.parse(uriString);
        if (null != browserUri) {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, browserUri);
            browserIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            try {
                startActivity(browserIntent);
            } catch (ActivityNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}