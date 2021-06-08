package org.example.robot.model;

import org.example.robot.utils.TimeFormatTransformer;

public class UniformInfo {
    private int type=-1;
    private ActionInfo info1=null;
    private LoginInfo info2=null;
    public static UniformInfo transform(ActionInfo info){
        UniformInfo uniformInfo=new UniformInfo();
        uniformInfo.type=1;
        uniformInfo.info1=info;
        return uniformInfo;
    }
    public static UniformInfo transform(LoginInfo info){
        UniformInfo uniformInfo=new UniformInfo();
        uniformInfo.type=0;
        uniformInfo.info2=info;
        return uniformInfo;
    }

    public int earlierThan(UniformInfo info){
        Long time1=this.type==0? TimeFormatTransformer.formatTimeStringToTimeStamp(info2.getLoginTime()): TimeFormatTransformer.formatTimeStringToTimeStamp(info1.getActionTime());
        Long time2=info.type==0? TimeFormatTransformer.formatTimeStringToTimeStamp(info.info2.getLoginTime()): TimeFormatTransformer.formatTimeStringToTimeStamp(info.info1.getActionTime());
        return time1.compareTo(time2);
    }

    @Override
    public String toString() {
        if(this.type==0)return this.info2.toString();
        else if(this.type==1)return this.info1.toString();
        else return "";
    }
}
