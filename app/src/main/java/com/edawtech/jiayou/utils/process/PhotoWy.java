package com.edawtech.jiayou.utils.process;

import java.io.Serializable;

public class PhotoWy implements Serializable {

    /**
     * 图片原始路径
     */
    private String originalPath;
    /**
     * 是否压缩过
     */
    private boolean compressed;
    /**
     * 压缩后路径
     */
    private String compressPath;

    public PhotoWy(String originalPath) {
        this.originalPath = originalPath;
    }

    public String getOriginalPath() {
        return originalPath;
    }

    public void setOriginalPath(String originalPath) {
        this.originalPath = originalPath;
    }

    public String getCompressPath() {
        return compressPath;
    }

    public void setCompressPath(String compressPath) {
        this.compressPath = compressPath;
    }

    public boolean isCompressed() {
        return compressed;
    }

    public void setCompressed(boolean compressed) {
        this.compressed = compressed;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o instanceof PhotoWy) {
            PhotoWy photo = (PhotoWy) o;
            return originalPath.equals(photo.originalPath);
        }
        return super.equals(o);
    }

}
