package bean;

import java.util.List;

public class RxxpBean {
    private int id;
    private String name;
    private List<CommodityListBean> commodityList;

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

    public List<CommodityListBean> getCommodityList() {
        return commodityList;
    }

    public void setCommodityList(List<CommodityListBean> commodityList) {
        this.commodityList = commodityList;
    }
}
