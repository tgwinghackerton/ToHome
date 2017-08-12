package b05studio.com.seeyouagain.model;

import java.util.Calendar;

/**
 * Created by mansu on 2017-07-05.
 */

public class AlarmInfo {
    private String writerId;
    private String imageUrl;
    private String name;
    private Calendar date;
    private String content;

    public AlarmInfo(String writerId, String imageUrl, String name, Calendar date, String content) {
        this.writerId = writerId;
        this.imageUrl = imageUrl;
        this.name = name;
        this.date = date;
        this.content = content;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Calendar getDate() {
        return date;
    }

    public void setDate(Calendar date) {
        this.date = date;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getWriterId() {
        return writerId;
    }

    public void setWriterId(String writerId) {
        this.writerId = writerId;
    }
}
