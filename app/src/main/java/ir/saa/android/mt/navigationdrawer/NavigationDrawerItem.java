package ir.saa.android.mt.navigationdrawer;

import ir.saa.android.mt.R;
import ir.saa.android.mt.application.G;

import java.util.ArrayList;
import java.util.List;

public class NavigationDrawerItem {

    private String title;
    private int imageId;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public static List<NavigationDrawerItem> getData() {
        List<NavigationDrawerItem> dataList = new ArrayList<>();

        int[] imageIds = getImages();
        String[] titles = getTitles();

        for (int i = 0; i < titles.length; i++) {
            NavigationDrawerItem navItem = new NavigationDrawerItem();
            navItem.setTitle(titles[i]);
            navItem.setImageId(imageIds[i]);
            dataList.add(navItem);
        }
        return dataList;
    }

    private static int[] getImages() {

        return new int[]{
                R.drawable.speedometer,
                R.drawable.altimeter,
                R.drawable.video_input_hdmi,
                R.drawable.finance,
                R.drawable.settings,
                R.drawable.exit_to_app
        };
    }

    private static String[] getTitles() {
        return new String[]{
                G.context.getResources().getString(R.string.menuItem1),
                G.context.getResources().getString(R.string.menuItem2),
                G.context.getResources().getString(R.string.menuItem3),
                G.context.getResources().getString(R.string.menuItem4),
                G.context.getResources().getString(R.string.menuItem5),
                G.context.getResources().getString(R.string.menuItem6),
        };
    }
}
