package cn.njcit.girl;

import java.util.List;

public class Morning {

    /**
     * code : 200
     * msg : success
     * newslist : [{"content":"睁开眼，缓一缓；快起床，伸懒腰；笑一笑，美好的一天又开始了。早安，祝你今天好心情，工作顺利，生活甜美。"}]
     */

    private int code;
    private String msg;
    private List< NewslistBean > newslist;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List< NewslistBean > getNewslist() {
        return newslist;
    }

    public void setNewslist(List< NewslistBean > newslist) {
        this.newslist = newslist;
    }

    public static class NewslistBean {
        /**
         * content : 睁开眼，缓一缓；快起床，伸懒腰；笑一笑，美好的一天又开始了。早安，祝你今天好心情，工作顺利，生活甜美。
         */

        private String content;

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }
    }
}
