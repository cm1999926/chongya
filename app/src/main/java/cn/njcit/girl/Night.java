package cn.njcit.girl;

import java.util.List;

public class Night {

    /**
     * code : 200
     * msg : success
     * newslist : [{"content":"和喜欢的人互道晚安，这是一天里最后的甜。晚安！"}]
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
         * content : 和喜欢的人互道晚安，这是一天里最后的甜。晚安！
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
