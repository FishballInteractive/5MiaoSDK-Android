package com.wumiao.sdk.demo;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Date;
import java.util.Random;
import java.util.UUID;

public class ProfileData {

    private static final String SP_NAME = "login.prefs";
    private static final String KEY_UID = "uid";
    private static final String KEY_NAME = "name";
    private static final String KEY_AVATOR = "avator";

    private SharedPreferences mSP;

    public ProfileData(Context context) {
        mSP = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
    }

    public Profile newProfile() {
        Profile profile = new Profile();
        profile.uid = UUID.randomUUID().toString();
        profile.name = NAMES[new Random(new Date().getTime()).nextInt(NAMES.length)];
        profile.avator = AVATORS[new Random(new Date().getTime()).nextInt(AVATORS.length)];

        SharedPreferences.Editor editor = mSP.edit();
        editor.putString(KEY_UID, profile.uid);
        editor.putString(KEY_NAME, profile.name);
        editor.putString(KEY_AVATOR, profile.avator);
        editor.commit();

        return profile;
    }

    public Profile getProfile() {
        Profile profile = new Profile();
        profile.uid = mSP.getString(KEY_UID, "");
        profile.name = mSP.getString(KEY_NAME, "");
        profile.avator = mSP.getString(KEY_AVATOR, "");
        return profile;
    }

    public void clearProfile() {
        SharedPreferences.Editor editor = mSP.edit();
        editor.clear();
        editor.commit();
    }

    private static final String[] NAMES = {
            "山彤",
            "敦厦",
            "负浪",
            "信品",
            "仁毅",
            "州龙",
            "务帅",
            "丙赣",
            "尉争",
            "单羽",
            "航沛",
            "开梦",
            "蓝风",
            "堃登",
            "农仲",
            "洪泉",
            "家昱",
            "道霄",
            "学共",
            "武亮",
            "宜鸿",
            "津广",
            "胤鸣",
            "进如",
            "豪谱",
            "准辰",
            "剑佛",
            "成贯",
            "耿羿",
            "协湃",
            "刚飘",
            "资龙",
            "仓翼",
            "枝迟",
            "帅齐",
            "彩武",
            "莉汐",
            "欢亭",
            "肖任",
            "政航",
            "东鑫",
            "有皆",
            "季晨",
            "奎汝",
            "韶釜",
            "汐家",
            "铮友",
            "锵良",
            "友水",
            "蒙少",
            "茗卓",
            "旭笙",
            "兴力",
            "谱班",
            "霄灼",
            "庭沛",
            "儒熙",
            "越潇",
            "中舟",
            "讯波",
            "封安",
            "和革",
            "康焕",
            "眺沃",
            "湃邦",
            "杜炯",
            "宇澄",
            "彬政",
            "召云",
            "颢锵",
            "邦余",
            "羿谆",
            "悠洋",
            "影焱",
            "绍焱",
            "叔华",
            "思徽",
            "辰博",
            "留时",
            "介辉",
            "谷佟",
            "文冲",
            "倍卫",
            "树琪",
            "邦楠",
            "枫震",
            "勋吟",
            "汐季",
            "广房",
            "誉胜",
            "希伙",
            "亚思",
            "尉寒",
            "悟营",
            "鲁郎",
            "粮鲜",
            "具匡",
            "满铿",
            "漂徽",
            "落兴",
            "风力",
            "军卓",
            "河舍",
            "俊立",
            "灶声",
            "欣英",
            "中管",
            "邦致",
            "雄民",
            "锦宏",
            "凌可",
            "朝剑",
            "梦茂",
            "瑛昼",
            "映冉",
            "翰毅",
            "争博",
            "松帆",
            "驹治",
            "独杜",
            "锐汉",
            "锋滕",
            "恩妙",
            "骞振",
            "舟察",
            "解淦",
            "舒旺",
            "豆焕",
            "帆新",
            "修纯",
            "章滕",
            "包显",
            "泰哲",
            "枫瑾",
            "善稚",
            "敬文",
            "子隐",
            "贡延",
            "烽凌",
            "戚栾",
    };

    private static final String[] AVATORS = {
            "http://p2.gexing.com/touxiang/20120802/0922/5019d66eef7ed_200x200_3.jpg",
            "http://img1.imgtn.bdimg.com/it/u=3936412306,1420363111&fm=21&gp=0.jpg",
            "http://p3.gexing.com/touxiang/2011-5/514271320201120794_200x200_3.jpg",
            "http://p1.gexing.com/touxiang/20121004/0356/506c9878cafc8_200x200_3.jpg",
            "http://wenwen.soso.com/p/20110807/20110807191237-610345246.jpg",
            "http://p4.gexing.com/shaitu/20120826/2003/503a1093520ce.jpg",
            "http://p3.gexing.com/touxiang/20121103/1911/5094fbf3f4013_200x200_3.jpg",
            "http://www.feizl.com/upload2007/2013_07/130731213272966.jpg",
            "http://p.3761.com/pic/84201393378242.jpg",
            "http://up.qqjia.com/z/14/tu17208_6.jpg",
            "http://p4.gexing.com/G1/M00/F2/95/rBACFFI4ZyGBmtMUAAAZXHmumG8935_200x200_3.jpg",
            "http://static.yingyonghui.com/user_head_img/41/1880641profile_img_s.png?t=1407316906921",
            "http://v1.qzone.cc/avatar/201406/19/15/55/53a2977256e8a087.jpg!200x200.jpg",
            "http://fdfs.xmcdn.com/group5/M01/31/34/wKgDtVOQJXCCMR5nAAC8LGv8y_E565.jpg",
            "http://cdn.duitang.com/uploads/item/201406/08/20140608161225_VYVEV.thumb.700_0.jpeg",
            "http://www.qq745.com/uploads/allimg/141012/1-141012103335-50.jpg",
            "http://v1.qzone.cc/avatar/201408/22/15/53/53f6f6e0b7dfd447.jpg!200x200.jpg",
            "http://www.qq745.com/uploads/allimg/141012/1-141012103343.jpg",
            "http://www.qqzhi.com/uploadpic/2014-12-24/223329432.jpg",
            "http://i1.t.hjfile.cn/ing_new/201307_2/dffcc3b8-3c53-4b27-95af-e65b1f5fc8e7_200X181.png",
            "http://v1.qzone.cc/avatar/201403/15/22/11/53245f8857ecf469.jpg!200x200.jpg",
            "http://www.qq745.com/uploads/allimg/141018/1-14101Q20918.jpg"
    };

    public static class Profile {
        String uid;
        String name;
        String avator;
    }
}
