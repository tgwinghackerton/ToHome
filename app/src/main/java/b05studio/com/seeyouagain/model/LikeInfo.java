package b05studio.com.seeyouagain.model;

/**
 * Created by mansu on 2017-10-21.
 */

public class LikeInfo {
    private int type;
    private String id;

    public LikeInfo() {}

    public LikeInfo(int type, String id) {
        this.type = type;
        this.id = id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if(!(o instanceof LikeInfo))
            return false;

        LikeInfo other = (LikeInfo)o;
        if(other.getType() == this.type && other.getId().compareTo(this.id) == 0)
            return true;
        else
            return false;
    }
}
