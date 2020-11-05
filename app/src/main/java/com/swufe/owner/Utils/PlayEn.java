package com.swufe.owner.Utils;

import java.io.InputStream;


import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;

public class PlayEn {
    public final static String MUSIC_ENG_RELATIVE_PATH="yueci/sounds/sounds_EN/";

    public Context context=null;
    public String tableName=null;
    public MediaPlayer mediaPlayer=null;
    Download fileU=null;
    public  boolean isMusicPermitted=true;     //用于对是否播放音乐进行保护性设置，当该变量为false时，可以阻止一次音乐播放

    public PlayEn(Context context,String tableName){
        this.context=context;
        this.tableName=tableName;
        fileU=new Download();
        isMusicPermitted=true;

    }
    /**
     *
     * 一个Activity中一般只能有一个Voice成员变量，对应的也就只有一个MediaPlayer对象，这样才能对播放
     * 状态进行有效控制
     * 该方法原则上只能在线程中调用
     *
     */
    public void playMusicByWord(String url){
        //走到这里说明文件夹里一定有响应的音乐文件，故在这里播放
        /**
         * 这个方法存在缺点，可能因为同时new 了多个MediaPlayer对象，导致start方法失效，
         * 因此解决的方法是，使用同一个MediaPlayer对象，若一次播放时发现对象非空，那么先调用release()方法释放资源，再重新create
         */


        try{
            if(mediaPlayer!=null){
                if(mediaPlayer.isPlaying())
                    mediaPlayer.stop();
                mediaPlayer.release();
                mediaPlayer=null;     //为了防止mediaPlayer多次调用stop release，这里置空还是有必要
            }
            mediaPlayer=MediaPlayer.create(context, Uri.parse(url));
            mediaPlayer.start();
        }catch(Exception e){
            mediaPlayer.release();
            e.printStackTrace();
        }

    }



}

