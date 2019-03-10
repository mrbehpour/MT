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
                R.drawable.account_search,
                R.drawable.finance,
                R.drawable.settings,
                R.drawable.file_download_outline,
                R.drawable.icon_daryaftmoshtarakin,
                R.drawable.briefcase_upload,
                //R.drawable.exit_to_app
        };
    }

    private static String[] getTitles() {
        return new String[]{
                G.context.getResources().getString(R.string.menuItemBazdid),
                G.context.getResources().getString(R.string.menuItemReport),
                //G.context.getResources().getString(R.string.menuItem3),
                G.context.getResources().getString(R.string.menuItemSetting),
                G.context.getResources().getString(R.string.menuItemBaseInfo),
                G.context.getResources().getString(R.string.menuItemRecieveJoints),
                G.context.getResources().getString(R.string.menuItemSendJoints),
                //G.context.getResources().getString(R.string.menuItemExit),
        };
    }
}
