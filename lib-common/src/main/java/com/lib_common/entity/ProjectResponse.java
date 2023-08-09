package com.lib_common.entity;

import java.io.Serializable;
import java.util.List;

/**
 * created by yhw
 * date 2023/8/9
 */
public class ProjectResponse implements Serializable {
    private int total;
    private int size;
    private List<ProjectItem> datas;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public List<ProjectItem> getDatas() {
        return datas;
    }

    public void setDatas(List<ProjectItem> datas) {
        this.datas = datas;
    }

    public static class ProjectItem implements Serializable{
        private String title;
        private String link;
        private String desc;
        private String envelopePic;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getLink() {
            return link;
        }

        public void setLink(String link) {
            this.link = link;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public String getEnvelopePic() {
            return envelopePic;
        }

        public void setEnvelopePic(String envelopePic) {
            this.envelopePic = envelopePic;
        }
    }
}
