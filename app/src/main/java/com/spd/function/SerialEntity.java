package com.spd.function;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author :Reginer in  2018/1/5 14:43.
 * 联系方式:QQ:282921012
 * 功能描述:
 */
public class SerialEntity implements Parcelable {
    /**
     * 封签号
     */
    private String sealNo;
    /**
     * 封签码
     */
    private String sealCode;

    /**
     * 是否异常
     */
    private boolean isException;


    public String getSealNo() {
        return sealNo;
    }

    public void setSealNo(String sealNo) {
        this.sealNo = sealNo;
    }

    public String getSealCode() {
        return sealCode;
    }

    public void setSealCode(String sealCode) {
        this.sealCode = sealCode;
    }

    public boolean isException() {
        return isException;
    }

    public void setException(boolean exception) {
        isException = exception;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.sealNo);
        dest.writeString(this.sealCode);
        dest.writeByte(this.isException ? (byte) 1 : (byte) 0);
    }

    public SerialEntity() {
    }

    protected SerialEntity(Parcel in) {
        this.sealNo = in.readString();
        this.sealCode = in.readString();
        this.isException = in.readByte() != 0;
    }

    public SerialEntity(String sealNo, String sealCode, boolean isException) {
        this.sealNo = sealNo;
        this.sealCode = sealCode;
        this.isException = isException;
    }

    public static final Parcelable.Creator<SerialEntity> CREATOR = new Parcelable.Creator<SerialEntity>() {
        @Override
        public SerialEntity createFromParcel(Parcel source) {
            return new SerialEntity(source);
        }

        @Override
        public SerialEntity[] newArray(int size) {
            return new SerialEntity[size];
        }
    };

    @Override
    public int hashCode() {
        return this.sealNo.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || !(obj instanceof SerialEntity) || null == this.sealNo) {
            return false;
        }
        SerialEntity entity = (SerialEntity) obj;
        return this.sealNo.equals(entity.getSealNo());
    }
}
