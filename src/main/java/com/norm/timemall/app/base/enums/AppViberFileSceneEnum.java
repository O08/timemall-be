package com.norm.timemall.app.base.enums;

import java.util.Objects;

public enum AppViberFileSceneEnum {
    IMAGE("image", "图片"),
    ATTACHMENT("attachment", "附件"),
    COMIC("comic", "漫画"),
    LOCAL_AUDIO("local_audio", "本地音频资源"),
    LOCAL_VIDEO("local_video", "本地视频资源");

    private  String mark;
    private  String desc;

    AppViberFileSceneEnum(String mark, String desc) {
        this.mark = mark;
        this.desc = desc;
    }

    public static boolean validation(String value) {
        for (AppViberFileSceneEnum s : AppViberFileSceneEnum.values()) {
            if (Objects.equals(s.getMark(), value)) {
                return true;
            }
        }
        return false;
    }

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}