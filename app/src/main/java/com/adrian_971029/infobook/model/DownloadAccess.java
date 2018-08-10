package com.adrian_971029.infobook.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

class DownloadAccess implements Parcelable {

    @SerializedName("kind")
    @Expose
    private String kind;
    @SerializedName("volumeId")
    @Expose
    private String volumeId;
    @SerializedName("restricted")
    @Expose
    private boolean restricted;
    @SerializedName("deviceAllowed")
    @Expose
    private boolean deviceAllowed;
    @SerializedName("justAcquired")
    @Expose
    private boolean justAcquired;
    @SerializedName("maxDownloadDevices")
    @Expose
    private int maxDownloadDevices;
    @SerializedName("downloadsAcquired")
    @Expose
    private int downloadsAcquired;
    @SerializedName("nonce")
    @Expose
    private String nonce;
    @SerializedName("source")
    @Expose
    private String source;
    @SerializedName("reasonCode")
    @Expose
    private String reasonCode;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("signature")
    @Expose
    private String signature;

    public DownloadAccess() {
    }

    protected DownloadAccess(Parcel in) {
        kind = in.readString();
        volumeId = in.readString();
        restricted = in.readByte() != 0;
        deviceAllowed = in.readByte() != 0;
        justAcquired = in.readByte() != 0;
        maxDownloadDevices = in.readInt();
        downloadsAcquired = in.readInt();
        nonce = in.readString();
        source = in.readString();
        reasonCode = in.readString();
        message = in.readString();
        signature = in.readString();
    }

    public static final Creator<DownloadAccess> CREATOR = new Creator<DownloadAccess>() {
        @Override
        public DownloadAccess createFromParcel(Parcel in) {
            return new DownloadAccess(in);
        }

        @Override
        public DownloadAccess[] newArray(int size) {
            return new DownloadAccess[size];
        }
    };

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public String getVolumeId() {
        return volumeId;
    }

    public void setVolumeId(String volumeId) {
        this.volumeId = volumeId;
    }

    public boolean isRestricted() {
        return restricted;
    }

    public void setRestricted(boolean restricted) {
        this.restricted = restricted;
    }

    public boolean isDeviceAllowed() {
        return deviceAllowed;
    }

    public void setDeviceAllowed(boolean deviceAllowed) {
        this.deviceAllowed = deviceAllowed;
    }

    public boolean isJustAcquired() {
        return justAcquired;
    }

    public void setJustAcquired(boolean justAcquired) {
        this.justAcquired = justAcquired;
    }

    public int getMaxDownloadDevices() {
        return maxDownloadDevices;
    }

    public void setMaxDownloadDevices(int maxDownloadDevices) {
        this.maxDownloadDevices = maxDownloadDevices;
    }

    public int getDownloadsAcquired() {
        return downloadsAcquired;
    }

    public void setDownloadsAcquired(int downloadsAcquired) {
        this.downloadsAcquired = downloadsAcquired;
    }

    public String getNonce() {
        return nonce;
    }

    public void setNonce(String nonce) {
        this.nonce = nonce;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getReasonCode() {
        return reasonCode;
    }

    public void setReasonCode(String reasonCode) {
        this.reasonCode = reasonCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(kind);
        dest.writeString(volumeId);
        dest.writeByte((byte) (restricted ? 1 : 0));
        dest.writeByte((byte) (deviceAllowed ? 1 : 0));
        dest.writeByte((byte) (justAcquired ? 1 : 0));
        dest.writeInt(maxDownloadDevices);
        dest.writeInt(downloadsAcquired);
        dest.writeString(nonce);
        dest.writeString(source);
        dest.writeString(reasonCode);
        dest.writeString(message);
        dest.writeString(signature);
    }
}
