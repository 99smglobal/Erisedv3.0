package in.erised.android.erised.pojo;

/**
 * Created by tamilselvankalimuthu on 02/08/15.
 */
public class ListItemPOJO {
    protected int id;
    protected String name;
    protected boolean isSelected = false;
    protected boolean isAnimaitonShown = false;

    public ListItemPOJO() {
    }

    public ListItemPOJO(int id, String name, boolean isSelected) {
        this.id = id;
        this.name = name;
        this.isSelected = isSelected;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setIsSelected(boolean isSelected) {
        this.isSelected = isSelected;
    }

    public boolean isAnimaitonShown() {
        return isAnimaitonShown;
    }

    public void setIsAnimaitonShown(boolean isAnimaitonShown) {
        this.isAnimaitonShown = isAnimaitonShown;
    }
}
