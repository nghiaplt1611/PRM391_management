package com.example.myapplication.ultility;

import com.example.myapplication.R;

public class GetAvatarResource {
    public static int getAvatarImageID(int avatar){
        switch (avatar){
            case 1:
                return R.drawable.avatar_1;
            case 2:
                return R.drawable.avatar_2;
            case 3:
                return R.drawable.avatar_3;
            case 4:
                return R.drawable.avatar_4;
            case 5:
                return R.drawable.avatar_5;
            default:
                return R.drawable.avatar_6;
        }
    }

//    public static int getButtonAvatarID(int avatar){
//        switch (avatar){
//            case 1:
//                return R.id.btn_avatar_1;
//            case 2:
//                return R.id.btn_avatar_2;
//            case 3:
//                return R.id.btn_avatar_3;
//            case 4:
//                return R.id.btn_avatar_4;
//            case 5:
//                return R.id.btn_avatar_5;
//            default:
//                return R.id.btn_avatar_6;
//        }
    //}
}
